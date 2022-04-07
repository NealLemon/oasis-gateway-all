/*
 * Copyright By ZATI
 * Copyright By 3a3c88295d37870dfd3b25056092d1a9209824b256c341f2cdc296437f671617
 * All rights reserved.
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */

package com.oasis.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: za-zhushaobin
 * @Date: 2022/3/25 10:39
 * @Description:
 * @Version 1.0.0
 */
@AllArgsConstructor
@Data
public class OasisResponseVO<T> implements Serializable {

    private int code;

    private String msg;

    private T data;

}
