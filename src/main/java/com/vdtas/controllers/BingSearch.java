package com.vdtas.controllers;

import java.net.*;
import java.util.*;
import java.io.*;
import javax.inject.Inject;
import javax.inject.Named;
import javax.net.ssl.HttpsURLConnection;

import com.vdtas.daos.CacheDao;
import com.vdtas.helpers.ExperimentHelper;
import com.vdtas.models.search.SearchParameters;
import com.vdtas.models.search.SearchResults;
import com.vdtas.models.User;
import org.jooby.Request;
import org.jooby.Result;
import org.jooby.Results;
import org.jooby.mvc.GET;
import org.jooby.mvc.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.vdtas.helpers.Routes.SEARCH;

@Path(SEARCH)
public class BingSearch {

    /**
     * Bing Subscription Key
     */
    private final String subscriptionKey;
    /**
     * Bing API BING_ENDPOINT and path used for placing search requests
     */
    private static final String BING_ENDPOINT = "https://api.cognitive.microsoft.com/bing/v7.0/search";
    private final Logger logger = LoggerFactory.getLogger(BingSearch.class);
    private ExperimentHelper experimentHelper;
    private final CacheDao cacheDao;

    @Inject
    public BingSearch(@Named("bing.apiKey") String apiKey, ExperimentHelper experimentHelper, CacheDao cacheDao) {
        subscriptionKey = apiKey;
        this.experimentHelper = experimentHelper;
        this.cacheDao = cacheDao;
    }

    @GET
    public Result processRequest(Request request, SearchParameters searchParameters) {
        User user = request.get("user");
        // Increment search count
        experimentHelper.incrementQueryCount(user);

        SearchResults result = new SearchResults();
        logger.info("Received request");

        try {
            String cachedResult = cacheDao.getResultsBySearchParam(searchParameters);
            if(cachedResult != null) {
                result.jsonResponse = cachedResult;
            } else {
                result = searchWeb(searchParameters);
                cacheDao.insertCache(result.jsonResponse, searchParameters.getOffset(), searchParameters.getCount(), searchParameters.getSearchQuery());
            }

        } catch (Exception e) {
            logger.error("An error occurred while processing search request", e);
        }

        return Results.json(result);
    }

    /**
     * Perform the Bing API WebSearch request
     *
     * @param searchParameters all search parameters for this search request.
     * @return
     * @throws Exception
     */
    private SearchResults searchWeb(SearchParameters searchParameters) throws Exception {
        String getParams = "?q=" + URLEncoder.encode(searchParameters.getSearchQuery(), "UTF-8");
        getParams += "&offset=" +  String.valueOf(searchParameters.getOffset());
        getParams += "&count=" + String.valueOf(searchParameters.getCount());
        getParams += "&responseFilter=Webpages"; // Only return web pages

        // TODO: include more?
        // construct URL of search request (endpoint + query string)
        URL url = new URL(BING_ENDPOINT + getParams);

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