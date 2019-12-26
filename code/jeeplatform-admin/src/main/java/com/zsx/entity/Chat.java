package com.zsx.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Chat implements Serializable {

    private static final long serialVersionUID = 1L;

    private String sender;
    private String receiver;
    private String content;
}
