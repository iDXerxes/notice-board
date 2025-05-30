package com.board.notice.domain.repository.notice.impl;

import com.board.notice.domain.model.notice.Notice;
import com.board.notice.domain.model.notice.QNotice;
import com.board.notice.domain.repository.notice.CustomNoticeRepository;
import com.board.notice.dto.notice.request.SearchNoticeRequest;
import com.board.notice.enums.SearchType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class CustomNoticeRepositoryImpl implements CustomNoticeRepository {

    private final JPAQueryFactory jpaQueryFactory;

    QNotice qNotice = QNotice.notice;

    @Override
    public Page<Notice> findBySearchNoticeRequest(SearchNoticeRequest searchNoticeRequest, Pageable pageable) {
        List<Notice> result = jpaQueryFactory.selectFrom(QNotice.notice)
                .where(eqKeyword(searchNoticeRequest))
                .where(afterStrDtm(searchNoticeRequest.getStrRegDtm()))
                .where(beforeEndDtm(searchNoticeRequest.getEndRegDtm()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qNotice.regDtm.desc())
                .fetch();

        return new PageImpl<>(result, pageable, this.findBySearchNoticeRequestCnt(searchNoticeRequest));
    }

    private Long findBySearchNoticeRequestCnt(SearchNoticeRequest searchNoticeRequest) {
        Long result = jpaQueryFactory.select(qNotice.count()).from(qNotice)
                .where(eqKeyword(searchNoticeRequest))
                .where(afterStrDtm(searchNoticeRequest.getStrRegDtm()))
                .where(beforeEndDtm(searchNoticeRequest.getEndRegDtm()))
                .fetchOne();
        return ObjectUtils.isEmpty(result) ? 0L : result;
    }

    private BooleanExpression eqKeyword(SearchNoticeRequest searchNoticeRequest) {
        if (ObjectUtils.isEmpty(searchNoticeRequest.getKeyword())) {
            return null;
        }

        searchNoticeRequest.setTitle(searchNoticeRequest.getTitle());
        if (SearchType.TITLE.equals(searchNoticeRequest.getType())) {
            return qNotice.title.eq(searchNoticeRequest.getTitle());
        } else {
            searchNoticeRequest.setContent(searchNoticeRequest.getContent());
            return qNotice.title.eq(searchNoticeRequest.getTitle()).or(qNotice.content.eq(searchNoticeRequest.getContent()));
        }

    }

    private BooleanExpression afterStrDtm(LocalDateTime strRegDtm) {
        return ObjectUtils.isEmpty(strRegDtm) ? null : qNotice.regDtm.after(strRegDtm);
    }

    private BooleanExpression beforeEndDtm(LocalDateTime endRegDtm) {
        return ObjectUtils.isEmpty(endRegDtm) ? null : qNotice.regDtm.after(endRegDtm);
    }
}
