package dbproject.ownpli.domain.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Sex {

    MALE("남성"),
    FEMALE("여성"),
    OTHER("기타");

    private final String sexName;

}
