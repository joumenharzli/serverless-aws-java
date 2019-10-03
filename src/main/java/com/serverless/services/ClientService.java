package com.serverless.services;

import com.serverless.dto.AddClientDto;
import com.serverless.dto.ClientDto;
import com.serverless.utils.Page;

public interface ClientService {

    ClientDto add(AddClientDto client);

    Page<ClientDto, String> findAll(Integer limit, String lastEvaluatedKey);

    void delete(String clientId);

}
