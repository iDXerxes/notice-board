package com.board.notice.domain.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@NoArgsConstructor
public class PageInfo {

    private int page;

    private int size;

    private long total;

    private int totalPage;

    private boolean hasNext;

    private Sort sor;

    public  <F> PageInfo(Page<F> page) {
        this.page = page.getNumber();
        this.size = page.getSize();
        this.total = page.getTotalElements();
        this.totalPage = page.getTotalPages();
        this.hasNext = page.hasNext();
    }
}
