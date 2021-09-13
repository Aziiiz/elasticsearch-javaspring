package elasitcsearch.client.io.index;

import elasitcsearch.client.io.client.EsClient;
import elasitcsearch.client.io.domain.EsDocument;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.join.query.JoinQueryBuilders;
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
            SearchResponse response = esClient.getClient().search(serchBuilder(value, indexName, type), RequestOptions.DEFAULT);
            log.info("RESPONSE FUZZY " +  response.toString());
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public SearchResponse queryTerm(String indexName, String type, String value) {
        try {
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                    .query(
                            QueryBuilders.termQuery("firstName", value)
                    )
                    .from(0).size(60).explain(true);
            SearchRequest searchRequest = new SearchRequest(indexName).source(searchSourceBuilder).searchType(SearchType.DFS_QUERY_THEN_FETCH);
            SearchResponse response = esClient.getClient().search(searchRequest, RequestOptions.DEFAULT);
            log.info("RESPONSE QUERYTERM " + response.toString());
            return response;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }




    // search builder
    private SearchRequest serchBuilder(String value, String index, String type) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .query(
                        QueryBuilders.boolQuery().should(QueryBuilders.boolQuery())
                                .must(QueryBuilders.fuzzyQuery("firstName", value).fuzziness(Fuzziness.TWO).prefixLength(0))
                )
                .from(0).size(60).explain(true);
        return new SearchRequest(index)
                .source(searchSourceBuilder)
                .searchType(SearchType.DFS_QUERY_THEN_FETCH);
    }
}
