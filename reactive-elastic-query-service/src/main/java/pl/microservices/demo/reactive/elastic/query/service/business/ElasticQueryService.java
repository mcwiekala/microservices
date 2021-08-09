package pl.microservices.demo.reactive.elastic.query.service.business;

import pl.microservices.demo.elastic.query.service.common.model.ElasticQueryServiceResponseModel;
import reactor.core.publisher.Flux;

public interface ElasticQueryService {

    Flux<ElasticQueryServiceResponseModel> getDocumentByText(String text);

}
