import org.apache.commons.lang.math.RandomUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.util.Calendar;

public class EnergyRangeSearcher extends SolrJSearcher {
    public static void main(String[] args) throws SolrServerException {
        HttpSolrServer server = new HttpSolrServer("http://localhost:8080/solr");
        energyRangeSearch(server,100);
    }
    public static void energyRangeSearch(HttpSolrServer server, final int numberOfQueries) throws SolrServerException {
        long totalNumberOfResults = 0, totalExecutionTime = 0;
        double average_time_perResult = 0.0;

        System.out.println("--------======\t Starting Energy Range Queries =======--------");

        long numberOfResults = 0, executionTime = 0;
        for (int i = 0; i < numberOfQueries; i++) {
            long beginTime, endTime;
            double time_per_result = 0;

            //creating random energy levels
            int finalEnergy = RandomUtils.nextInt(10000);

            //constructing the query
            SolrQuery query = constructQuery("test", "finalEnergy:[" + finalEnergy + " TO *]");

            //executing the query
            beginTime = Calendar.getInstance().getTimeInMillis();
            QueryResponse response = server.query(query);
            endTime = Calendar.getInstance().getTimeInMillis();

            numberOfResults += response.getResults().getNumFound();
            executionTime += endTime - beginTime;
        }
        printResults("createdDate date TO *", numberOfResults, executionTime);
        totalNumberOfResults += numberOfResults;
        totalExecutionTime += executionTime;

        numberOfResults = 0;
        executionTime = 0;
        for (int i = 0; i < numberOfQueries; i++) {
            long beginTime, endTime;
            double time_per_result = 0;

            //creating random energy levels
            int finalEnergy_1 = RandomUtils.nextInt(10000);
            int finalEnergy_2 = RandomUtils.nextInt(10000);

            //getting the max of the final energy
            //swapping only if the first one is larger
            if (finalEnergy_1 > finalEnergy_2) {
                int temp = finalEnergy_1;
                finalEnergy_1 = finalEnergy_2;
                finalEnergy_2 = temp;
            }

            //constructing the query
            SolrQuery query = constructQuery("test", "finalEnergy:[" + finalEnergy_1 + " TO "+finalEnergy_2+"]");

            //executing the query
            beginTime = Calendar.getInstance().getTimeInMillis();
            QueryResponse response = server.query(query);
            endTime = Calendar.getInstance().getTimeInMillis();

            numberOfResults += response.getResults().getNumFound();
            executionTime += endTime - beginTime;
        }
        printResults("createdDate date TO *", numberOfResults, executionTime);
        totalNumberOfResults += numberOfResults;
        totalExecutionTime += executionTime;

        numberOfResults = 0;
        executionTime = 0;
        for (int i = 0; i < numberOfQueries; i++) {
            long beginTime, endTime;
            double time_per_result = 0;

            //creating random energy levels
            int finalEnergy = RandomUtils.nextInt(10000);

            //constructing the query
            SolrQuery query = constructQuery("test", "finalEnergy:[ 0 TO "+finalEnergy+"]");

            //executing the query
            beginTime = Calendar.getInstance().getTimeInMillis();
            QueryResponse response = server.query(query);
            endTime = Calendar.getInstance().getTimeInMillis();

            numberOfResults += response.getResults().getNumFound();
            executionTime += endTime - beginTime;
        }
        printResults("createdDate date TO *", numberOfResults, executionTime);
        totalNumberOfResults += numberOfResults;
        totalExecutionTime += executionTime;


        //printing the final results
        System.out.println("\nTotal Time: " + totalExecutionTime + "\tTotal Results: " + totalNumberOfResults);
        average_time_perResult = ((totalNumberOfResults > 0) ? (double) totalExecutionTime / totalNumberOfResults : 0);

        System.out.println("Average date range query time per experiment: " + average_time_perResult);
        System.out.println("--------======\t End Energy Range Queries =======--------\n");

    }
}
