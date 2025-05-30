package com.board.notice.domain.repository.notice;

import com.board.notice.domain.model.notice.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long>, CustomNoticeRepository {

    List<Notice> findAllByStartDateAfterAndEndDateBefore(LocalDateTime startDate, LocalDateTime endDate);

}
