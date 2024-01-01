package com.geekydroid.savestmentbackend.service.expenditure;

import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureType;
import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureTypeRequest;
import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureTypeResponse;
import com.geekydroid.savestmentbackend.repository.expenditure.ExpenditureTypeRepository;
import com.geekydroid.savestmentbackend.utils.models.Error;
import com.geekydroid.savestmentbackend.utils.models.GenericNetworkResponse;
import com.geekydroid.savestmentbackend.utils.models.NetworkResponse;
import com.geekydroid.savestmentbackend.utils.models.Success;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional
@ApplicationScoped
public class ExpenditureTypeServiceImpl implements ExpenditureTypeService {

    @Inject
    ExpenditureTypeRepository expenditureTypeRepository;

    @Override
    public NetworkResponse addExpenditureType(List<ExpenditureTypeRequest> expenditureTypes) {
        if (expenditureTypes == null || expenditureTypes.size() == 0) {
            return new Error(Response.Status.BAD_REQUEST, null, "Expenditure Type names cannot be empty");
        }
        LocalDateTime currentTime = LocalDateTime.now();
        List<ExpenditureType> expenditureTypeList = new ArrayList<>();
        for (ExpenditureTypeRequest ExpenditureTypeStr : expenditureTypes) {
            ExpenditureType expenditureType = new ExpenditureType(
                    ExpenditureTypeStr.getExpenditureType(),
                    currentTime,
                    currentTime
            );
            expenditureTypeList.add(expenditureType);

        }
        List<ExpenditureType> result = expenditureTypeRepository.addExpenditureType(expenditureTypeList);
        if (result == null) {
            return new Error(Response.Status.INTERNAL_SERVER_ERROR, null, null);
        } else {
            GenericNetworkResponse response = new GenericNetworkResponse();
            response.setStatus("SUCCESS");
            response.setMessage("Expenditure types created successfully");
            response.setStatusCode(Response.Status.CREATED.getStatusCode());
            return new Success(Response.Status.CREATED, null, response);
        }
    }

    @Override
    public List<ExpenditureTypeResponse> getAllExpenditureTypes() {
        return expenditureTypeRepository.getAllExpenditureTypes();
    }
}
