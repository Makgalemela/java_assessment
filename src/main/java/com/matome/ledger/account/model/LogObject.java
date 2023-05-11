package com.matome.ledger.account.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class LogObject {
    private String responseTime;
    private String httpMethod;
    private String hostname;
    private String path;
    private String statusCode;
    private String requestBody;
    private String responseBody;
}
