package com.serverless.repositories;

import com.serverless.domain.Bill;
import com.serverless.utils.Page;

public interface BillRepository {
    Bill save(Bill bill);

    Page<Bill, String> findByClientId(String clientId, Integer limit, String lastEvaluatedKey);

    Page<Bill, String> findAll(Integer limit, String lastEvaluatedKey);
}
