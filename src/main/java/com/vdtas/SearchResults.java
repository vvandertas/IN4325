package com.vdtas;

import java.util.HashMap;

/**
 * Container class for search results encapsulates relevant headers and JSON data
 * @author vvandertas
 */
public class SearchResults {
    HashMap<String, String> relevantHeaders;
    String jsonResponse;

    SearchResults(HashMap<String, String> headers, String json) {
      relevantHeaders = headers;
      jsonResponse = json;
    }

  public SearchResults() {}
}