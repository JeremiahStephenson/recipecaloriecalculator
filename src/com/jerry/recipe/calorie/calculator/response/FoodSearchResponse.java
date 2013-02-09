package com.jerry.recipe.calorie.calculator.response;

public class FoodSearchResponse {

	private String food_description;
	private String food_id;
	private String food_name;
	private String food_type;
	private String brand_name;
	private String food_url;
	
	public FoodSearchResponse(){}
	
	public String getFoodDescription() {
		return food_description;
	}

	public String getFoodId() {
		return food_id;
	}

	public String getFoodName() {
		return food_name;
	}

	public String getFoodType() {
		return food_type;
	}

	public String getBrandName() {
		return brand_name;
	}

	public String getFoodUrl() {
		return food_url;
	}
	
}
