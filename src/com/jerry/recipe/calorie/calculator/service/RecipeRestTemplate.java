package com.jerry.recipe.calorie.calculator.service;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import com.jerry.recipe.calorie.calculator.response.EmptyResponse;

import de.greenrobot.event.EventBus;

public class RecipeRestTemplate<TResponse> extends RestTemplate implements StatusRestTemplate {
    Class<TResponse> mResponseType;
    private HttpStatus mStatusCode;
    
    public RecipeRestTemplate(Class<TResponse> responseType) {
        mResponseType = responseType;

        List<HttpMessageConverter<?>> messageConverters = getMessageConverters();
        MappingJacksonHttpMessageConverter jsonConverter = new MappingJacksonHttpMessageConverter();

        List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
        supportedMediaTypes.add(new MediaType("application", "json"));
        supportedMediaTypes.add(new MediaType("text", "html"));

        jsonConverter.setSupportedMediaTypes(supportedMediaTypes);
        messageConverters.add(jsonConverter);
        
        setMessageConverters(messageConverters);
    }

    @Override
    protected <T> T doExecute(URI url, HttpMethod method, RequestCallback requestCallback, ResponseExtractor<T> responseExtractor) {
        ClientHttpResponse response = null;
        
        mStatusCode = HttpStatus.METHOD_FAILURE;

        T extractedResponse = null;
        
        try {
            final ClientHttpRequest request = createRequest(url, method);

            if (requestCallback != null) {
                requestCallback.doWithRequest(request);
            }

            response = request.execute();

            mStatusCode = response.getStatusCode();

            if (mStatusCode.value() >= HttpStatus.OK.value() && mStatusCode.value() < HttpStatus.MULTIPLE_CHOICES.value()
                && !mResponseType.equals(EmptyResponse.class) && responseExtractor != null) {
                extractedResponse = responseExtractor.extractData(response);
            } else{
            	EventBus eventBus = EventBus.getDefault();
            	eventBus.post(mStatusCode);
            }
           
        } catch (final IOException ex) {
            ex.printStackTrace();
        } catch (final Exception e) {
        	e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
        }

        return extractedResponse;
    }
    
    @Override
    public HttpStatus getHttpStatus() {
        return mStatusCode;
    }
}
