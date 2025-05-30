package com.board.notice.dto.notice.request;

import com.board.notice.enums.SearchType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class SearchNoticeRequest {

    private SearchType type;

    private String keyword;

    private String title;

    private String content;

    private LocalDateTime strRegDtm;

    private LocalDateTime endRegDtm;

}
