package com.epam.esm.base;

import java.util.List;

public interface RootDto<T>{

    int insert(T t);

    T getById(Integer id);

    List<T> getAll();

    int deleteById(Integer id);

    boolean checkExists(Integer id);

}
