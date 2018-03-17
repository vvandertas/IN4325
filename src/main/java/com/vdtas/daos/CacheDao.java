package com.vdtas.daos;

import com.vdtas.models.search.SearchParameters;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

/**
 * @author vvandertas
 */
public interface CacheDao {

    /**
     * Retrieve cached results for these search parameters
     *
     * @param searchParameters the search parameters
     * @return cached results json or null
     */
    @SqlQuery("SELECT results FROM search_caching WHERE start = :offset AND count =:count AND query =:searchQuery")
    String getResultsBySearchParam(@BindBean SearchParameters searchParameters);

    /**
     * Cache results json
     */
    @SqlUpdate("INSERT INTO search_caching(results, start, count, query) VALUES(:resultsJson, :offset, :count, :searchQuery);")
    void insertCache(String resultsJson, int offset, int count, String searchQuery);
}
