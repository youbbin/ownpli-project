package dbproject.ownpli.controller.dto.base;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Getter
public class PageDto {

    private final int currentPage;
    private int totalPages;
    private long totalElements;

    @Builder
    protected PageDto(int currentPage, int totalPages, long totalElements) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public void updateTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void updateTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public static PageDto of(Page page) {;
        return PageDto.builder()
                .totalPages(page.getTotalPages())
                .currentPage(page.getNumber())
                .totalElements(page.getTotalElements())
                .build();
    }

    public static PageDto of(Pageable pageable) {
        return PageDto.builder()
                .currentPage(pageable.getPageNumber())
                .build();
    }
}
