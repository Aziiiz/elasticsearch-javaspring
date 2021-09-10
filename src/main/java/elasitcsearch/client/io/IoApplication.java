package elasitcsearch.client.io;

import elasitcsearch.client.io.client.EsClient;
import elasitcsearch.client.io.domain.EsDocument;
import elasitcsearch.client.io.index.DocumentEntryController;
import elasitcsearch.client.io.index.IndexController;
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
//		indexDocument(esClient);
		indexDocument(esClient);

		IndexController indexController = new IndexController(esClient);




	}

	public static void indexDocument(EsClient esClient) {
		try {
			DocumentEntryController entryController = new DocumentEntryController(esClient);
			entryController.createDocument(new EsDocument("A", "A1", 20, "lorem ipsum"), indexName, typname);
		}catch (final Exception e) {
			log.error("EXCEPTION: ", e);
		}
	}



}
