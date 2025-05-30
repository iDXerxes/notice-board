package com.board.notice.domain.common;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBaseEntity is a Querydsl query type for BaseEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QBaseEntity extends EntityPathBase<BaseEntity<?>> {

    private static final long serialVersionUID = -715459570L;

    public static final QBaseEntity baseEntity = new QBaseEntity("baseEntity");

    public final QSystemEntity _super = new QSystemEntity(this);

    public final DateTimePath<java.time.LocalDateTime> regDtm = createDateTime("regDtm", java.time.LocalDateTime.class);

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QBaseEntity(String variable) {
        super((Class) BaseEntity.class, forVariable(variable));
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QBaseEntity(Path<? extends BaseEntity> path) {
        super((Class) path.getType(), path.getMetadata());
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QBaseEntity(PathMetadata metadata) {
        super((Class) BaseEntity.class, metadata);
    }

}

