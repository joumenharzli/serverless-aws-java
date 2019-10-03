package com.serverless.repositories;

import com.serverless.domain.Client;
import com.serverless.utils.Page;

public interface ClientRepository {

    Client save(Client client);

    Page<Client, String> findAll(Integer limit, String lastEvaluatedKey);

    void delete(String clientId);
}
