package dbproject.ownpli.dto;

import lombok.Builder;

import java.util.List;

@Builder
public class SingerListResponse {

    List<String> singerName;

    public static SingerListResponse of(List<String> singers) {
        return SingerListResponse.builder()
                .singerName(singers)
                .build();
    }

}
