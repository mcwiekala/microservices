package pl.microservices.demo.reactive.elastic.query.service.business.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.microservices.demo.elastic.model.index.impl.TwitterIndexModel;
import pl.microservices.demo.elastic.query.service.common.model.ElasticQueryServiceResponseModel;
import pl.microservices.demo.elastic.query.service.common.transformer.ElasticToResponseModelTransformer;
import pl.microservices.demo.reactive.elastic.query.service.business.ElasticQueryService;
import pl.microservices.demo.reactive.elastic.query.service.business.ReactiveElasticQueryClient;
import reactor.core.publisher.Flux;

@Service
public class TwitterElasticQueryClient implements ElasticQueryService {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterElasticQueryClient.class);

    private final ReactiveElasticQueryClient<TwitterIndexModel> reactiveElasticQueryClient;
    private final ElasticToResponseModelTransformer elasticToResponseModelTransformer;

    public TwitterElasticQueryClient(ReactiveElasticQueryClient<TwitterIndexModel> reactiveElasticQueryClient, ElasticToResponseModelTransformer elasticToResponseModelTransformer) {
        this.reactiveElasticQueryClient = reactiveElasticQueryClient;
        this.elasticToResponseModelTransformer = elasticToResponseModelTransformer;
    }

    @Override
    public Flux<ElasticQueryServiceResponseModel> getDocumentByText(String text) {
        LOG.info("Querying reactive elasticsearch for text: '{}'", text);
        return reactiveElasticQueryClient
                .getIndexModelByText(text)
                .map(elasticToResponseModelTransformer::getResponseModel);
    }
}
