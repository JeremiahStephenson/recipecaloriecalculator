package com.jerry.recipe.calorie.calculator.response;

import java.util.ArrayList;

public class CollectionResponse<TItem> {

    private int total_results;
    private int max_results;
    private int page_number;
    
    protected ArrayList<TItem> mItems;
    
    public CollectionResponse() {}
    
    public int getTotalResults() {
        return total_results;
    }

    public void setTotalResults(int totalResults) {
        this.total_results = totalResults;
    }

    public int getMaxResults() {
        return max_results;
    }

    public void setMaxResults(int maxResults) {
        this.max_results = maxResults;
    }

    public int getPageNumber() {
        return page_number;
    }

    public void setPageNumber(int pageNumber) {
        this.page_number = pageNumber;
    }
    
    public ArrayList<TItem> getItems() {
        return mItems;
    }

    public void setItems(ArrayList<TItem> items) {
        mItems = items;
    }
}
