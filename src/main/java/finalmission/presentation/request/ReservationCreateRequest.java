package finalmission.presentation.request;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record ReservationCreateRequest(
        @NotBlank
        String email,

        LocalDate reserveDate,

        Long bookId
) {
}
