package pl.microservices.demo.elastic.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import pl.microservices.demo.config.ElasticConfigData;

import java.util.Objects;

@Configuration
@EnableElasticsearchRepositories(basePackages = "pl.microservices.demo.elastic.index.client.repository")
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {

    private final ElasticConfigData elasticConfigData;

    public ElasticsearchConfig(ElasticConfigData elasticConfigData) {
        this.elasticConfigData = elasticConfigData;
    }

    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        UriComponents serverUri = UriComponentsBuilder.fromHttpUrl(elasticConfigData.getConnectionUrl()).build();

        HttpHost httpHost = new HttpHost(
                Objects.requireNonNull(serverUri.getHost()),
                serverUri.getPort(),
                serverUri.getScheme());

        RestClientBuilder restClientBuilder = RestClient.builder(httpHost)
                .setRequestConfigCallback( builder -> builder
                        .setConnectTimeout(elasticConfigData.getConnectionTimeoutMs())
                        .setSocketTimeout(elasticConfigData.getSocketTimeoutMs()));

        return new RestHighLevelClient(restClientBuilder);
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate(){
        return new ElasticsearchRestTemplate(elasticsearchClient());
    }
}
