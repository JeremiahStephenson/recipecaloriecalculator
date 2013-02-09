package com.jerry.recipe.calorie.calculator.service;

import org.springframework.http.HttpStatus;

public interface StatusRestTemplate {
    HttpStatus getHttpStatus();
}
