package com.common.core.util;

import com.common.ContextHolder;
import com.common.core.annotation.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.metamodel.SingularAttribute;
import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * User: Allen
 * Date: 11/26/12
 */
public class EntityUtil {

    private static Logger logger = LoggerFactory.getLogger(EntityUtil.class);

    public static Serializable getEntityId(Object entity, EntityManager em) {
        if (entity == null) {
            return null;
        }

        JpaEntityInformation metadata = null;
        try {
            metadata = JpaEntityInformationSupport.getMetadata(entity.getClass(), em);
        } catch (Exception e) {
            //when entity is not a jpa entity, there might be IllegalArgumentException
            logger.error(e.getMessage());
        }
        return metadata == null ? null : metadata.getId(entity);
    }

    public static Object getEntityId(Object entity) {
        return ContextHolder.getBean(EntityManagerFactory.class).getPersistenceUnitUtil().getIdentifier(entity);
    }

    public static String getEntityComment(Object entity) {
        Comment comment = entity.getClass().getAnnotation(Comment.class);
        return comment == null ? entity.getClass().getName() : comment.value();
    }

    public static GenerationType getIdStrategy(Object entity, EntityManager em) {
        if (entity == null) {
            return null;
        }

        JpaEntityInformation metadata = null;
        try {
            metadata = JpaEntityInformationSupport.getMetadata(entity.getClass(), em);
            SingularAttribute idAttribute = metadata.getIdAttribute();
            if (idAttribute == null)
                return null;
            Field idField = entity.getClass().getDeclaredField(idAttribute.getName());
            if (idField == null)
                return null;
            GeneratedValue annotation = idField.getAnnotation(GeneratedValue.class);
            return annotation == null ? null : annotation.strategy();
        } catch (Exception e) {
            //when entity is not a jpa entity, there might be IllegalArgumentException
            logger.error(e.getMessage());
        }
        return null;
    }
}
