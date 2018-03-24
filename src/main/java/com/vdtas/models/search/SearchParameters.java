package com.vdtas.models.search;

/**
 * @author vvandertas
 */
public class SearchParameters {
    private String where;
    private String when;

    private String searchQuery;
    private String searchFlags;
    private int offset;
    private int count;

    public SearchParameters(String searchQuery, String searchFlags, int offset, int count) {
        this.searchQuery = searchQuery;
        this.searchFlags = searchFlags;
        this.offset = offset;
        this.count = count;
    }

    public String getSearchQuery() {
        return searchQuery + " " + searchFlags;
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
