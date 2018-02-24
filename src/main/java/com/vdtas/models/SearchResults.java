package com.vdtas.models;

import java.util.HashMap;

/**
 * Container class for search results encapsulates relevant headers and JSON data
 * @author vvandertas
 */
public class SearchResults {
    public HashMap<String, String> relevantHeaders;
    String jsonResponse;

    public SearchResults(HashMap<String, String> headers, String json) {
      relevantHeaders = headers;
      jsonResponse = json;
    }

  public SearchResults() {}
}