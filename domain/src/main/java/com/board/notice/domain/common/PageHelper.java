package com.board.notice.domain.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@JsonIgnoreProperties(value = {"pageable"})
public class PageHelper<T> extends PageImpl<T> {

    @JsonCreator
    public PageHelper(@JsonProperty("content") List<T> content,
                      @JsonProperty("page") int page,
                      @JsonProperty("size") int size,
                      @JsonProperty("totalElements") Long totalElements) {
        super(content, PageRequest.of(page, size), totalElements);
    }

    public PageHelper(List<T> content, Page page) {
        this(content, page.getNumber(), page.getSize(), page.getTotalElements());
    }

    public PageModel<T> getPageModel() {
        PageModel<T> page = new PageModel<>();
        page.setContent(this.getContent());
        page.setPageInfo(this.getPageInfo());

        return page;
    }

    private PageInfo getPageInfo() {
        PageInfo info = new PageInfo();
        info.setPage(this.getNumber());
        info.setSize(this.getSize());
        info.setTotal(this.getTotalElements());
        info.setTotalPage(this.getTotalPages());
        info.setHasNext(this.hasNext());

        return info;
    }
}
