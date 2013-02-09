package com.jerry.recipe.calorie.calculator.request;

import android.content.Context;

public abstract class RecipeRequestBody {
    private Context mContext;
    
    public RecipeRequestBody(Context context) {
        mContext = context;
    }
    
    public RecipeAuthentication getAuthentication() {
        return RecipeAuthentication.getJsonObject(mContext);
    }
}
