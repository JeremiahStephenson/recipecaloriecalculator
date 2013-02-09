package com.jerry.recipe.calorie.calculator.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;

public class PagingListAdapter extends CursorAdapter {

	public PagingListAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
	}
	
	public PagingListAdapter(Context context, Cursor c, int flags) {
		super(context, c, flags);
	}

	@Override
	public void bindView(View view, Context context, Cursor c) {
		
	}

	@Override
	public View newView(Context context, Cursor c, ViewGroup viewGroup) {
		return null;
	}

}
