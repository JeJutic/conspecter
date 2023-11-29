package pan.artem.conspecterweb.properties;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
//@Validated
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    @Getter
    @Setter
    @Validated
    @ConfigurationProperties(prefix = "app.repository-service")
    public static class RepositoryServiceProperties {

        @NotEmpty
        private String baseUrl;
    }
}
