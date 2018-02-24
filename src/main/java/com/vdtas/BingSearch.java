package com.vdtas;

import java.net.*;
import java.util.*;
import java.io.*;
import javax.inject.Inject;
import javax.inject.Named;
import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jooby.Request;
import org.jooby.Result;
import org.jooby.Results;
import org.jooby.mvc.GET;
import org.jooby.mvc.Path;

@Path("/search")
public class BingSearch {

  /**
   * Bing Subscription Key
   */
  final String subscriptionKey;
  /**
   * Bing API host and path used for placing search requests
   */
  static String host = "https://api.cognitive.microsoft.com";
  static String path = "/bing/v7.0/search";

  @Inject
  public BingSearch( @Named("bing.apiKey") String apiKey) {
    System.out.println("Api key: " + apiKey);
    subscriptionKey = apiKey;
  }

  @GET
  public Result processRequest(Request request) {

    SearchResults result = new SearchResults();
    System.out.println("Received request");
    System.out.println("Using key: " + subscriptionKey);

    try {
      String query = request.param("query").value();
      int offset = request.param("offset").intValue();
      int count = request.param("count").intValue();

      result = searchWeb(query, offset, count);

    } catch(Exception e) {
      e.printStackTrace();
    }

    return Results.json(result);
  }

  /**
   * Perform the Bing API WebSearch request
   * @param searchQuery
   * @return
   * @throws Exception
   */
  private SearchResults searchWeb(String searchQuery, Integer offset, Integer count) throws Exception {
    String getParams = "?q=" + URLEncoder.encode(searchQuery, "UTF-8");
    getParams += "&offset=" + (offset != null ? String.valueOf(offset) : "0");
    getParams += "&count=" + (count != null ? String.valueOf(count) : "20"); // default to 20 results
    getParams += "&responseFilter=Webpages"; // Only return web pages

    // TODO: include more?
    // construct URL of search request (endpoint + query string)
    URL url = new URL(host + path + getParams);

    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
    connection.setRequestProperty("Ocp-Apim-Subscription-Key", subscriptionKey);

    // receive JSON body
    InputStream stream = connection.getInputStream();
    String response = new Scanner(stream).useDelimiter("\\A").next();

    // construct result object for return
    SearchResults results = new SearchResults(new HashMap<>(), response);

    // extract Bing-related HTTP headers
    Map<String, List<String>> headers = connection.getHeaderFields();
    for(String header : headers.keySet()) {
      if(header == null) continue;      // may have null key
      if(header.startsWith("BingAPIs-") || header.startsWith("X-MSEdge-")) {
        results.relevantHeaders.put(header, headers.get(header).get(0));
      }
    }

    stream.close();
    return results;
  }

  // pretty-printer for JSON; uses GSON parser to parse and re-serialize
  public static String prettify(String json_text) {
    JsonParser parser = new JsonParser();
    JsonObject json = parser.parse(json_text).getAsJsonObject();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    return gson.toJson(json);
  }
}