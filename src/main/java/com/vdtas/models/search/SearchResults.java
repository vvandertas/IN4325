package com.vdtas.models.search;

import java.util.HashMap;

/**
 * POJO for Bing Web Search API results
 *
 * @author vvandertas
 */
public class SearchResults {
    public HashMap<String, String> relevantHeaders;
    public String jsonResponse;

    public SearchResults(HashMap<String, String> headers, String json) {
      relevantHeaders = headers;
      jsonResponse = json;
    }

  public SearchResults() {}
}