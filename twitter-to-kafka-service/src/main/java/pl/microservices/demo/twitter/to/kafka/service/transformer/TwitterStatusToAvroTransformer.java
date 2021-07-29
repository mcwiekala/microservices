package pl.microservices.demo.twitter.to.kafka.service.transformer;

import org.springframework.stereotype.Component;
import pl.microservices.demo.kafka.avro.model.TwitterAvroModel;
import twitter4j.Status;

@Component
public class TwitterStatusToAvroTransformer {

    public TwitterAvroModel getTwitterAvroModelStatus(Status status){
        return TwitterAvroModel
                .newBuilder()
                .setId(status.getId())
                .setUserId(status.getUser().getId())
                .setText(status.getText())
                .setCreatedAt(status.getCreatedAt().getTime())
                .build();
    }

}
