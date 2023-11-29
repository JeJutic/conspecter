package pan.artem.conspecterweb.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SolvedTask(@NotNull @Size String answer,
                         @NotNull Integer score,
                         @NotNull Integer outOf,
                         @NotNull Boolean status) {
}
