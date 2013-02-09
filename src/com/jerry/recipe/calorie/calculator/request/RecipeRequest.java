package com.jerry.recipe.calorie.calculator.request;

import java.util.ArrayList;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.jerry.recipe.calorie.calculator.util.FilterParameter;

public abstract class RecipeRequest<TResponse> implements Parcelable {


    public static final String RECIPE_SERVER = "http://platform.fatsecret.com/rest/server.api";

    public enum RequestType {
        Get, Post, Put, Delete, Patch
    }

    protected RequestType mRequestType;
    protected Object mRequestBody;

    protected RecipeRequest(Parcel parcel) {
        mRequestType = RequestType.values()[parcel.readInt()];
    }

    protected RecipeRequest(RequestType requestType) {
        mRequestType = requestType;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(mRequestType.ordinal());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public abstract ArrayList<FilterParameter> getRequestParameters();
    public abstract String getRequestMethod();
    public abstract void initialize(Context context);
    public abstract Gson buildGson();
    
    public RequestType getRequestType() {
        return mRequestType;
    }

    public void setRequestBody(RecipeRequestBody requestBody) {
        
    }

    public Object getRequestBody() {
        return mRequestBody;
    }

    public abstract Class<TResponse> getResponseType();
}

