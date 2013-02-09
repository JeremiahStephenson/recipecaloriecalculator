package com.jerry.recipe.calorie.calculator.service;

import android.content.Context;

import com.jerry.recipe.calorie.calculator.request.RecipeRequest;
import com.jerry.recipe.calorie.calculator.response.FoodMainResponse;
import com.jerry.recipe.calorie.calculator.service.RequestServiceProgress.ProgressType;
import com.jerry.recipe.calorie.calculator.util.ServerStatus;

public abstract class ProgressRequestService<TRequest extends RecipeRequest<TResponse>, TResponse extends FoodMainResponse<?>, TProgress extends RequestServiceProgress>
    extends RecipeRequestService<TRequest, TResponse> {

    private Class<TProgress> mServiceProgressType;

    protected ProgressRequestService(String name, Class<TProgress> serviceProgressType) {
        super(name);

        mServiceProgressType = serviceProgressType;
    }

    @Override
    public ServerResponse executeRequest(RecipeRequest<TResponse> request) {
        postProgress(ProgressType.StartRequest);
        ServerResponse response = super.executeRequest(request);
        postProgress(ProgressType.EndRequest);

        return response;
    }

    @Override
    public void synchronizeResponse(Context context, ServerStatus status, TResponse response) {
        postProgress(ProgressType.StartSynchronize);
        super.synchronizeResponse(context, status, response);
        postProgress(ProgressType.EndSynchronize);
    }

    private void postProgress(ProgressType progressType) {
        TProgress progress = null;

        try {
            progress = mServiceProgressType.newInstance();
            progress.setProgressType(progressType);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (progress != null) {
            mEventBus.post(progress);
        }
    }
}
