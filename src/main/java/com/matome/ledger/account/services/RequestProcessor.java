package com.matome.ledger.account.services;



import com.matome.ledger.account.model.Request;
import com.matome.ledger.account.model.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RequestProcessor {


    private final RabbitTemplate amqTemplate;
    private final DirectExchange serviceExchange;
    private String serviceRoutingKey;
    private Long serviceReplyTimeout;

    public RequestProcessor(RabbitTemplate amqTemplate,
                            DirectExchange serviceExchange,
                            String serviceRoutingKey,
                            Long serviceReplyTimeout) {
        this.amqTemplate = amqTemplate;
        this.serviceExchange = serviceExchange;
        this.serviceRoutingKey = serviceRoutingKey;
        this.serviceReplyTimeout = serviceReplyTimeout;
    }


    public ResponseResult processRequest(final Request request) {

        ResponseResult response = ResponseResult.builder().build();
        try {
            amqTemplate.setReplyTimeout(this.serviceReplyTimeout);
            response = amqTemplate.convertSendAndReceiveAsType(
                    serviceExchange.getName(),
                    this.serviceRoutingKey,
                    request,
                    new ParameterizedTypeReference<>() {});


        } catch (Exception ex) {

            ex.printStackTrace();
//            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//            Status status = Status.builder()
//                    .code("7")
//                    .description("Internal server error")
//                    .message("system.message.error.default")
//                    .build();
//            response = CustomerResponse
//                    .builder()
//                    .status(status)
//                    .build();
        }

        return response;
    }
}
