package com.vdtas.models;

/**
 * @author vvandertas
 */
public class SearchCache {
    private int offset;
    private int count;
    private String query;
    private String results;

    public SearchCache(int offset, int count, String query, String results) {
        this.offset = offset;
        this.count = count;
        this.query = query;
        this.results = results;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }
}
