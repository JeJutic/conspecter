package pan.artem.conspecterweb.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record Repository(@NotNull @Size String author,
                         @NotNull @Size String name) {
}
