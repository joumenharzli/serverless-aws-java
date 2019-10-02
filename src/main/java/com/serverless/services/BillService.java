package com.serverless.services;

import com.serverless.dto.AddBillDto;
import com.serverless.dto.BillDto;
import com.serverless.utils.Page;

import java.util.Map;


public interface BillService {

    BillDto add(AddBillDto bill);

    BillDto update(AddBillDto bill);

    Page<BillDto, Map<String, String>> findByClientId(String clientId);

    void delete(String clientId, String billId);

    void validate(String billId);

}
