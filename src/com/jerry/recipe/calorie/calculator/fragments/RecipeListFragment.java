package com.jerry.recipe.calorie.calculator.fragments;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockListFragment;

import de.greenrobot.event.EventBus;

public class RecipeListFragment extends SherlockListFragment {

	protected EventBus mEventBus;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEventBus = EventBus.getDefault();
    }
	
	@Override
    public void onResume() {
        super.onResume();

        mEventBus.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mEventBus.unregister(this);
    }
}
