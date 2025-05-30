package com.board.notice.domain.model.attach;

import com.board.notice.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "ATCH_FILE")
public class Attachment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ATCH_ID")
    private Long atchId;

    @Column(name = "NTC_ID")
    private Long ntcId;

    @Column(name = "ORG_FILE_NM")
    private String originalFileName;

    @Column(name = "STRD_FILE_NM")
    private String storedFileName;

    @Column(name = "FILE_PATH")
    private String filePath; // 파일 저장 경로 (S3 URL 또는 로컬 경로)

    @Column(name = "FILE_SIZE")
    private Long fileSize;

    @Builder
    public Attachment(String originalFileName, String storedFileName, String filePath, Long fileSize, Long ntcId) {
        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.ntcId = ntcId;
    }
}