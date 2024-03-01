package dbproject.ownpli.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class SingerListResponse {

    List<String> singerName;

    public static SingerListResponse of(List<String> singers) {
        return SingerListResponse.builder()
                .singerName(singers)
                .build();
    }

}
