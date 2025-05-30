package com.board.notice.adminapi.service.notice.impl;

import com.board.notice.adminapi.service.notice.NoticeService;
import com.board.notice.domain.common.PageHelper;
import com.board.notice.domain.common.PageModel;
import com.board.notice.domain.model.attach.Attachment;
import com.board.notice.domain.model.notice.Notice;
import com.board.notice.domain.repository.attach.AttachmentRepository;
import com.board.notice.domain.repository.notice.NoticeRepository;
import com.board.notice.dto.notice.NoticeDto;
import com.board.notice.dto.notice.request.SearchNoticeRequest;
import com.board.notice.utils.FileUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final ModelMapper modelMapper;

    private final NoticeRepository noticeRepository;

    private final AttachmentRepository attachmentRepository;

    private final FileUtil fileUtil;

    @Transactional
    @Override
    public PageModel<NoticeDto> getNotices(SearchNoticeRequest searchNoticeRequest, Pageable pageable) {
        Page<Notice> page = noticeRepository.findBySearchNoticeRequest(searchNoticeRequest, pageable);
        List<NoticeDto> content = page.getContent().stream().map(entity -> modelMapper.map(entity, NoticeDto.class)).collect(Collectors.toList());

        return new PageHelper<>(content, page).getPageModel();
    }

    @Override
    public List<NoticeDto> getAlertNotices() {
        List<Notice> notices = noticeRepository.findAllByStartDateAfterAndEndDateBefore(LocalDateTime.now(), LocalDateTime.now());
        return notices.stream().map(entity -> modelMapper.map(entity, NoticeDto.class)).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public NoticeDto getNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow();
        notice.setViewCount(notice.getViewCount() + 1);
        return modelMapper.map(notice, NoticeDto.class);
    }

    @Transactional
    @Override
    public void createNotice(NoticeDto noticeDto, List<MultipartFile> fileList) {
        Notice notice = noticeRepository.save(modelMapper.map(noticeDto, Notice.class));

        this.attachFile(notice.getNtcId(), fileList);
    }

    @Transactional
    protected void attachFile(Long ntcId, List<MultipartFile> fileList) {
        for (MultipartFile file : fileList) {
            if (!file.isEmpty()) {
                String storedFileName = fileUtil.storeFile(file);
                String filePath = fileUtil.getFileUrl(storedFileName); // 파일 URL 생성
                Attachment attachment = Attachment.builder()
                        .originalFileName(file.getOriginalFilename())
                        .storedFileName(storedFileName)
                        .filePath(filePath)
                        .fileSize(file.getSize())
                        .ntcId(ntcId)
                        .build();

                attachmentRepository.save(attachment);
            }
        }
    }

    @Transactional
    @Override
    public void updateNotice(NoticeDto noticeDto, List<MultipartFile> fileList) {
        Notice notice = noticeRepository.findById(noticeDto.getNtcId()).orElseThrow();
        notice.update(notice.getTitle(), notice.getContent(), notice.getStartDate(), notice.getEndDate());
        this.updateAttachment(notice.getNtcId(), fileList);
    }

    @Transactional
    protected void updateAttachment(Long ntcId, List<MultipartFile> fileList) {
        this.deleteFiles(ntcId);
        this.attachFile(ntcId, fileList);
    }

    protected void deleteFiles(Long ntcId) {
        List<Attachment> attachments = attachmentRepository.findAllByNtcId(ntcId);
        for (Attachment attachment : attachments) {
            fileUtil.deleteFile(attachment.getStoredFileName()); // 실제 파일 삭제
        }
        attachmentRepository.deleteAll(attachments);
    }

    @Override
    public void deleteNotice(Long noticeId) {
        noticeRepository.deleteById(noticeId);
        this.deleteFiles(noticeId);
    }
}
