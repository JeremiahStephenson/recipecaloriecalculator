package com.jerry.recipe.calorie.calculator.deserializer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.jerry.recipe.calorie.calculator.enums.JsonEnum;
import com.jerry.recipe.calorie.calculator.response.FoodSearchArrayResponse;
import com.jerry.recipe.calorie.calculator.response.FoodSearchResponse;

public class FoodDeserializer implements JsonDeserializer<FoodSearchArrayResponse> {
	
	@Override
	public FoodSearchArrayResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		
		final JsonObject resultsObj = json.getAsJsonObject();
        final Gson g = new Gson();
        final FoodSearchArrayResponse foodArray = g.fromJson(json, FoodSearchArrayResponse.class);
        
        ArrayList<FoodSearchResponse> foods = null;
        
        final JsonElement element = resultsObj.get(JsonEnum.FOOD);
        
        if (element != null) {
        
	        if (element.isJsonArray()) {
	            foods = g.fromJson(element, new TypeToken<List<FoodSearchResponse>>(){}.getType());
	        } else {
	        	final FoodSearchResponse single = g.fromJson(element, FoodSearchResponse.class);
	            foods = new ArrayList<FoodSearchResponse>();
	            foods.add(single);
	        }
        }
        
        if (foods == null) {
        	foods = new ArrayList<FoodSearchResponse>();
        }
        
        foodArray.setItems(foods);
        return foodArray;
	}
}
