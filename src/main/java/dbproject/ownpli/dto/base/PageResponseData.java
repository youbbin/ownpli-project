package dbproject.ownpli.dto.base;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Builder
@Getter
public class PageResponseData<T> {

    private T wrapper;
    private PageDto page;

    public static <T> PageResponseData<T> of(T wrapper, PageDto page) {
        return new PageResponseData<T>(wrapper, page);
    }

    public static <T> PageResponseData<T> of(T wrapper, Pageable pageable) {
        return new PageResponseData<T>(wrapper, PageDto.of(pageable));
    }

}
