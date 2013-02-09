package com.jerry.recipe.calorie.calculator.service;

import java.net.HttpURLConnection;
import java.util.ArrayList;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;

import com.jerry.recipe.calorie.calculator.contentprovider.Provider;
import com.jerry.recipe.calorie.calculator.database.tables.FoodSearchItemTable;
import com.jerry.recipe.calorie.calculator.request.GetFoodSearchRequest;
import com.jerry.recipe.calorie.calculator.response.FoodSearchResponse;
import com.jerry.recipe.calorie.calculator.util.ServerStatus;
import com.jerry.recipe.calorie.calculator.wrappers.GetFoodSearchResponseWrapper;

public class GetFoodSearchService extends RecipeRequestService<GetFoodSearchRequest, GetFoodSearchResponseWrapper> {

    public GetFoodSearchService() {
        super("GetFoodSearchService");
    }
    
    public class GetFoodSearchEvent{}

    @Override
    public void synchronizeResponse(Context context, ServerStatus status, GetFoodSearchResponseWrapper response) {
        super.synchronizeResponse(context, status, response);

        if (status.getResponseCode() == HttpURLConnection.HTTP_OK) {
            
        	ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();
        	
        	if (response.getData() == null || response.getData().getPageNumber() == 0) {
        		operations.add(ContentProviderOperation.newDelete(Provider.FOODSEARCHITEM_CONTENT_URI).build());
        	}
        	
        	if (response.getData() != null) {
	        	final ArrayList<FoodSearchResponse> foods = response.getData().getItems();
	        	if (foods != null) {
		        	for (FoodSearchResponse food : foods) {
		        		
		        		ContentValues foodValues = new ContentValues();
		        		foodValues.put(FoodSearchItemTable.BRAND, food.getBrandName());
		        		foodValues.put(FoodSearchItemTable.DESCRIPTION, food.getFoodDescription());
		        		foodValues.put(FoodSearchItemTable.NAME, food.getFoodName());
		        		foodValues.put(FoodSearchItemTable.SERVERID, food.getFoodId());
		        		foodValues.put(FoodSearchItemTable.TYPE, food.getFoodType());
		        		foodValues.put(FoodSearchItemTable.URL, food.getFoodUrl());
		        		
		        		ContentProviderOperation.Builder insertActivityOperation = ContentProviderOperation
		                        .newInsert(Provider.FOODSEARCHITEM_CONTENT_URI).withValues(foodValues);
		
		                operations.add(insertActivityOperation.build());
		        	}
	        	}
        	}
        	
            try {
                getContentResolver().applyBatch(Provider.AUTHORITY, operations);
                mEventBus.post(new GetFoodSearchEvent());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
