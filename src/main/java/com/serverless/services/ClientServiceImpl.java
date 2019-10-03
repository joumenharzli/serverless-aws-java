package com.serverless.services;

import com.serverless.domain.Client;
import com.serverless.dto.AddClientDto;
import com.serverless.dto.ClientDto;
import com.serverless.repositories.ClientRepository;
import com.serverless.utils.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class ClientServiceImpl implements ClientService {

    private static final Logger LOGGER = LogManager.getLogger(ClientServiceImpl.class);

    private final ClientRepository clientRepository;

    @Inject
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientDto add(AddClientDto dto) {
        LOGGER.debug(() -> "Request to add client " + dto);

        Client client = new Client();
        client.setName(dto.getName());
        Client savedEntity = clientRepository.save(client);

        return toDto(savedEntity);
    }

    @Override
    public Page<ClientDto, String> findAll(Integer limit, String lastEvaluatedKey) {
        LOGGER.debug(() -> "Request to find all clients with limit " + limit + " and last evaluated key " + lastEvaluatedKey);

        return clientRepository.findAll(limit, lastEvaluatedKey)
                .map(this::toDto);
    }

    @Override
    public void delete(String clientId) {
        LOGGER.debug(() -> "Request to delete client with id " + clientId);
        clientRepository.delete(clientId);
    }

    private ClientDto toDto(Client el) {
        ClientDto mapped = new ClientDto();
        mapped.setId(el.getId());
        mapped.setName(el.getName());
        return mapped;
    }

}
