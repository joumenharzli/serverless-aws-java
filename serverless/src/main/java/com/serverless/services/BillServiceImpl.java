package com.serverless.services;

import com.serverless.domain.Bill;
import com.serverless.dto.AddBillDto;
import com.serverless.dto.BillDto;
import com.serverless.repositories.BillRepository;
import com.serverless.utils.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.Map;

public class BillServiceImpl implements BillService {

    private static final Logger LOGGER = LogManager.getLogger(BillServiceImpl.class);

    private final BillRepository billRepository;

    @Inject
    public BillServiceImpl(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    @Override
    public BillDto add(AddBillDto dto) {
        LOGGER.debug(() -> "Request to add bill " + dto);

        Bill bill = new Bill();
        bill.setAmount(dto.getAmount());
        bill.setClientId(dto.getClientId());
        bill.setValidated(false);
        bill.setFileUploaded(false);
        Bill savedEntity = billRepository.save(bill);

        return toDto(savedEntity);
    }

    @Override
    public BillDto update(BillDto dto) {
        LOGGER.debug(() -> "Request to update bill " + dto);

        Bill entity = toEntity(dto);
        Bill savedEntity = billRepository.save(entity);
        return toDto(savedEntity);
    }

    @Override
    public Page<BillDto, Map<String, String>> findByClientId(String clientId, Integer limit, String lastEvaluatedKey) {
        return null;
    }

    @Override
    public void delete(String clientId, String billId) {

    }


    @Override
    public void validate(String billId) {

    }

    private BillDto toDto(Bill entity) {

        BillDto dto = new BillDto();
        entity.setId(dto.getId());
        entity.setAmount(dto.getAmount());
        entity.setClientId(dto.getClientId());
        entity.setCreationDate(dto.getCreationDate());
        entity.setValidated(dto.getValidated());

        return dto;
    }

    private Bill toEntity(BillDto dto) {

        Bill entity = new Bill();
        entity.setId(dto.getId());
        entity.setAmount(dto.getAmount());
        entity.setClientId(dto.getClientId());
        entity.setCreationDate(dto.getCreationDate());
        entity.setValidated(dto.getValidated());

        return entity;
    }

}
