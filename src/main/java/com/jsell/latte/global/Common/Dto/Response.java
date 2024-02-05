package com.jsell.latte.global.Common.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(staticName = "of")
public class Response<T> {
    private final Integer code;
    private final String msg;
    private final T data;
}
