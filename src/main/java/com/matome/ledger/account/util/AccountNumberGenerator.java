package com.matome.ledger.account.util;

import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Service
@Slf4j
public class AccountNumberGenerator  implements IdentifierGenerator {


    @Override
    @SneakyThrows
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {


        Connection connection = session.connection();
        PreparedStatement ps = connection.prepareStatement("SELECT count(account_number) AS count from public.account");
        ResultSet rs = ps.executeQuery();
        Integer count = 0;
        if(rs.next()) {
           count = (Integer) rs.getInt("count");

        }
        LocalDate currentDateTime = LocalDate.now();
        // 5 char long chars
        String account = currentDateTime.toString()
                .replaceAll("-", "")
                .substring(3);

        // For production ready application we will need to write a better generator
        // we would find a proper algorithm
        // We will also need to deal with concurrency to make sure that no same account numbers is created
        // This is limited to 99999 account at every given day

        String accountNumber = String.format("%05d", count);

        return   Long.valueOf(String.format("%s%s", account, accountNumber));
    }

}
