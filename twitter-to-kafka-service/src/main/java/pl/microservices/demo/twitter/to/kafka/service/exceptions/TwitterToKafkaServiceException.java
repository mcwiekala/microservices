package pl.microservices.demo.twitter.to.kafka.service.exceptions;

public class TwitterToKafkaServiceException extends RuntimeException{

    public TwitterToKafkaServiceException() {
    }

    public TwitterToKafkaServiceException(String message) {
        super(message);
    }

    public TwitterToKafkaServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
