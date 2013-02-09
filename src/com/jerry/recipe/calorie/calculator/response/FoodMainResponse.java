package com.jerry.recipe.calorie.calculator.response;


public abstract class FoodMainResponse<TData> extends ErrorResponse {
    
    private TData foods;
    
    public TData getData() {
        return foods;
    } 
    
    public void setData(TData foods) {
    	this.foods = foods;
    }

    public FoodMainResponse () {}
    
}
