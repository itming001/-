package com.li.enums;

import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public enum CountryEnum {
    /**
     * 齐国
     */
    ONE(1,"齐"),
    TWO(2,"楚"),
    THREE(3,"燕"),
    FOUR(4,"赵"),
    FIVE(5,"魏"),
    SIX(6,"韩");

    private Integer code;
    private String retMessage;

    CountryEnum(Integer code, String retMessage) {
        this.code = code;
        this.retMessage = retMessage;
    }

    public static CountryEnum getCountry(int index){
        CountryEnum countryEnum = null;
        CountryEnum[] values = CountryEnum.values();
        for (int i = 0; i < 6; i++) {
            if(values[i].code == index){
                 return values[i];
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getRetMessage() {
        return retMessage;
    }

    public void setRetMessage(String retMessage) {
        this.retMessage = retMessage;
    }
}
