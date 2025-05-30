package com.board.notice.adminapi.controller;

import com.board.notice.adminapi.service.notice.NoticeService;
import com.board.notice.domain.common.PageModel;
import com.board.notice.dto.Result;
import com.board.notice.dto.notice.NoticeDto;
import com.board.notice.dto.notice.request.SearchNoticeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping(value = "/create", name = "공지 등록", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Result<String>> createNotice(
            @RequestPart NoticeDto noticeDto,
            @RequestPart(value = "file", required = false) List<MultipartFile> fileList
    ) {
        noticeService.createNotice(noticeDto, fileList);
        return ResponseEntity.ok(Result.ok());
    }

    @GetMapping(value = "/list", name = "공지 목록")
    public ResponseEntity<Result<PageModel<NoticeDto>>> getNoticeList(SearchNoticeRequest searchNoticeRequest, Pageable pageable) {
        return ResponseEntity.ok(Result.ok(noticeService.getNotices(searchNoticeRequest, pageable)));
    }

    @GetMapping(value = "/alert", name = "공지 알림")
    public ResponseEntity<Result<List<NoticeDto>>> alertNotice() {
        return ResponseEntity.ok(Result.ok(noticeService.getAlertNotices()));
    }

    @GetMapping(value = "/{ntcId}", name = "공지 조회")
    public ResponseEntity<Result<NoticeDto>> getNotice(@PathVariable(name = "ntcId") Long ntcId) {
        return ResponseEntity.ok(Result.ok(noticeService.getNotice(ntcId)));
    }

    @PostMapping(value = "/update", name = "공지 수정", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Result<String>> updateNotice(
            @RequestPart NoticeDto noticeDto,
            @RequestPart(value = "file", required = false) List<MultipartFile> fileList
    ) {
        noticeService.updateNotice(noticeDto, fileList);
        return ResponseEntity.ok(Result.ok());
    }

    @PostMapping(value = "/delete", name = "공지 삭제")
    public ResponseEntity<Result<String>> deleteNotice(@RequestBody Long ntcId) {
        noticeService.deleteNotice(ntcId);
        return ResponseEntity.ok(Result.ok());
    }
}
