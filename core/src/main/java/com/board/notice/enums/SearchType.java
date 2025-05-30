package com.board.notice.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SearchType {

    TITLE("TITLE", "제목"),
    ALL("ALL", "전체");

    private final String code;

    private final String value;
}
