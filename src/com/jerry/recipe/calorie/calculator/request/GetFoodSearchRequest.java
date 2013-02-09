package com.jerry.recipe.calorie.calculator.request;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jerry.recipe.calorie.calculator.deserializer.FoodDeserializer;
import com.jerry.recipe.calorie.calculator.response.FoodSearchArrayResponse;
import com.jerry.recipe.calorie.calculator.util.FilterParameter;
import com.jerry.recipe.calorie.calculator.wrappers.GetFoodSearchResponseWrapper;

public class GetFoodSearchRequest extends RecipeRequest<GetFoodSearchResponseWrapper> {

	private String mSearchExpression;
	
    public GetFoodSearchRequest(Parcel parcel) {
        super(parcel);
        
        mSearchExpression = parcel.readString();
    }

    public GetFoodSearchRequest() {
        super(RequestType.Get);
    }
    
    public GetFoodSearchRequest(String searchExpression) {
        super(RequestType.Get);
        
        mSearchExpression = searchExpression;
    }
    
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        super.writeToParcel(parcel, flags);

        parcel.writeString(mSearchExpression);
    }

    public static final Parcelable.Creator<GetFoodSearchRequest> CREATOR = new Parcelable.Creator<GetFoodSearchRequest>() {
        public GetFoodSearchRequest createFromParcel(Parcel in) {
            return new GetFoodSearchRequest(in);
        }

        public GetFoodSearchRequest[] newArray(int size) {
            return new GetFoodSearchRequest[size];
        }
    };

    @Override
	public void initialize(Context context) {
    	// ?
	}
    
    @Override
    public String getRequestMethod() {
        return "foods.search";
    }

    @Override
    public Class<GetFoodSearchResponseWrapper> getResponseType() {
        return GetFoodSearchResponseWrapper.class;
    }
    
    public String getSearchExpression(){
    	return mSearchExpression;
    }

	@Override
	public ArrayList<FilterParameter> getRequestParameters() {
		
		final ArrayList<FilterParameter> filters = new ArrayList<FilterParameter>();
		
		try {
			filters.add(new FilterParameter("search_expression", URLEncoder.encode(mSearchExpression, "UTF-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return filters;
	}

	@Override
	public Gson buildGson() {
		return new GsonBuilder().registerTypeAdapter(FoodSearchArrayResponse.class, new FoodDeserializer()).create();
	}
    
}
