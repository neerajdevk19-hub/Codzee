package io.daymark.task.dto.response;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public record PageResponse<T>(
        List<T> content,
        int page,
        int number,
        int size,
        int numberOfElements,
        long totalElements,
        int totalPages,
        boolean first,
        boolean last,
        boolean empty,
        SortMetadata sort,
        PageableMetadata pageable) {

    public static <T> PageResponse<T> from(Page<T> source) {
        return new PageResponse<>(
                source.getContent(),
                source.getNumber(),
                source.getNumber(),
                source.getSize(),
                source.getNumberOfElements(),
                source.getTotalElements(),
                source.getTotalPages(),
                source.isFirst(),
                source.isLast(),
                source.isEmpty(),
                SortMetadata.from(source.getSort()),
                PageableMetadata.from(source.getPageable()));
    }

    public record SortMetadata(boolean empty, boolean sorted, boolean unsorted) {
        private static SortMetadata from(Sort source) {
            return new SortMetadata(source.isEmpty(), source.isSorted(), source.isUnsorted());
        }
    }

    public record PageableMetadata(
            int pageNumber,
            int pageSize,
            SortMetadata sort,
            long offset,
            boolean paged,
            boolean unpaged) {

        private static PageableMetadata from(Pageable source) {
            return new PageableMetadata(
                    source.isPaged() ? source.getPageNumber() : 0,
                    source.isPaged() ? source.getPageSize() : 0,
                    SortMetadata.from(source.getSort()),
                    source.isPaged() ? source.getOffset() : 0,
                    source.isPaged(),
                    source.isUnpaged());
        }
    }
}
