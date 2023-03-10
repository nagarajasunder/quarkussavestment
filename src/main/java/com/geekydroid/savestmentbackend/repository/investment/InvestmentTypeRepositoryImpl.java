package com.geekydroid.savestmentbackend.repository.investment;

import com.geekydroid.savestmentbackend.domain.investment.InvestmentType;
import org.jooq.DSLContext;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

import static com.geekydroid.savestment.domain.db.Tables.INVESTMENT_TYPES;

@ApplicationScoped
@Transactional
public class InvestmentTypeRepositoryImpl implements InvestmentTypeRepository{

    @Inject
    DSLContext context;

    @Override
    public InvestmentType findInvestmentTypeByName(String investmentTypeName) {
        return InvestmentType.find("investmentName",investmentTypeName).firstResult();
    }

    @Override
    public List<InvestmentType> getAllInvestmentTypes() {
        return InvestmentType.listAll();
    }

    @Override
    public List<String> getAllInvestmentCategories() {
        return context.select(INVESTMENT_TYPES.INVESTMENT_NAME)
                .from(INVESTMENT_TYPES)
                .fetchInto(String.class);
    }
}
