package finalmission.infra.thirdparty;

import finalmission.exception.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
public class AladinRestClient {

    private static final String SEARCH_URI = "/ttb/api/ItemSearch.aspx";
    private final String apiKey;
    private final RestClient restClient;

    public AladinRestClient(
            @Value("${aladin.api.base-url}") String aladinApiBaseUrl,
            @Value("${aladin.api.key}") String apiKey,
            RestClient.Builder restClientBuilder
    ) {
        this.apiKey = apiKey;
        this.restClient = restClientBuilder.baseUrl(aladinApiBaseUrl).build();
    }

    public AladinSearchResponses search(String keyword) {
        try {
            return restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path(SEARCH_URI)
                            .queryParam("ttbkey", apiKey)
                            .queryParam("Query", keyword)
                            .queryParam("output", "js")
                            .queryParam("Version", "20131101")
                            .build()
                    )
                    .retrieve()
                    .body(AladinSearchResponses.class);
        } catch (RestClientException e) {
            throw new ApiException("[ERROR] API 통신 중 문제 발생", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
