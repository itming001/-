package com.li.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownFileUtils {
    /**
     * 获取连接
     */
    private static HttpURLConnection getHttpUrlConnection(String netUrl) throws Exception {
        URL url = new URL(netUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        // 设置超时间为3秒
        httpURLConnection.setConnectTimeout(3 * 1000);
        // 防止屏蔽程序抓取而返回403错误
        httpURLConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        return httpURLConnection;
    }


    /**
     * 判断连接是否支持断点下载
     */
    private static boolean isSupportRange(String netUrl) throws Exception {
        HttpURLConnection httpURLConnection = getHttpUrlConnection(netUrl);
        String acceptRanges = httpURLConnection.getHeaderField("Accept-Ranges");
        if (StringUtils.isEmpty(acceptRanges)) {
            return false;
        }
        if ("bytes".equalsIgnoreCase(acceptRanges)) {
            return true;
        }
        return false;
    }

    /**
     * 获取远程文件大小
     */
    private static int getFileContentLength(String netUrl) throws Exception {
        HttpURLConnection httpUrlConnection = getHttpUrlConnection(netUrl);
        int contentLength = httpUrlConnection.getContentLength();
        closeHttpUrlConnection(httpUrlConnection);
        return contentLength;
    }

    public static void testDownLoad(String url) throws Exception {
        int fileContentLength = getFileContentLength(url);
        segmentDownload(fileContentLength, url, 3);
    }

    public static void groupDownload(String url) throws Exception {
        int fileContentLength = getFileContentLength(url);
        groupDownload(url, fileContentLength, 3);
    }


    public static void main(String[] args) {
        try {
            String url = "https://oss-scm-goods-testenv.oss-cn-hangzhou.aliyuncs.com/%E5%87%BA%E5%BA%93%E5%8D%95_20240220142305_PD076279.xlsx";
            //测试单线程断点下载
            testDownLoad(url);
            //测试多线程断点下载
            groupDownload(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 单线程串行下载
     *
     * @param totalFileSize 文件总大小
     * @param netUrl        文件地址
     * @param N             串行下载分段次数
     */
    private static void segmentDownload(int totalFileSize, String netUrl, int N) throws Exception {
        String localFilePath = "D:\\test\\测试文件.xlsx";
        int eachFileSize = totalFileSize / N;
        for (int i = 0; i < N; i++) {
            File localFile = new File(localFilePath);
            long start = i * eachFileSize;
            long end = (i < N - 1) ? (start + eachFileSize - 1) : totalFileSize - 1; // 确保最后一段不超过文件大小
            appendFile(netUrl, localFile, start, end);
            System.out.println(String.format("我是第%s次下载，下载片段范围start=%s,end=%s", i + 1, start, end));
        }
        System.out.println("本地文件大小：" + new File(localFilePath).length());
    }


    /**
     * 文件尾部追加
     * @param netUrl 地址
     * @param localFile 本地文件
     * @param start 分段开始位置
     * @param end 分段结束位置
     */
    private static void appendFile(String netUrl, File localFile, long start, long end) throws Exception {
        HttpURLConnection httpURLConnection = getHttpUrlConnection(netUrl);
        httpURLConnection.setRequestProperty("Range", "bytes=" + start + "-" + end);
        // 获取远程文件流信息
        InputStream inputStream = httpURLConnection.getInputStream();
        // 本地文件写入流，支持文件追加
        FileOutputStream fos = FileUtils.openOutputStream(localFile, true);
        IOUtils.copy(inputStream, fos);
        closeHttpUrlConnection(httpURLConnection);
    }

    private static void closeHttpUrlConnection(HttpURLConnection httpURLConnection) {
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
    }




    private static void groupDownload(String netUrl, int totalFileSize, int N) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(N);
        String localFilePath = "D:\\test\\测试文件moreThread.xlsx";
        int groupSize = totalFileSize / N;
        ExecutorService executorService = Executors.newFixedThreadPool(N); // 使用单个线程池

        for (int i = 0; i < N; i++) {
            int start = i * groupSize;
            int end = (i < N - 1) ? (start + groupSize - 1) : totalFileSize - 1;
            downloadAndMerge(executorService, i + 1, netUrl, localFilePath, start, end, countDownLatch);
        }

        countDownLatch.await();
        executorService.shutdown(); // 关闭线程池
        validateCompleteness(localFilePath, netUrl);
    }

    private static void downloadAndMerge(ExecutorService executorService, int threadNum, String netUrl, String localFilePath, int start, int end, CountDownLatch countDownLatch) {
        executorService.execute(() -> {
            try (RandomAccessFile randomAccessFile = new RandomAccessFile(localFilePath, "rw")) {
                HttpURLConnection httpURLConnection = getHttpUrlConnection(netUrl);
                httpURLConnection.setRequestProperty("Range", "bytes=" + start + "-" + end);
                try (InputStream inputStream = httpURLConnection.getInputStream()) {
                    randomAccessFile.seek(start);
                    byte[] buffer = new byte[1024 * 10];
                    int len;
                    while ((len = inputStream.read(buffer)) != -1) {
                        randomAccessFile.write(buffer, 0, len);
                    }
                }
                System.out.println("下载完成: 线程 " + threadNum + ", 范围 " + start + "-" + end);
            } catch (Exception e) {
                System.out.println("下载异常: 线程 " + threadNum + ", 范围 " + start + "-" + end);
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
        });
    }


    /**
     * 校验文件一致性，我们判断Etag和本地文件的md5是否一致
     * 注:Etag携带了双引号
     * @param localFilePath
     * @param netUrl
     */
    private static void validateCompleteness(String localFilePath, String netUrl) throws Exception{
        File file = new File(localFilePath);
        InputStream data = new FileInputStream(file);
        String md5 = DigestUtils.md5Hex(data);
        HttpURLConnection httpURLConnection = getHttpUrlConnection(netUrl);
        String etag = httpURLConnection.getHeaderField("Etag");
        if (etag.toUpperCase().contains(md5.toUpperCase())) {
            System.out.println(String.format("本地文件和远程文件一致，md5 = %s, Etag = %s", md5.toUpperCase(), etag));
        } else {
            System.out.println(String.format("本地文件和远程文件不一致，md5 = %s, Etag = %s", md5.toUpperCase(), etag));
        }
    }
}
