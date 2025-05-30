package com.board.notice.domain.model.notice;

import com.board.notice.domain.common.BaseEntity;
import com.board.notice.domain.model.attach.Attachment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "NTC_M")
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NTC_ID")
    private Long ntcId;

    @Column(name = "NTC_TITL")
    private String title;

    @Column(name = "NTC_CNNT", columnDefinition = "TEXT")
    private String content;

    @Column(name = "NTC_STR_DTM")
    private LocalDateTime startDate;

    @Column(name = "NTC_END_DTM")
    private LocalDateTime endDate;

    @Column(name = "VIW_CNT", columnDefinition = "BIGINT DEFAULT 0")
    private Long viewCount;

    @Column(name = "REG_ID", length = 50)
    private String author;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "NTC_ID", referencedColumnName = "NTC_ID")
    private List<Attachment> attachments = new ArrayList<>();

    public void update(String title, String content, LocalDateTime startDate, LocalDateTime endDate) {
        Optional.ofNullable(title).ifPresent(t -> this.title = t);
        Optional.ofNullable(content).ifPresent(t -> this.content = t);
        Optional.ofNullable(startDate).ifPresent(t -> this.startDate = t);
        Optional.ofNullable(endDate).ifPresent(t -> this.endDate = t);
    }


}