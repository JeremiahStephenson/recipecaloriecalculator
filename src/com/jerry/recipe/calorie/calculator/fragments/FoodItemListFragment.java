package com.jerry.recipe.calorie.calculator.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jerry.recipe.calorie.calculator.adapters.FoodSearchListAdapter;
import com.jerry.recipe.calorie.calculator.contentprovider.Provider;
import com.jerry.recipe.calorie.calculator.service.GetFoodSearchService.GetFoodSearchEvent;

public class FoodItemListFragment extends ObservableListFragment implements LoaderCallbacks<Cursor> {

	private FoodSearchListAdapter mAdapter;
	
	protected static final int LOAD_FOOD_SEARCH = 1;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		final View view = super.onCreateView(inflater, container, savedInstanceState);
		
		mAdapter = new FoodSearchListAdapter(getActivity(), null, false);
	    setListAdapter(mAdapter);
		
		return view;
	}
	
	
	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		
		Loader<Cursor> loader = null;

        switch (arg0) {

            case LOAD_FOOD_SEARCH:
            	loader = new CursorLoader(getActivity(), Provider.FOODSEARCHITEM_CONTENT_URI, null, null, null, null);
                break;
        }

        return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		
		if (cursor != null && cursor.moveToFirst()) {

            switch (loader.getId()) {

                case LOAD_FOOD_SEARCH:
                    mAdapter.swapCursor(cursor);
                    break;
            }
        }
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		mAdapter.swapCursor(null);
	}
	
	public void onEventMainThread(GetFoodSearchEvent response) {
		getLoaderManager().restartLoader(LOAD_FOOD_SEARCH, null, this);
	}

}
