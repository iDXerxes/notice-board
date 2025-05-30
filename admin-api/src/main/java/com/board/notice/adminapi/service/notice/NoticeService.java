package com.board.notice.adminapi.service.notice;

import com.board.notice.domain.common.PageModel;
import com.board.notice.dto.notice.NoticeDto;
import com.board.notice.dto.notice.request.SearchNoticeRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NoticeService {

    void createNotice(NoticeDto noticeDto, List<MultipartFile> files);

    PageModel<NoticeDto> getNotices(SearchNoticeRequest searchNoticeRequest, Pageable pageable);

    List<NoticeDto> getAlertNotices();

    NoticeDto getNotice(Long noticeId);

    void updateNotice(NoticeDto noticeDto, List<MultipartFile> fileList);

    void deleteNotice(Long noticeId);
}
