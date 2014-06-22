package com.common.core.entity;

import java.util.Collection;

/**
 * User: Allen
 * Date: 9/16/12
 */
public interface Treeable<PK, T extends Treeable> {

    public void setParent(PK parent);

    public PK getParent();

    public void setChildren(Collection<T> children);

    public Collection<T> getChildren();

    public void addChild(T entity);
}
