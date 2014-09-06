import org.apache.commons.lang.math.RandomUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class SolrjPopulator {
    public static void main(String[] args) throws IOException, SolrServerException {
        HttpSolrServer server = new HttpSolrServer("http://localhost:8080/solr");
        /*Random randomGenerator = new Random();
        for (int i = 0; i < 1000; ++i) {
            String filename = "GridChem Experiment "+i+" 00993/334"+i;
            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("fileName", filename+".out");
            doc.addField("filePath", "/home/gridchem/projects/"+filename);
            doc.addField("generatedApplicationName", "Gaussian");
            doc.addField("dataArchiveNode", "Trestles");
            doc.addField("createdDate", new Date());
            doc.addField("ownerName", "sudhakar");
            UUID inchi_key = UUID.randomUUID();
            doc.addField("inchi", inchi_key.toString().substring(8));
            doc.addField("inchi_key", inchi_key.toString());
            doc.addField("finalEnergy", randomGenerator.nextInt(10000));

            server.add(doc);
            if (i % 100 == 0) server.commit();  // periodically flush
        }
        server.commit();*/

        int numberOfDocuments = 10000000;
        int numberOfUsers = 8;
        int documents_per_user = (int) Math.floor(numberOfDocuments / numberOfUsers);

        indexNDocuments(documents_per_user, server, "sachith", "gridchemTest", "Gaussian", "gw111", "sachith");
        indexNDocuments(documents_per_user, server, "ccsds", "rpj", "Gaussian", "gw110", "supun");
        indexNDocuments(documents_per_user, server, "wedsd", "sudhakar", "calculator", "gw101", "hirantha");
        indexNDocuments(documents_per_user, server, "asddda", "paramChem-ttyl", "Echo", "gw121", "dinu");
        indexNDocuments(documents_per_user, server, "asdf3r", "c23Po", "Amber", "gw121", "swsachith");
        indexNDocuments(documents_per_user, server, "te322st", "r2p3Test", "Nicol32", "gw127", "sudhakar");
        indexNDocuments(documents_per_user, server, "tes231323t", "starwarsTest", "JFlex", "gw126", "Suresh");
        indexNDocuments(documents_per_user, server, "singlton23323s2233t", "backtotheFuture", "cup22", "gw322",
                "nakandala");

    }

    public static SolrInputDocument createDocument(String filename, String filepath, String generatedApp,
                                                   String dataArchiveNode, Date createdDate, String ownerName,
                                                   String inchi, String inchi_key, int finalEnergy) {
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("fileName", filename);
        doc.addField("filePath", filepath);
        doc.addField("generatedApplicationName", generatedApp);
        doc.addField("dataArchiveNode", dataArchiveNode);
        doc.addField("createdDate", createdDate);
        doc.addField("ownerName", ownerName);
        doc.addField("inchi", inchi);
        doc.addField("inchi_key", inchi_key);
        doc.addField("finalEnergy", finalEnergy);

        return doc;
    }

    public static void indexNDocuments(int n, HttpSolrServer server, String projectName, String filename, String generatedApp,
                                       String dataArchiveNode, String ownerName) throws IOException, SolrServerException {
        System.out.println("==================================================");
        System.out.println("Creating " + n + " documents for the user: " + ownerName);
        for (int i = 0; i < n; ++i) {
            UUID uuid = UUID.randomUUID();
            String inchi_key = uuid.toString();
            String inchi = inchi_key.substring(11);
            String filename_generated = projectName + "_Experiment " + i + " " + filename + "-" + inchi + ".out";
            String filepath = "/home/gridchem/" + projectName + "/" + filename + "-" + inchi;
            Date date = new Date(Math.abs(System.currentTimeMillis() - RandomUtils.nextLong()));
            int finalEnergy = RandomUtils.nextInt(10000);

            SolrInputDocument doc = createDocument(filename_generated, filepath, generatedApp,
                    dataArchiveNode, date, ownerName, inchi, inchi_key, finalEnergy);
            server.add(doc);
            if (i % 100000 == 0) {
                server.commit();  // periodically flush

                System.out.println("created " + i + " documents for the user : " + ownerName +
                        " \tremaining " + (n - i) + " documents ........");
            }

        }
        server.commit();


    }
}
