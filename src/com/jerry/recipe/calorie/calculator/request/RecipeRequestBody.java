package com.jerry.recipe.calorie.calculator.request;

import org.codehaus.jackson.annotate.JsonProperty;

import android.content.Context;

public abstract class RecipeRequestBody {
    private Context mContext;
    
    public RecipeRequestBody(Context context) {
        mContext = context;
    }
    
    @JsonProperty("auth")
    public RecipeAuthentication getAuthentication() {
        return RecipeAuthentication.getJsonObject(mContext);
    }
}
