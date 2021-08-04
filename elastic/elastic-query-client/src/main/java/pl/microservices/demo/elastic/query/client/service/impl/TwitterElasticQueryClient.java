package pl.microservices.demo.elastic.query.client.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import pl.microservices.demo.config.ElasticConfigData;
import pl.microservices.demo.config.ElasticQueryConfigData;
import pl.microservices.demo.elastic.model.index.impl.TwitterIndexModel;
import pl.microservices.demo.elastic.query.client.exception.ElasticQueryClientException;
import pl.microservices.demo.elastic.query.client.service.ElasticQueryClient;
import pl.microservices.demo.elastic.query.client.util.ElasticQueryUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TwitterElasticQueryClient implements ElasticQueryClient<TwitterIndexModel> {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterElasticQueryClient.class);
    ElasticConfigData elasticConfigData;
    ElasticQueryConfigData elasticQueryConfigData;
    ElasticsearchOperations elasticsearchOperations;
    ElasticQueryUtil<TwitterIndexModel> elasticQueryUtil;

    public TwitterElasticQueryClient(ElasticConfigData elasticConfigData, ElasticQueryConfigData elasticQueryConfigData, ElasticsearchOperations elasticsearchOperations, ElasticQueryUtil<TwitterIndexModel> elasticQueryUtil) {
        this.elasticConfigData = elasticConfigData;
        this.elasticQueryConfigData = elasticQueryConfigData;
        this.elasticsearchOperations = elasticsearchOperations;
        this.elasticQueryUtil = elasticQueryUtil;
    }

    @Override
    public TwitterIndexModel getIndexModelById(String id) {
        Query query = elasticQueryUtil.getSearchQueryById(id);
        SearchHit<TwitterIndexModel> searchResult = elasticsearchOperations.searchOne(query, TwitterIndexModel.class,
                IndexCoordinates.of(elasticConfigData.getIndexName()));
        if (searchResult == null) {
            LOG.error("No document found at elasticsearch with id: '{}'", id);
            throw new ElasticQueryClientException("No document found at elasticsearch with id: " + id);
        }
        LOG.info("Document with id'{}' retrieved successfully", searchResult.getId());
        return searchResult.getContent();
    }

    @Override
    public List<TwitterIndexModel> getIndexModelByText(String text) {
        Query query = elasticQueryUtil.getSearchQueryByFieldText(elasticQueryConfigData.getTextField(), text);
        SearchHits<TwitterIndexModel> searchResult = elasticsearchOperations.search(query, TwitterIndexModel.class,
                IndexCoordinates.of(elasticConfigData.getIndexName()));
        LOG.info("{} of documents with text '{}' retrieved successfully", searchResult.getTotalHits(), text);
        return searchResult.get().map(SearchHit::getContent).collect(Collectors.toList());
    }

    @Override
    public List<TwitterIndexModel> getAllIndexModels() {
        Query query = elasticQueryUtil.getSearchQueryForAll();
        SearchHits<TwitterIndexModel> searchResult = elasticsearchOperations.search(query, TwitterIndexModel.class,
                IndexCoordinates.of(elasticConfigData.getIndexName()));
        LOG.info("{} number of documents retrieved successfully", searchResult.getTotalHits());
        return searchResult.get().map(SearchHit::getContent).collect(Collectors.toList());
    }
}
