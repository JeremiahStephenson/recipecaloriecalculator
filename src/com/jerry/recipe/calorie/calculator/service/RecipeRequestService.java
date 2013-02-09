package com.jerry.recipe.calorie.calculator.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Map;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.jerry.recipe.calorie.calculator.exceptions.FatSecretException;
import com.jerry.recipe.calorie.calculator.request.RecipeAuthentication;
import com.jerry.recipe.calorie.calculator.request.RecipeRequest;
import com.jerry.recipe.calorie.calculator.response.EmptyResponse;
import com.jerry.recipe.calorie.calculator.response.FoodMainResponse;
import com.jerry.recipe.calorie.calculator.service.RequestServiceStatus.RequestServiceStatusType;
import com.jerry.recipe.calorie.calculator.util.FilterParameter;
import com.jerry.recipe.calorie.calculator.util.ServerStatus;

import de.greenrobot.event.EventBus;

public abstract class RecipeRequestService<TRequest extends RecipeRequest<TResponse>, TResponse extends FoodMainResponse<?>> extends
    IntentService {

    public static final String EXTRA_REQUEST = "com.cheil.mubble.EXTRA_REQUEST";

    protected String mName;
    protected EventBus mEventBus;

    protected RecipeRequestService(String name) {
        super(name);

        mName = name;
        mEventBus = EventBus.getDefault();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        RecipeRequest<TResponse> request = intent.getParcelableExtra(EXTRA_REQUEST);

        if (request == null) {
            throw new InvalidParameterException("You must provide a request");
        }

        request.initialize(this);

        ServerResponse response = executeRequest(request);
        
        if (response != null) {
            synchronizeResponse(this, response.status, response.response);
            mEventBus.post(new RequestServiceStatus(mName, RequestServiceStatusType.Completed, response.status));
        } 
    }

    public ServerResponse executeRequest(RecipeRequest<TResponse> request) {
        ServerResponse response = null;
        String message = "Request failed";

        try {
        	
        	ArrayList<FilterParameter> parameters = new ArrayList<FilterParameter>();
        	parameters.add(new FilterParameter("method", request.getRequestMethod()));
        	parameters.addAll(request.getRequestParameters());
        	
        	final URI uri = RecipeAuthentication.appendQueryParams(this, parameters, request.getRequestType());
        	
            switch (request.getRequestType()) {
                case Get:
                	response = doHttpMethodReq(uri.toASCIIString(), "GET", request.getResponseType(), request.buildGson(), null, null); 
                    break;
                case Post:
                    break;
                case Put:
                    break;
                case Delete:
                    break;
                case Patch:
                	break;
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
            message = e.getMessage();
        } catch (FatSecretException fe) {
        	fe.printStackTrace();
        	message = fe.getMessage();
        }

        if (response == null && !request.getResponseType().equals(EmptyResponse.class)) {
            mEventBus.post(new RequestServiceStatus(mName, RequestServiceStatusType.Error, message));
        }

        return response;
    }
    
	private ServerResponse doHttpMethodReq(String url, String requestMethod, Class<TResponse> responseType, Gson gson, 
    								  String paramStr, Map<String, String> header) throws FatSecretException{
    	
    	final StringBuffer sb = new StringBuffer();
    	try {
    		
			final URL purl = new URL(url);
    		final HttpURLConnection conn = (HttpURLConnection) purl.openConnection();
    		conn.setDoOutput((paramStr != null && paramStr.length() > 0) ? true : false);
    		conn.setDoInput(true);
    		if (requestMethod != null)
    			conn.setRequestMethod(requestMethod);
    		
    		if (header != null) {
    			for (String key : header.keySet()) {
    				conn.setRequestProperty(key, header.get(key));
    			}
    		} 
    		
    		// If use POST, must use this
    		OutputStreamWriter wr = null;
    		if (requestMethod != null && !requestMethod.equals("GET") && !requestMethod.equals("DELETE")) {
    			wr = new OutputStreamWriter(conn.getOutputStream());
    			wr.write(paramStr);
    			wr.flush();
    		}
    		
    		// Get the response
    		final BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
    		do {
    			String line = null;
                try{
                	line = br.readLine();
                }
                catch (IOException e) {
                	throw new FatSecretException(1, "An unknown error occurred: 'please try again later'");
                }
    			
    			if (line == null) 
    				break;
    			
    			sb.append(line).append("\n");
    		} while (true);
    		
    		if (wr != null)
    			wr.close();
    		
    		if (br != null)
    			br.close();
    		
    		
    		final String response = sb.toString();
        	final ServerResponse serverResponse = new ServerResponse(gson.fromJson(response, responseType), new ServerStatus(conn.getResponseCode(), conn.getResponseMessage()));	
    		
    		conn.disconnect();
    		
    		return serverResponse;
    		
    	} catch (Exception e) {
    		throw new FatSecretException(1, "An unknown error occurred: 'please try again later'");
    	}
    }
    
    public void synchronizeResponse(Context context, ServerStatus status, TResponse response) {}
    
    protected class ServerResponse {
    	
    	public TResponse response;
    	public ServerStatus status;
    	
    	public ServerResponse(TResponse response, ServerStatus status) {
    		this.response = response;
    		this.status = status;
    	}
    	
    }
}
