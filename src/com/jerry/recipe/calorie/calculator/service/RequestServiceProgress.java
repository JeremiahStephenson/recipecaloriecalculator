package com.jerry.recipe.calorie.calculator.service;

public abstract class RequestServiceProgress {
    public enum ProgressType {
        StartRequest, EndRequest, StartSynchronize, EndSynchronize
    }
    
    private ProgressType mProgressType;
    
    public void setProgressType(ProgressType progressType) {
        mProgressType = progressType;
    }
    
    public ProgressType getProgressType() {
        return mProgressType;
    }
}
