package elasitcsearch.client.io.index;

import elasitcsearch.client.io.client.EsClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class IndexController {
    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    private EsClient esClient;

    public IndexController(EsClient esClient) {
        this.esClient = esClient;
    }

    public void createIndex(String indexName, String type) {
        try {
            CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
            createIndexRequest.settings(Settings.builder()
                    .put("index.number_of_shards", 3)
                    .put("index.number_of_replicas", 2));
            createIndexRequest.mapping(getMapping(type));
            final CreateIndexResponse createIndexRequestResponse = esClient.getClient().indices().create(createIndexRequest, RequestOptions.DEFAULT);
            log.info("INDEX IS CREATED....");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private XContentBuilder getMapping(String type) throws IOException {
        XContentBuilder mappingbuilder = XContentFactory.jsonBuilder().startObject()
                .startObject("firstname").field("type", "string").endObject()
                .startObject("lastname").field("type", "string").endObject()
                .startObject("age").field("type", "integer").endObject()
                .startObject("description").field("type", "string").endObject()
                .endObject().endObject().endObject();
        return mappingbuilder;
    }
}
