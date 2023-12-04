package pan.artem.conspecterrepo.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import pan.artem.conspecterrepo.properties.AppProperties;

@Configuration
@EnableConfigurationProperties({
        AppProperties.class,
        AppProperties.ParsingProperties.class
})
@EnableScheduling
public class AppConfiguration {
}
