package com.jerry.recipe.calorie.calculator.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jerry.recipe.calorie.calculator.R;
import com.jerry.recipe.calorie.calculator.model.FoodSearchItem;

public class FoodSearchListAdapter extends PagingListAdapter {

	private final LayoutInflater mInflater;
	
	public FoodSearchListAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		
		mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public void bindView(View view, Context context, Cursor c) {
		
		final FoodSearchItem food = new FoodSearchItem(c);

		FoodItemHolder holder = (FoodItemHolder)view.getTag();

		holder.name.setText(food.getName());
		holder.description.setText(food.getDescription());
	}

	@Override
	public View newView(Context context, Cursor c, ViewGroup viewGroup) {
		
		View view = mInflater.inflate(R.layout.list_item_food_search, viewGroup, false);

        FoodItemHolder holder = new FoodItemHolder();
        holder.name = (TextView)view.findViewById(R.id.txt_name);
        holder.description = (TextView)view.findViewById(R.id.txt_description);
        
        view.setTag(holder);

        return view;
	}
	
	public static class FoodItemHolder {
        TextView name;
        TextView description;
    }

}
