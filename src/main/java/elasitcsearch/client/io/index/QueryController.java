package elasitcsearch.client.io.index;

import elasitcsearch.client.io.client.EsClient;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class QueryController {
    private static final Logger log = LoggerFactory.getLogger(QueryController.class);

    private EsClient esClient;

    public QueryController(EsClient esClient) {
        this.esClient = esClient;
    }

    public SearchResponse queryFuzzy(String indexName, String type, String value) {
        try {
            SearchResponse response = esClient.getClient().search(serchBuilder(value), RequestOptions.DEFAULT);
            log.info("RESPONSE " +  response.toString());
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    // search builder
    private SearchRequest serchBuilder(String value) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .query(
                        QueryBuilders.boolQuery().should(QueryBuilders.boolQuery())
                                .must(QueryBuilders.fuzzyQuery("firstname", value).fuzziness(Fuzziness.TWO).prefixLength(0))
                )
                .from(0).size(60).explain(true);
        return new SearchRequest()
                .source(searchSourceBuilder)
                .searchType(SearchType.DFS_QUERY_THEN_FETCH);
    }
}
