package com.geekydroid.savestmentbackend.repository.investment;

import com.geekydroid.savestmentbackend.domain.expenditure.CategoryRespnose;
import com.geekydroid.savestmentbackend.domain.investment.InvestmentType;
import org.jooq.DSLContext;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static com.geekydroid.savestment.domain.db.Tables.INVESTMENT_TYPES;

@ApplicationScoped
@Transactional
public class InvestmentTypeRepositoryImpl implements InvestmentTypeRepository {

    @Inject
    DSLContext context;

    @Inject
    EntityManager entityManager;

    @Override
    public InvestmentType getById(Long id) {
        return entityManager.createQuery(
                        "select t from InvestmentType  t where t.id=?1", InvestmentType.class
                ).setParameter(1, id)
                .getResultList().stream().findFirst().orElse(null);
    }


    @Override
    public List<CategoryRespnose> getAllInvestmentCategories() {
        return context.select(INVESTMENT_TYPES.INVESTMENT_TYPE_ID, INVESTMENT_TYPES.INVESTMENT_NAME)
                .from(INVESTMENT_TYPES)
                .fetchInto(CategoryRespnose.class);
    }

    @Override
    public InvestmentType create(InvestmentType investmentType) {
        entityManager.persist(investmentType);
        return investmentType;
    }

    @Override
    public InvestmentType update(InvestmentType investmentType) {
        entityManager.merge(investmentType);
        return investmentType;
    }

    @Override
    public InvestmentType delete(InvestmentType investmentType) {
        entityManager.remove(investmentType);
        return investmentType;
    }

    @Override
    public InvestmentType getByName(String investmentType) {
        return entityManager.createQuery(
                        "select t from InvestmentType t where lower(t.investmentName)=?1",InvestmentType.class
                )
                .setParameter(1, investmentType)
                .getResultList().stream().findFirst().orElse(null);
    }
}
