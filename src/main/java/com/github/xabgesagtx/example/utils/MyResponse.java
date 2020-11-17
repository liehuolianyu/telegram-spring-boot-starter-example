package com.github.xabgesagtx.example.utils;

import java.io.Serializable;

public class MyResponse  {
    private Integer code;
    private boolean success;

    public MyResponse(Integer code,boolean success){
        this.code=code;
        this.success=success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
