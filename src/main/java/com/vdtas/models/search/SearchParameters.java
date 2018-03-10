package com.vdtas.models.search;

/**
 * @author vvandertas
 */
public class SearchParameters {
    private String where;
    private String when;

    private String searchQuery;
    private int offset;
    private int count;

    public SearchParameters(String searchQuery, int offset, int count) {
        this.searchQuery = searchQuery;
        this.offset = offset;
        this.count = count;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
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
}
