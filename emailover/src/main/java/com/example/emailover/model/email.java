package com.example.emailover.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class email {
    private String userName;
    private String toUserName;
    private String talkFont;
    private String startData;
    private String smtpNum;
    private int time;
    private String title;
}
