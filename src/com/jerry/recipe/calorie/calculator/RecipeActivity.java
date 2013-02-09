package com.jerry.recipe.calorie.calculator;

import org.springframework.http.HttpStatus;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.jerry.recipe.calorie.calculator.service.RequestServiceStatus;
import com.jerry.recipe.calorie.calculator.service.RequestServiceStatus.RequestServiceStatusType;

import de.greenrobot.event.EventBus;

public class RecipeActivity extends SherlockFragmentActivity {

	protected EventBus mEventBus;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEventBus = EventBus.getDefault();
    }
	
	@Override
    protected void onResume() {
        super.onResume();

        mEventBus.register(this);

        RequestServiceStatus requestServiceStatus = (RequestServiceStatus)mEventBus.removeStickyEvent(RequestServiceStatus.class);
        
        if (requestServiceStatus != null) {
            onEventMainThread(requestServiceStatus);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mEventBus.unregister(this);
    }
    
    public void onEventMainThread(RequestServiceStatus requestServiceStatus) {
        if (requestServiceStatus.getStatusType() == RequestServiceStatusType.Error) {
            AlertDialog.Builder builder = new Builder(this).setTitle("Error!")
                .setMessage("Something went wrong.  Details:\n\n" + requestServiceStatus.getData()).setPositiveButton(R.string.ok, null);
            builder.show();
        }
    }

    public void onEventMainThread(HttpStatus status) {
        if (RecipeApp.IS_DEVELOPMENT) {
            if (status != null) {
                Toast.makeText(this, String.format("%s %s: %s", status.getReasonPhrase(), status.name(), status.value() + ""),
                    Toast.LENGTH_SHORT).show();
            }
        }
    }
}
