package com.group.internet.banking.exception;

public class BusinessException extends Exception {

    private String errCode;

    public BusinessException(String errMsg) {
        super(errMsg);
    }

    public BusinessException(String errMsg, String errCode) {
        super(errMsg);
        this.errCode = errCode;
    }

    public String getErrCode(){
        return this.errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }
}
