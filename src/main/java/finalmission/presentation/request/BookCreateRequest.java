package finalmission.presentation.request;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record BookCreateRequest(
        @NotBlank
        String title,

        @NotBlank
        String author,

        LocalDate pubDate,

        @NotBlank
        String description,

        String image,

        @NotBlank
        String isbn,

        int totalQuantity
) {
}
