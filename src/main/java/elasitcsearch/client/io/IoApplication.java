package elasitcsearch.client.io;

import elasitcsearch.client.io.client.EsClient;
import elasitcsearch.client.io.domain.EsDocument;
import elasitcsearch.client.io.index.DocumentEntryController;
import elasitcsearch.client.io.index.IndexController;
import elasitcsearch.client.io.index.QueryController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IoApplication {
	public static final String indexName = "person";
	public static final String typname = "document";
	private static final Logger log = LoggerFactory.getLogger(IoApplication.class);


	private static String host;
	private static int port;

	public IoApplication(@Value("${elasticsearch.host}") String host, @Value("${elasticsearch.port}") int port) {
		this.host = host;
		this.port = port;
	}

	public static void main(String[] args) {
		SpringApplication.run(IoApplication.class, args);
		System.out.println(host+ " "+port);
		EsClient esClient = new EsClient(host, port);
		indexDocument(esClient);

		IndexController indexController = new IndexController(esClient);
		QueryController queryController = new QueryController(esClient);
		queryController.queryTerm(indexName,typname,"jemmy");
		queryController.queryFuzzy(indexName,typname, "Jemmy");



	}

	public static void indexDocument(EsClient esClient) {
		try {
			DocumentEntryController entryController = new DocumentEntryController(esClient);
			entryController.createDocument(new EsDocument("Alice", "Brown", 20, "lorem ipsum"), indexName, typname);
			entryController.createDocument(new EsDocument("Bob", "Grown", 20, "ipsum lorea"), indexName, typname);
			entryController.createDocument(new EsDocument("Steve", "Voznyak", 20, "lorem ipsum"), indexName, typname);
			entryController.createDocument(new EsDocument("Jemmy", "Thomson", 20, "lorem ipsum"), indexName, typname);
		}catch (final Exception e) {
			log.error("EXCEPTION: ", e);
		}
	}



}
