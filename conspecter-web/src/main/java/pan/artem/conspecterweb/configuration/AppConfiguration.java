package pan.artem.conspecterweb.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pan.artem.conspecterweb.properties.AppProperties;

@Configuration
@EnableConfigurationProperties({
        AppProperties.class,
        AppProperties.RepositoryServiceProperties.class
})
public class AppConfiguration {

    @Bean
    public RestTemplate repositoryServiceEndpoint(
            RestTemplateBuilder restTemplateBuilder,
            AppProperties.RepositoryServiceProperties properties
    ) {
        return restTemplateBuilder
                .rootUri(properties.getBaseUrl())
                .build();
    }
}
