package pl.microservices.demo.reactive.elastic.query.service.business.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.microservices.demo.config.ElasticQueryServiceConfigData;
import pl.microservices.demo.elastic.model.index.impl.TwitterIndexModel;
import pl.microservices.demo.reactive.elastic.query.service.business.ReactiveElasticQueryClient;
import pl.microservices.demo.reactive.elastic.query.service.repository.ElasticQueryRepository;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Service
public class TwitterReactiveElasticQueryClient implements ReactiveElasticQueryClient<TwitterIndexModel> {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterReactiveElasticQueryClient.class);

    private final ElasticQueryRepository elasticQueryRepository;
    private final ElasticQueryServiceConfigData elasticQueryServiceConfigData;

    public TwitterReactiveElasticQueryClient(ElasticQueryRepository elasticQueryRepository, ElasticQueryServiceConfigData elasticQueryServiceConfigData) {
        this.elasticQueryRepository = elasticQueryRepository;
        this.elasticQueryServiceConfigData = elasticQueryServiceConfigData;
    }

    @Override
    public Flux<TwitterIndexModel> getIndexModelByText(String text) {
        LOG.info("Getting data from elasticsearch for text: '{}'", text);
        return elasticQueryRepository
                .findByText(text)
                .delayElements(Duration.ofMillis(elasticQueryServiceConfigData.getBackPressureDelayMs()));
    }
}
