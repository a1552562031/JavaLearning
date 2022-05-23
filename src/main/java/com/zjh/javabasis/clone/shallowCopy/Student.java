package com.zjh.javabasis.clone.shallowCopy;

import lombok.*;

@Data
public class Student implements Cloneable{
    private Subject subject;
    private String name;
    /**
     *  重写clone()方法
     * @return
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
