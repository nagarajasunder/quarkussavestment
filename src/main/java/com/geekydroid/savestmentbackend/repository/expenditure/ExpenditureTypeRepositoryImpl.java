package com.geekydroid.savestmentbackend.repository.expenditure;

import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureType;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@ApplicationScoped
public class ExpenditureTypeRepositoryImpl implements ExpenditureTypeRepository{
    @Override
    public List<ExpenditureType> addExpenditureType(List<ExpenditureType> expenditureTypes) {
        ExpenditureType.persist(expenditureTypes);
        return expenditureTypes;
    }

    @Override
    public List<ExpenditureType> getAllExpenditureTypes() {
        return ExpenditureType.listAll();
    }
}
