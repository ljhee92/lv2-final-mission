package finalmission.infra.thirdparty;

public record Item(
        String title,
        String author,
        String pubDate,
        String description,
        String cover,
        String isbn,
        Long categoryId,
        String categoryName,
        String publisher
) {
}
