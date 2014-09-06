import org.apache.commons.lang.math.RandomUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.util.Calendar;
import java.util.UUID;

public class SubstringSearcher extends SolrJSearcher {
    public static void main(String[] args) throws SolrServerException {
        HttpSolrServer server = new HttpSolrServer("http://localhost:8080/solr");
        searchSubstrings(server,100);
    }
    public static void searchSubstrings(HttpSolrServer server, final int numberOfQueries) throws SolrServerException {
        long totalNumberOfResults = 0, totalExecutionTime = 0;
        double average_time_perResult = 0.0;

        System.out.println("--------======\t Starting Substring Queries =======--------");

        long numberOfResults = 0, executionTime = 0;
        for (int i = 0; i < numberOfQueries; i++) {
            long beginTime, endTime;
            double time_per_result = 0;

            UUID uuid = UUID.randomUUID();
            String inchi_key = uuid.toString();
            String inchi = inchi_key.substring(11);

            int first_index = RandomUtils.nextInt(24);
            int second_index = RandomUtils.nextInt(24);

            if (first_index > second_index) {
                int temp = first_index;
                first_index = second_index;
                second_index = temp;
            }

            //constructing the query
            SolrQuery query = constructQuery(inchi_key.substring(first_index,second_index), "");

            //executing the query
            beginTime = Calendar.getInstance().getTimeInMillis();
            QueryResponse response = server.query(query);
            endTime = Calendar.getInstance().getTimeInMillis();

            numberOfResults += response.getResults().getNumFound();
            executionTime += endTime - beginTime;
        }
        printResults("Substring searching ....", numberOfResults, executionTime);
        totalNumberOfResults += numberOfResults;
        totalExecutionTime += executionTime;

        //printing the final results
        System.out.println("\nTotal Time: " + totalExecutionTime + "\tTotal Results: " + totalNumberOfResults);
        average_time_perResult = ((totalNumberOfResults > 0) ? (double) totalExecutionTime / totalNumberOfResults : 0);

        System.out.println("Average date range query time per experiment: " + average_time_perResult);
        System.out.println("--------======\t End Substring Search Queries =======--------\n");
    }
}
