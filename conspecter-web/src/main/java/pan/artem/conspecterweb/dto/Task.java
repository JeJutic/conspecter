package pan.artem.conspecterweb.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record Task(String repositoryName,
                   String conspectName,
                   @NotNull @Size String text) {
}
