package com.gabrielmotta.userscore.api.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class CustomPageRequest implements Pageable {

    private int page;
    private int size;
    private String orderBy;
    private String orderDirection;

    public CustomPageRequest() {
        this.page = 0;
        this.size = 10;
        this.orderBy = "id";
        this.orderDirection = "ASC";
    }

    @Override
    public int getPageNumber() {
        return this.page;
    }

    @Override
    public int getPageSize() {
        return this.size;
    }

    @Override
    public long getOffset() {
        return (long) this.page * this.size;
    }

    @Override
    public Sort getSort() {
        return this.orderBy == null
            ? Sort.unsorted()
            : Sort.by(Sort.Direction.fromString(this.orderDirection), this.orderBy);
    }

    @Override
    public Pageable next() {
        return new CustomPageRequest(this.page + 1, this.size, this.orderBy, this.orderDirection);
    }

    @Override
    public Pageable previousOrFirst() {
        return this.hasPrevious()
            ? this.previous()
            : this.first();
    }

    @Override
    public boolean hasPrevious() {
        return this.page > 0;
    }

    public Pageable previous() {
        return new CustomPageRequest(this.page - 1, this.size, this.orderBy, this.orderDirection);
    }

    @Override
    public Pageable first() {
        return new CustomPageRequest(0, this.size, this.orderBy, this.orderDirection);
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return new CustomPageRequest(pageNumber, this.size, this.orderBy, this.orderDirection);
    }
}
