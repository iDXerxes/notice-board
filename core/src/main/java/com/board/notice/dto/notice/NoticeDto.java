package com.board.notice.dto.notice;

import com.board.notice.dto.attachment.AttachmentDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDto {

    private Long ntcId;

    private String title;

    private String content;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Long viewCount;

    private String author;

    private List<AttachmentDto> attachments;

    @Builder
    public NoticeDto(Long ntcId, String title, String content, LocalDateTime startDate, LocalDateTime endDate, String author) {
        this.ntcId = ntcId;
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.viewCount = 0L;
        this.author = author;
    }

}
