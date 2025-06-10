package finalmission.presentation.response;

public record ErrorResponse(
        int status,
        String message
) {
}
