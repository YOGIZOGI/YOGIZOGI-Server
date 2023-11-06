package dev.yogizogi.infra.kakao.maps.model.dto.response;

import dev.yogizogi.infra.kakao.maps.model.entity.Documents;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReceiveCoordinateOutDto {

    private List<Documents> documents = new ArrayList<>();

}
