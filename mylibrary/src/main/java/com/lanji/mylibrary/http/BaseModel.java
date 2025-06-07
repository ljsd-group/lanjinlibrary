package com.lanji.mylibrary.http;

import java.io.Serializable;

public class BaseModel implements Serializable {
    public int code;
    public String message;

    @Override
    public String toString() {
        return "Model{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
