package com.wfh.shopping.common.data;

import lombok.Data;

@Data
public class R<T> {
    private T data;

    private int state;

    private String message;

    public R(T data, int state, String message) {
        this.data = data;
        this.state = state;
        this.message = message;
    }
    public static <E> R<E> ok(E data){
        return new R<>(data,200,"ok");
    }

    public static <E> R<E> error(int state,String message){
        return new R<>(null,state,message);
    }

    public static <E> R<E> error(int state){
        return error(state,null);
    }
}
