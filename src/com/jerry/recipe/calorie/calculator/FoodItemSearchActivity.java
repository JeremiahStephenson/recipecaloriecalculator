package com.jerry.recipe.calorie.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.jerry.recipe.calorie.calculator.request.GetFoodSearchRequest;
import com.jerry.recipe.calorie.calculator.service.GetFoodSearchService;
import com.jerry.recipe.calorie.calculator.service.RecipeRequestService;

public class FoodItemSearchActivity extends RecipeActivity implements OnClickListener {

	private Button mBtnSearch;
	private EditText mEditText;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_search);
        
        mBtnSearch = (Button)findViewById(R.id.btn_search);
    	mBtnSearch.setOnClickListener(this);
    	
    	mEditText = (EditText)findViewById(R.id.txt_search);
    }
    
	@Override
	public void onClick(View v) {
		
		if (v == mBtnSearch) {
			
			Intent intent = new Intent(this, GetFoodSearchService.class);
            intent.putExtra(RecipeRequestService.EXTRA_REQUEST, new GetFoodSearchRequest(mEditText.getText().toString()));
            startService(intent);
			
		}
	}
}
