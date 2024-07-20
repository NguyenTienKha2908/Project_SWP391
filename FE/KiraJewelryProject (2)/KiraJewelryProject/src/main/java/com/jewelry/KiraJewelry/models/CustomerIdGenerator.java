package com.jewelry.KiraJewelry.models;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.stream.Stream;

public class CustomerIdGenerator implements IdentifierGenerator {

    private static final String PREFIX = "CUS";

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) {
        String query = "SELECT c.customer_Id FROM Customer c";
        Stream<String> ids = session.createQuery(query, String.class).stream();

        Long max = ids.map(id -> id.replace(PREFIX, ""))
                .mapToLong(Long::parseLong)
                .max()
                .orElse(0L);

        return PREFIX + String.format("%03d", max + 1);
    }
}
