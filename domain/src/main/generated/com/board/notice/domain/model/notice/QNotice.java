package com.board.notice.domain.model.notice;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNotice is a Querydsl query type for Notice
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNotice extends EntityPathBase<Notice> {

    private static final long serialVersionUID = 1646937344L;

    public static final QNotice notice = new QNotice("notice");

    public final com.board.notice.domain.common.QBaseEntity _super = new com.board.notice.domain.common.QBaseEntity(this);

    public final ListPath<com.board.notice.domain.model.attach.Attachment, com.board.notice.domain.model.attach.QAttachment> attachments = this.<com.board.notice.domain.model.attach.Attachment, com.board.notice.domain.model.attach.QAttachment>createList("attachments", com.board.notice.domain.model.attach.Attachment.class, com.board.notice.domain.model.attach.QAttachment.class, PathInits.DIRECT2);

    public final StringPath author = createString("author");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> endDate = createDateTime("endDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> ntcId = createNumber("ntcId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDtm = _super.regDtm;

    public final DateTimePath<java.time.LocalDateTime> startDate = createDateTime("startDate", java.time.LocalDateTime.class);

    public final StringPath title = createString("title");

    public final NumberPath<Long> viewCount = createNumber("viewCount", Long.class);

    public QNotice(String variable) {
        super(Notice.class, forVariable(variable));
    }

    public QNotice(Path<? extends Notice> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNotice(PathMetadata metadata) {
        super(Notice.class, metadata);
    }

}

