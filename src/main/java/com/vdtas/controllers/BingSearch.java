package com.vdtas.controllers;

import java.net.*;
import java.util.*;
import java.io.*;
import javax.inject.Inject;
import javax.inject.Named;
import javax.net.ssl.HttpsURLConnection;

import com.vdtas.SessionException;
import com.vdtas.helpers.SessionHelper;
import com.vdtas.models.SearchResults;
import org.jooby.Request;
import org.jooby.Result;
import org.jooby.Results;
import org.jooby.mvc.GET;
import org.jooby.mvc.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/search")
public class BingSearch {

    /**
     * Bing Subscription Key
     */
    private final String subscriptionKey;
    /**
     * Bing API host and path used for placing search requests
     */
    private static String host = "https://api.cognitive.microsoft.com";
    private static String path = "/bing/v7.0/search";
    private final Logger logger = LoggerFactory.getLogger(BingSearch.class);

    @Inject
    public BingSearch(@Named("bing.apiKey") String apiKey) {
        subscriptionKey = apiKey;
    }

    @GET
    public Result processRequest(Request request) {
        // Increment search count
        try {
            SessionHelper.incrementQueryCount(request.session());
        } catch (SessionException se) {
            logger.error("Unable to increment query count.", se);
        }

        SearchResults result = new SearchResults();
        logger.info("Received request");

        try {
            String query = request.param("query").value();
            int offset = request.param("offset").intValue();
            int count = request.param("count").intValue();

            result = searchWeb(query, offset, count);

        } catch (Exception e) {
            logger.error("An error occurred while processing search request", e);
        }

        return Results.json(result);
    }

    /**
     * Perform the Bing API WebSearch request
     *
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
        for (String header : headers.keySet()) {
            if (header == null) continue;      // may have null key
            if (header.startsWith("BingAPIs-") || header.startsWith("X-MSEdge-")) {
                results.relevantHeaders.put(header, headers.get(header).get(0));
            }
        }

        stream.close();
        return results;
    }
}