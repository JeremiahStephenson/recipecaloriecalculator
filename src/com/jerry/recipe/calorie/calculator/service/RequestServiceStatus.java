package com.jerry.recipe.calorie.calculator.service;

public class RequestServiceStatus {
    public enum RequestServiceStatusType {
        Progress, Completed, Error
    }

    private String mServiceName;
    private RequestServiceStatusType mStatusType;
    private Object mData;

    public RequestServiceStatus(String serviceName, RequestServiceStatusType statusType, Object data) {
        mServiceName = serviceName;
        mStatusType = statusType;
        mData = data;
    }
    
    public String getServiceName() {
        return mServiceName;
    }

    public RequestServiceStatusType getStatusType() {
        return mStatusType;
    }

    public Object getData() {
        return mData;
    }
}
