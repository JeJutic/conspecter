package pan.artem.conspecterrepo.properties;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    @Getter
    @Setter
    @Validated
    @ConfigurationProperties(prefix = "app.parsing")
    public static class ParsingProperties {

        @NotNull
        private Integer maxLineLength;

        @NotNull
        private Integer minTaskSize;
    }
}
