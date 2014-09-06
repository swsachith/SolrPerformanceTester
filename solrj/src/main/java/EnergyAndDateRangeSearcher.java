import org.apache.commons.lang.math.RandomUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class EnergyAndDateRangeSearcher extends SolrJSearcher {
    public static void main(String[] args) throws SolrServerException {
        HttpSolrServer server = new HttpSolrServer("http://localhost:8080/solr");
        energyAndDateRangeQueries(server);

    }
    public static void energyAndDateRangeQueries(HttpSolrServer server) throws SolrServerException {
        final int QUERIES = 1;
        long totalNumberOfResults = 0, totalExecutionTime = 0;
        double average_time_perResult = 0.0;

        System.out.println("--------======\t Starting Date & Energy Range Queries =======--------");

        long numberOfResults = 0, executionTime = 0;
        for (int i = 0; i < QUERIES; i++) {
            long beginTime, endTime;
            double time_per_result = 0;

            //create a random date and convert it to the Solr date format
            Date date = new Date(Math.abs(System.currentTimeMillis() - RandomUtils.nextLong()));
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            df.setTimeZone(TimeZone.getTimeZone("UTC"));

            //creating random energy levels
            int finalEnergy = RandomUtils.nextInt(10000);

            //constructing the query
            SolrQuery query = constructQuery("test", "createdDate:[" + df.format(date) + " TO *] AND " +
                    "finalEnergy:[0 TO " + finalEnergy + "]");

            //executing the query
            beginTime = Calendar.getInstance().getTimeInMillis();
            QueryResponse response = server.query(query);
            endTime = Calendar.getInstance().getTimeInMillis();

            numberOfResults += response.getResults().getNumFound();
            executionTime += endTime - beginTime;
        }
        printResults("createdDate date TO * and FinalEnergy 0 To *", numberOfResults, executionTime);
        totalNumberOfResults += numberOfResults;
        totalExecutionTime += executionTime;

        numberOfResults = 0;
        executionTime = 0;
        for (int i = 0; i < QUERIES; i++) {
            long beginTime, endTime;
            double time_per_result = 0;

            //create a random date and convert it to the Solr date format
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            df.setTimeZone(TimeZone.getTimeZone("UTC"));

            Date date_1 = new Date(Math.abs(System.currentTimeMillis() - RandomUtils.nextLong()));
            Date date_2 = new Date(Math.abs(System.currentTimeMillis() - RandomUtils.nextLong()));

            //swapping the dates to get before TO after
            Date temp;
            if (date_1.after(date_2)) {
                temp = date_1;
                date_1 = date_2;
                date_2 = temp;
            }
            //creating random energy levels
            int finalEnergy_1 = RandomUtils.nextInt(10000);
            int finalEnergy_2 = RandomUtils.nextInt(10000);

            //getting the max of the final energy
            //swapping only if the first one is larger
            if (finalEnergy_1 > finalEnergy_2) {
                int temp_1 = finalEnergy_1;
                finalEnergy_1 = finalEnergy_2;
                finalEnergy_2 = temp_1;
            }
            //constructing the query
            SolrQuery query = constructQuery("test", "createdDate:[" + df.format(date_1) + " TO " + df.format(date_2) + "] " +
                    "AND finalEnergy:[" + finalEnergy_1 + " TO " + finalEnergy_2 + "]");

            //executing the query
            beginTime = Calendar.getInstance().getTimeInMillis();
            QueryResponse response = server.query(query);
            endTime = Calendar.getInstance().getTimeInMillis();

            numberOfResults += response.getResults().getNumFound();
            executionTime += endTime - beginTime;
        }
        printResults("CreatedDate: date To date AND finalEnergy: int TO int", numberOfResults, executionTime);
        totalNumberOfResults += numberOfResults;
        totalExecutionTime += executionTime;


        //a new query execution
        numberOfResults = 0;
        executionTime = 0;
        for (int i = 0; i < QUERIES; i++) {
            long beginTime, endTime;
            double time_per_result = 0;

            //create a random date and convert it to the Solr date format
            Date date = new Date(Math.abs(System.currentTimeMillis() - RandomUtils.nextLong()));
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            df.setTimeZone(TimeZone.getTimeZone("UTC"));

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
            SolrQuery query = constructQuery("test", "createdDate:[NOW TO " + df.format(date) + "] " +
                    "AND finalEnergy:[" + finalEnergy_1 + " TO " + finalEnergy_2 + "]");

            //executing the query
            beginTime = Calendar.getInstance().getTimeInMillis();
            QueryResponse response = server.query(query);
            endTime = Calendar.getInstance().getTimeInMillis();

            numberOfResults += response.getResults().getNumFound();
            executionTime += endTime - beginTime;
        }
        printResults("createdDate NOW TO date AND finalEnergy: int TO int", numberOfResults, executionTime);
        totalNumberOfResults += numberOfResults;
        totalExecutionTime += executionTime;


        numberOfResults = 0;
        executionTime = 0;
        for (int i = 0; i < QUERIES; i++) {
            long beginTime, endTime;
            double time_per_result = 0;

            //create a random date and convert it to the Solr date format
            Date date = new Date(Math.abs(System.currentTimeMillis() - RandomUtils.nextLong()));
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            df.setTimeZone(TimeZone.getTimeZone("UTC"));

            //creating random energy levels
            int finalEnergy = RandomUtils.nextInt(10000);

            //constructing the query
            SolrQuery query = constructQuery("test", "finalEnergy:["+finalEnergy+" TO *] AND " +
                    "createdDate:[" + df.format(date) + " TO *]");

            //executing the query
            beginTime = Calendar.getInstance().getTimeInMillis();
            QueryResponse response = server.query(query);
            endTime = Calendar.getInstance().getTimeInMillis();

            numberOfResults += response.getResults().getNumFound();
            executionTime += endTime - beginTime;
        }
        printResults("finalEnergy: int TO * AND createdDate date TO *", numberOfResults, executionTime);
        totalNumberOfResults += numberOfResults;
        totalExecutionTime += executionTime;

        numberOfResults = 0;
        executionTime = 0;
        for (int i = 0; i < QUERIES; i++) {
            long beginTime, endTime;
            double time_per_result = 0;

            //create a random date and convert it to the Solr date format
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            df.setTimeZone(TimeZone.getTimeZone("UTC"));

            Date date_1 = new Date(Math.abs(System.currentTimeMillis() - RandomUtils.nextLong()));
            Date date_2 = new Date(Math.abs(System.currentTimeMillis() - RandomUtils.nextLong()));

            //swapping the dates to get before TO after
            Date temp;
            if (date_1.after(date_2)) {
                temp = date_1;
                date_1 = date_2;
                date_2 = temp;
            }
            //creating random energy levels
            int finalEnergy_1 = RandomUtils.nextInt(10000);
            int finalEnergy_2 = RandomUtils.nextInt(10000);

            //getting the max of the final energy
            //swapping only if the first one is larger
            if (finalEnergy_1 > finalEnergy_2) {
                int temp_1 = finalEnergy_1;
                finalEnergy_1 = finalEnergy_2;
                finalEnergy_2 = temp_1;
            }
            //constructing the query
            SolrQuery query = constructQuery("test", "finalEnergy:[" + finalEnergy_1 + " TO " + finalEnergy_2 + "] "+
                    "AND createdDate:[ "+ df.format(date_1) + " TO  *]");

            //executing the query
            beginTime = Calendar.getInstance().getTimeInMillis();
            QueryResponse response = server.query(query);
            endTime = Calendar.getInstance().getTimeInMillis();

            numberOfResults += response.getResults().getNumFound();
            executionTime += endTime - beginTime;
        }
        printResults("finalEnergy int TO int AND CreatedDate: date To *", numberOfResults, executionTime);
        totalNumberOfResults += numberOfResults;
        totalExecutionTime += executionTime;


        //printing the final results
        System.out.println("\nTotal Time: " + totalExecutionTime + "\tTotal Results: " + totalNumberOfResults);
        average_time_perResult = ((totalNumberOfResults > 0) ? (double) totalExecutionTime / totalNumberOfResults : 0);

        System.out.println("Average date range query time per experiment: " + average_time_perResult);

        System.out.println("--------======\t End Energy and Date Range Queries =======--------\n");


    }
}
