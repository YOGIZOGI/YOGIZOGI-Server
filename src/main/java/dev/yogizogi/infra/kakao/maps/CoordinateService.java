package dev.yogizogi.infra.kakao.maps;

import static dev.yogizogi.global.common.model.constant.Format.KAKAO_MAP_API_BASE_URL;
import static dev.yogizogi.global.common.model.constant.Format.KAKAO_MAP_API_URI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.yogizogi.domain.restaurant.exception.CoordinateException;
import dev.yogizogi.infra.kakao.maps.model.dto.response.ReceiveCoordinateOutDto;
import dev.yogizogi.infra.kakao.maps.model.entity.Coordinate;
import dev.yogizogi.global.common.code.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CoordinateService {

    @Value("${kakao.api-key}")
    private String apiKey;

    public Coordinate recieveCoordinate(String address) throws JsonProcessingException {

        ReceiveCoordinateOutDto receiveCoordinateOutDto = findCoordinateFromAddress(address);

        Coordinate coordinate = Coordinate.builder()
                .longitude(receiveCoordinateOutDto.getDocuments().get(0).getLongitude())
                .latitude(receiveCoordinateOutDto.getDocuments().get(0).getLatitude())
                .build();

        return coordinate;

    }

    private ReceiveCoordinateOutDto findCoordinateFromAddress(String address) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Mono<String> response = WebClient.builder()
                .baseUrl(KAKAO_MAP_API_BASE_URL)
                .build()
                .get()
                .uri(builder -> builder.path(KAKAO_MAP_API_URI)
                        .queryParam("query", address)
                        .build()
                )
                .header("Authorization", "KakaoAK " + apiKey)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume( throwable -> {
                    throw new CoordinateException(ErrorCode.FAIL_TO_GET_COORDINATE);
                });

        ReceiveCoordinateOutDto receiveCoordinateOutDto = mapper.readValue(response.block(), ReceiveCoordinateOutDto.class);

        return receiveCoordinateOutDto;

    }

}
