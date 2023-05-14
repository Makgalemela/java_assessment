package com.matome.ledger.account;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.matome.ledger.account.Dto.AccountDto;
import com.matome.ledger.account.controller.AccountController;
import com.matome.ledger.account.entities.Account;
import com.matome.ledger.account.model.Request;
import com.matome.ledger.account.model.ResponseResult;
import com.matome.ledger.account.services.RequestProcessor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static reactor.core.publisher.Mono.when;
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {


    @MockBean
    RequestProcessor requestProcessor;

    @Autowired
    private   MockMvc mockMvc;

    @Autowired
    private  ObjectMapper mapper;

    @Test
    @DisplayName("GET /v1/ledger/create success")
    void it_should_create_new_account() throws Exception {

        AccountDto accountDto = AccountDto.builder().accountNumber(1234567890L).build();
        Request request = Request.builder().account(accountDto).build();
        Account account = Account.builder().accountNumber(1234567890L).build();
        ResponseResult responseResult = ResponseResult.builder().account(account).build();
        doReturn(account).when(requestProcessor).processRequest(any());


        mockMvc.perform(post("/v1/ledger/create")
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

    }

}
