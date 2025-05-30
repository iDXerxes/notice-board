package com.board.notice.domain.repository.notice;

import com.board.notice.domain.model.notice.Notice;
import com.board.notice.dto.notice.request.SearchNoticeRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomNoticeRepository {

    Page<Notice> findBySearchNoticeRequest(SearchNoticeRequest searchNoticeRequest, Pageable pageable);

}
