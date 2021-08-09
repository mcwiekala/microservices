package pl.microservices.demo.elastic.query.service.business.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.microservices.demo.elastic.model.index.impl.TwitterIndexModel;
import pl.microservices.demo.elastic.query.client.service.ElasticQueryClient;
import pl.microservices.demo.elastic.query.service.business.ElasticQueryService;
import pl.microservices.demo.elastic.query.service.common.model.ElasticQueryServiceResponseModel;
import pl.microservices.demo.elastic.query.service.model.assembler.ElasticQueryServiceResponseModelAssembler;

import java.util.List;

@Service
public class TwitterElasticQueryService implements ElasticQueryService {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterElasticQueryService.class);

    private final ElasticQueryServiceResponseModelAssembler elasticQueryServiceResponseModelAssembler;
    private final ElasticQueryClient<TwitterIndexModel> elasticQueryClient;

    public TwitterElasticQueryService(ElasticQueryServiceResponseModelAssembler elasticQueryServiceResponseModelAssembler, ElasticQueryClient<TwitterIndexModel> elasticQueryClient) {
        this.elasticQueryServiceResponseModelAssembler = elasticQueryServiceResponseModelAssembler;
        this.elasticQueryClient = elasticQueryClient;
    }

    @Override
    public ElasticQueryServiceResponseModel getDocumentById(String id) {
        LOG.info("Quering elasticsearch by id '{]'", id);
        return elasticQueryServiceResponseModelAssembler.toModel((elasticQueryClient.getIndexModelById(id)));
    }

    @Override
    public List<ElasticQueryServiceResponseModel> getDocumentByText(String text) {
        LOG.info("Quering elasticsearch by text '{]'", text);
        return elasticQueryServiceResponseModelAssembler.toModels(elasticQueryClient.getIndexModelByText(text));
    }

    @Override
    public List<ElasticQueryServiceResponseModel> getAllDocuments() {
        LOG.info("Quering all documents in elasticsearch");
        return elasticQueryServiceResponseModelAssembler.toModels(elasticQueryClient.getAllIndexModels());
    }
}
