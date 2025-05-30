package com.board.notice.domain.repository.attach;

import com.board.notice.domain.model.attach.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    List<Attachment> findAllByNtcId(Long ntcId);

    void deleteAllByNoteId(Long noteId);

}
