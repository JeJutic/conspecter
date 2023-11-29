package pan.artem.conspecterweb.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record Conspect(@NotNull @Size String name,
                       @NotNull Integer taskCount,
                       @NotNull Integer tasksSolved) {
}
