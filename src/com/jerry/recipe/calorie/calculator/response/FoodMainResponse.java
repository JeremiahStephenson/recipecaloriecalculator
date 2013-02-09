package com.jerry.recipe.calorie.calculator.response;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class FoodMainResponse<TData> {
    
    private TData foods;
    
    public TData getData() {
        return foods;
    } 
    
    public void setData(TData foods) {
    	this.foods = foods;
    }

    public FoodMainResponse () {}
    
}
