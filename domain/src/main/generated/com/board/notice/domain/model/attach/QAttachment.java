package com.board.notice.domain.model.attach;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAttachment is a Querydsl query type for Attachment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAttachment extends EntityPathBase<Attachment> {

    private static final long serialVersionUID = 933651902L;

    public static final QAttachment attachment = new QAttachment("attachment");

    public final com.board.notice.domain.common.QBaseEntity _super = new com.board.notice.domain.common.QBaseEntity(this);

    public final NumberPath<Long> atchId = createNumber("atchId", Long.class);

    public final StringPath filePath = createString("filePath");

    public final NumberPath<Long> fileSize = createNumber("fileSize", Long.class);

    public final NumberPath<Long> ntcId = createNumber("ntcId", Long.class);

    public final StringPath originalFileName = createString("originalFileName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDtm = _super.regDtm;

    public final StringPath storedFileName = createString("storedFileName");

    public QAttachment(String variable) {
        super(Attachment.class, forVariable(variable));
    }

    public QAttachment(Path<? extends Attachment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAttachment(PathMetadata metadata) {
        super(Attachment.class, metadata);
    }

}

