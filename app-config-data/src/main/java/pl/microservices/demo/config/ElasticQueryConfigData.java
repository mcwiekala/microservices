package pl.microservices.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "elastic-query-config")
public class ElasticQueryConfigData {

    private String textField;
//    private String connectionUrl;
//    private Integer connectionTimeoutMs;
//    private Integer socketTimeoutMs;

}
