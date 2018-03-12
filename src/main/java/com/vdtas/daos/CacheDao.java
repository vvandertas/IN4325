package com.vdtas.daos;

import com.vdtas.models.SearchCache;
import com.vdtas.models.search.SearchParameters;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

/**
 * @author vvandertas
 */
public interface CacheDao {

    @SqlQuery("SELECT results FROM search_caching WHERE start = :offset AND count =:count AND query =:searchQuery")
    String getResultsBySearchParam(@BindBean SearchParameters searchParameters);

    @SqlUpdate("INSERT INTO search_caching(results, start, count, query) VALUES(:resultsJson, :offset, :count, :searchQuery);")
    void insertCache(@Bind("resultsJson") String resultsJson, @Bind("offset") int offset, @Bind("count") int count, @Bind("searchQuery") String query);


}
