package com.geekydroid.savestmentbackend.repository.expenditure;

import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureType;
import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureTypeResponse;
import org.jooq.DSLContext;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static com.geekydroid.savestment.domain.db.Tables.EXPENDITURE_TYPE;
import static com.geekydroid.savestment.domain.db.tables.ExpenditureCategory.EXPENDITURE_CATEGORY;

@Transactional
@ApplicationScoped
public class ExpenditureTypeRepositoryImpl implements ExpenditureTypeRepository {

    @Inject
    EntityManager entityManager;

    @Inject
    DSLContext context;

    @Override
    public List<ExpenditureType> addExpenditureType(List<ExpenditureType> expenditureTypes) {
        ExpenditureType.persist(expenditureTypes);
        return expenditureTypes;
    }

    @Override
    public List<ExpenditureTypeResponse> getAllExpenditureTypes() {
        return context.select(EXPENDITURE_TYPE.EXPENDITURE_TYPE_ID,
                EXPENDITURE_TYPE.EXPENDITURE_NAME)
                .from(EXPENDITURE_TYPE)
                .fetchInto(ExpenditureTypeResponse.class);
    }

    @Override
    public ExpenditureType getById(Long id) {
        return entityManager.createQuery("select e from ExpenditureType e where e.expenditureTypeId=?1", ExpenditureType.class)
                .setParameter(1, id)
                .getResultList().stream().findFirst().orElse(null);
    }
}
