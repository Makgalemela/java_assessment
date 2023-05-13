package com.matome.ledger.account.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LogObject implements Serializable {
    private String responseTime;
    private String httpMethod;
    private String hostname;
    private String path;
    private String statusCode;
    private String requestBody;
    private String responseBody;
}
