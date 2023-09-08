package com.dmj.dmzdbtest.data;

import com.dmj.dmzdbtest.data.response.DramaDetailResponse;
import com.dmj.dmzdbtest.data.response.DramaListResponse;
import com.dmj.dmzdbtest.data.response.DramaResultResponse;
import com.dmj.dmzdbtest.content.entity.Content;
import com.dmj.dmzdbtest.content.entity.DramaInfo;
import com.dmj.dmzdbtest.content.repository.ContentRepository;
import com.dmj.dmzdbtest.content.repository.DramaInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class DataGenerationTask {
    private final APIConfig apiConfig;
    private final ContentRepository contentRepository;
    private final DramaInfoRepository dramaInfoRepository;

    public DataGenerationTask(APIConfig apiConfig, ContentRepository contentRepository, DramaInfoRepository dramaInfoRepository) {
        this.apiConfig = apiConfig;
        this.contentRepository = contentRepository;
        this.dramaInfoRepository = dramaInfoRepository;
    }

    public void getData() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);

        RestTemplate restTemplate = new RestTemplate(factory);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);

        String baseURL = apiConfig.getBaseURL();
        String apikey = apiConfig.getKey();

        List<String> list = new ArrayList<>();
        for (int page = 1; page <= 2; page++) {

            URI url = UriComponentsBuilder.fromUriString(baseURL)
                    .path("/discover/tv")
                    .queryParam("api_key", apikey)
                    .queryParam("include_adult", false)
                    .queryParam("include_null_first_air_dates", false)
                    .queryParam("language", "en-US")
                    .queryParam("sort_by", "popularity.desc")
                    .queryParam("with_genres", "18")
                    .queryParam("with_origin_country", "KR")
                    .queryParam("page", page)
                    .build()
                    .toUri();

            ResponseEntity<DramaListResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, DramaListResponse.class);
            DramaListResponse body = responseEntity.getBody();

            assert body != null;
            for (DramaResultResponse result : body.getResults()) {
                // 드라마 상세
                URI url2 = UriComponentsBuilder.fromUriString(baseURL)
                        .path("/tv/" + result.getId())
                        .queryParam("api_key", apikey)
                        .queryParam("language", "ko")
                        .build()
                        .toUri();
                ResponseEntity<DramaDetailResponse> responseEntity2 = restTemplate.exchange(url2, HttpMethod.GET, entity, DramaDetailResponse.class);
                DramaDetailResponse body2 = responseEntity2.getBody();
                Content content = Content.toEntity(result);
                assert body2 != null;
                contentRepository.save(content);
                dramaInfoRepository.save(DramaInfo.toEntity(content, body2));
            }

//        assert body != null;
//        log.info(body.toString());
//            list.add(body);
        }
    }
}
