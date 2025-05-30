package com.board.notice.domain.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageModel<T> {

    private PageInfo pageInfo;

    private List<T> content;

    public PageModel(Page<T> page) {
        this.content = page.getContent();
        this.pageInfo = new PageInfo(page);
    }
}
