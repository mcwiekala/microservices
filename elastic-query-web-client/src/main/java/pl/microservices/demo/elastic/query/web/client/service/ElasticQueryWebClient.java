package pl.microservices.demo.elastic.query.web.client.service;


import pl.microservices.demo.elastic.query.web.client.common.model.ElasticQueryWebClientRequestModel;
import pl.microservices.demo.elastic.query.web.client.common.model.ElasticQueryWebClientResponseModel;

import java.util.List;

public interface ElasticQueryWebClient {

    List<ElasticQueryWebClientResponseModel> getDataByText(ElasticQueryWebClientRequestModel requestModel);

}
