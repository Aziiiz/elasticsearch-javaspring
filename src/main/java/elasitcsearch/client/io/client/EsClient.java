package elasitcsearch.client.io.client;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

public class EsClient {

    private RestHighLevelClient client;

    public EsClient(String host, int port) {
        client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(host, port, "http")
                )
        );
    }

    public RestHighLevelClient getClient() {
        return client;
    }

    public void shutdown() throws IOException {
        client.close();
        client = null;
    }
}
