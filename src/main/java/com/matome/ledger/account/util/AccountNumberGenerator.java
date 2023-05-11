package com.matome.ledger.account.util;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class AccountNumberGenerator  implements IdentifierGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {

        try {

//            if(rs.next())
            {
//                int id=rs.getInt(1)+101;
//                String generatedId = prefix + new Integer(id).toString();
                return generatedId;
            }
        } catch (Exception ex) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
        }

        return null;
    }
    }
}
