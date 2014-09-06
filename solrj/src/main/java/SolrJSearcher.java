import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.util.Calendar;

public class SolrJSearcher {

    public static SolrQuery constructQuery(String query_text, String filterQuery) {
        SolrQuery query = new SolrQuery();
        query.setQuery(query_text);
        query.addFilterQuery(filterQuery);
        query.setStart(0);
        query.set("defType", "edismax");
        return query;

    }

    //get the execution time for the query
    public static double executeQuery(String descriptopn, SolrQuery query, HttpSolrServer server) throws SolrServerException {

        long beginTime, endTime, numberOfResults, executionTime;
        double time_per_result = 0;
        //executing the query
        beginTime = Calendar.getInstance().getTimeInMillis();
        QueryResponse response = server.query(query);
        endTime = Calendar.getInstance().getTimeInMillis();

        numberOfResults = response.getResults().getNumFound();
        executionTime = endTime - beginTime;
        time_per_result = ((numberOfResults > 0) ? ((double) executionTime / numberOfResults) : 0.0);

        System.out.println("==============\t" + descriptopn + "\t================");
        System.out.println("Total Execution Time : " + executionTime);
        System.out.println("Number of results : " + numberOfResults);
        System.out.println("time per result : " + time_per_result);

        return time_per_result;

       /* SolrDocumentList results = response.getResults();
        for (int i = 0; i < results.size(); ++i) {
            System.out.println(results.get(i));
        }*/

    }

    public static void printResults(String description,long number_of_results, long execution_time) {
        System.out.println("\n----"+description+"---");
        System.out.println("Number of results: \t"+number_of_results);
        System.out.println("Time taken :\t"+execution_time);
    }


}


/*
* range query on Date, energy
* substring searching
* basic field querying
* & queries
* full text searching
* */