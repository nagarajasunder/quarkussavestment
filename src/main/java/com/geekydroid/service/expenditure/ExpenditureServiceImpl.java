package com.geekydroid.service.expenditure;

import com.geekydroid.domain.enums.DateFormat;
import com.geekydroid.domain.expenditure.Expenditure;
import com.geekydroid.domain.expenditure.ExpenditureCategory;
import com.geekydroid.domain.expenditure.ExpenditureRequest;
import com.geekydroid.repository.expenditure.ExpenditureRepository;
import com.geekydroid.utils.DateUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@ApplicationScoped
@Transactional
public class ExpenditureServiceImpl implements ExpenditureService {

    @Inject
    ExpenditureRepository repository;

    @Override
    public ExpenditureRequest createExpenditure(ExpenditureRequest expenditureRequest, ExpenditureCategory expenditureCategory) {
        LocalDateTime now = LocalDateTime.now();
        Expenditure newExpenditure = new Expenditure(
                "",
                expenditureCategory,
                expenditureRequest.getAmount(),
                expenditureRequest.getNotes(),
                expenditureRequest.getPaymode(),
                DateUtils.fromStringToDateTime(expenditureRequest.getExpenditureDate()),
                UUID.fromString(expenditureRequest.getCreatedBy()),
                now,
                now
        );
        Expenditure expenditure =  repository.createExpenditure(newExpenditure);
        if (expenditure != null) {
            return expenditureRequest;
        }
        return null;
    }

    @Override
    public void updateExpenditure(Expenditure expenditure) {

    }

    @Override
    public void deleteExpenditure(Expenditure expenditure) {

    }
}
