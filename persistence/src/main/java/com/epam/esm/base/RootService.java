package com.epam.esm.base;

import java.util.List;

public interface RootService<T> {

    int create(T t);

    T findById(String id);

    List<T> findAll();

    int deleteUsingId(String id);

}
