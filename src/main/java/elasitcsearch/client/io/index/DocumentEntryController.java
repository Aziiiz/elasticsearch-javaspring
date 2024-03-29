package elasitcsearch.client.io.index;

import com.fasterxml.jackson.databind.ObjectMapper;
import elasitcsearch.client.io.client.EsClient;
import elasitcsearch.client.io.domain.EsDocument;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DocumentEntryController {

    private static final Logger log = LoggerFactory.getLogger(DocumentEntryController.class);
    private EsClient esClient;

    public DocumentEntryController(EsClient esClient) {
        this.esClient = esClient;
    }

    public void createDocument(EsDocument document, String indexName, String type) {
        String documentJson = null;
        try {
            documentJson = new ObjectMapper().writeValueAsString(document);
            System.out.println(documentJson);
            IndexRequest indexRequest = new IndexRequest(indexName).type(type);
            indexRequest.source(documentJson, XContentType.JSON);
            esClient.getClient().index(indexRequest, RequestOptions.DEFAULT);
            log.info("DOCUMENT indexed ....");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
