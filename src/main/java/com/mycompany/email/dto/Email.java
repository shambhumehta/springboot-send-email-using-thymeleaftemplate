package com.mycompany.email.dto;

import lombok.Data;
import lombok.Getter;

/**
 * Created by shambhu.mehta on 3/10/2022
 * Description:
 */
@Data
@Getter
public class Email {

    private  String name;
    private String data;
    private  String to;
    private String cc;
    private String bcc;


    public Email(String name, String data, String to, String cc, String bcc) {
        this.name = name;
        this.data = data;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
    }
}
