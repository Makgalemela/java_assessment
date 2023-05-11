package com.matome.ledger.account.services;

import com.matome.ledger.account.model.Request;
import com.matome.ledger.account.util.ResponseHandler;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AccountImpl implements AccountInterface {


    @RabbitListener(queues = "#{serviceQueueInfo.name}", concurrency = "#{serviceQueueConsumers}")
    public ResponseEntity dequeueAndProcessRequest(final Request request, Message message) throws Exception {

        switch (request.getRequestType()) {
            case CREATE_ACCOUNT: {
                return null;
            }
            case CREDIT: {
                return null;
            }
            case DEBIT: {
                return null;
            }
            case DELETE: {
                return null;
            }

            case FUTURE_DATE: {
                return null;
            }

            case  BALANCE: {
                return null;
            }

            default: {
//                Status status = Status.builder()
//                        .code("7")
//                        .description("Internal server error")
//                        .message("system.message.error.default")
//                        .build();
//                return CustomerResponse
//                        .builder()
//                        .status(status)
//                        .build();
            }
        }

        return  null;
    }
    @Override
    public ResponseHandler deposit() {
        return null;
    }

    @Override
    public ResponseHandler createAccount() {
        return null;
    }

    @Override
    public ResponseHandler balance() {
        return null;
    }

    @Override
    public ResponseHandler featureDateDeposit() {
        return null;
    }

    @Override
    public ResponseHandler removeTransaction() {
        return null;
    }

    @Override
    public ResponseHandler removeAccount() {
        return null;
    }
}
