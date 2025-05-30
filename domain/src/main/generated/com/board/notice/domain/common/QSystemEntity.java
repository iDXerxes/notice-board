package com.board.notice.domain.common;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSystemEntity is a Querydsl query type for SystemEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QSystemEntity extends EntityPathBase<SystemEntity> {

    private static final long serialVersionUID = -1979757972L;

    public static final QSystemEntity systemEntity = new QSystemEntity("systemEntity");

    public QSystemEntity(String variable) {
        super(SystemEntity.class, forVariable(variable));
    }

    public QSystemEntity(Path<? extends SystemEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSystemEntity(PathMetadata metadata) {
        super(SystemEntity.class, metadata);
    }

}

