package com.vdtas.models;

import java.util.HashMap;

/**
 * POJO for Bing Web Search API results
 *
 * @author vvandertas
 */
public class SearchResults {
    public HashMap<String, String> relevantHeaders;
    private String jsonResponse;

    public SearchResults(HashMap<String, String> headers, String json) {
      relevantHeaders = headers;
      jsonResponse = json;
    }

  public SearchResults() {}
}