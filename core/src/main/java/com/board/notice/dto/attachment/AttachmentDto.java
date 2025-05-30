package com.board.notice.dto.attachment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentDto {

    private Long atchId;

    private Long ntcId;

    private String originalFileName;

    private String storedFileName;

    private String filePath; // 파일 저장 경로 (S3 URL 또는 로컬 경로)

    private Long fileSize;
}
