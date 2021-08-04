package pl.microservices.demo.elastic.query.web.client.exception;

public class ElasticQueryWebClientExeption extends RuntimeException{

    public ElasticQueryWebClientExeption() {
    }

    public ElasticQueryWebClientExeption(String message) {
        super(message);
    }

    public ElasticQueryWebClientExeption(String message, Throwable cause) {
        super(message, cause);
    }
}
