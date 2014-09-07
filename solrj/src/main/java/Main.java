import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

public class Main {
    public static void main(String[] args) throws SolrServerException {
        HttpSolrServer server = new HttpSolrServer("http://localhost:8080/solr");
        System.out.println("Running the queries for 1 million records");
        DateRangeSearcher.testDateRangeQueries(server,1000);
        EnergyRangeSearcher.energyRangeSearch(server,1000);
        EnergyAndDateRangeSearcher.energyAndDateRangeQueries(server,600);
        SubstringSearcher.searchSubstrings(server, 3000);
        ComposedSearcher.composedSearch(server,1000);
    }
}
