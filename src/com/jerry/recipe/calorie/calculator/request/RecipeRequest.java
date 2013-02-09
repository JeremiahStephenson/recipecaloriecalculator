package com.jerry.recipe.calorie.calculator.request;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.jerry.recipe.calorie.calculator.util.FilterParameter;
import com.jerry.recipe.calorie.calculator.util.Json;

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
    @JsonIgnore
    public int describeContents() {
        return 0;
    }

    @JsonIgnore
    public abstract ArrayList<FilterParameter> getRequestParameters();
    @JsonIgnore
    public abstract String getRequestMethod();
    @JsonIgnore
    public abstract void initialize(Context context);
    @JsonIgnore
    public abstract Gson buildGson();
    
    @JsonIgnore
    public RequestType getRequestType() {
        return mRequestType;
    }

    @JsonIgnore
    public void setRequestBody(RecipeRequestBody requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if(mRequestType == RequestType.Patch){
        	headers.add("X-HTTP-Method", "PATCH");
        }

        mRequestBody = new HttpEntity<String>(Json.toString(requestBody), headers);
    }

    @JsonIgnore
    public Object getRequestBody() {
        return mRequestBody;
    }

    public abstract Class<TResponse> getResponseType();
}

