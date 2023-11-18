package com.geekydroid.savestmentbackend.service.home;

import com.geekydroid.savestmentbackend.domain.expenditure.CategoryWiseExpense;
import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureOverview;
import com.geekydroid.savestmentbackend.domain.home.HomeScreenData;
import com.geekydroid.savestmentbackend.domain.investment.InvestmentOverview;
import com.geekydroid.savestmentbackend.domain.investment.InvestmentTypeOverview;
import com.geekydroid.savestmentbackend.repository.expenditure.ExpenditureRepository;
import com.geekydroid.savestmentbackend.repository.investment.InvestmentRepository;
import com.geekydroid.savestmentbackend.utils.DateUtils;
import com.geekydroid.savestmentbackend.utils.models.NetworkResponse;
import com.geekydroid.savestmentbackend.utils.models.Success;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@ApplicationScoped
@Transactional
public class HomeServiceImpl implements HomeService {

    @Inject
    InvestmentRepository investmentRepository;

    @Inject
    ExpenditureRepository expenditureRepository;

    @Override
    public NetworkResponse getHomeScreenData(String startDate, String endDate,String userId) {

        LocalDate startLocalDate = DateUtils.fromStringToLocalDate(startDate);
        LocalDate endLocalDate = DateUtils.fromStringToLocalDate(endDate);

        List<Double> totalExpenditures = expenditureRepository.getTotalExpenseAndIncomeAmount(userId,startLocalDate, endLocalDate);

        List<CategoryWiseExpense> categoryWiseExpenses = expenditureRepository.getCategoryWiseExpenseByGivenDateRange(userId,startLocalDate, endLocalDate);

        Double balanceAmount = totalExpenditures.get(1) - totalExpenditures.get(0);

        ExpenditureOverview expenditureOverview = new ExpenditureOverview(
                balanceAmount,
                totalExpenditures.get(0),
                totalExpenditures.get(1),
                categoryWiseExpenses
        );

        List<InvestmentTypeOverview> overviews = investmentRepository.getTotalInvestmentItemsByTypeGivenDateRange(startLocalDate, endLocalDate,userId);

        AtomicReference<Double> totalInvestmentAmount = new AtomicReference<>(0.0);
        overviews.forEach(item -> totalInvestmentAmount.updateAndGet(v -> v + item.getTotalBuyAmount()));
        InvestmentOverview investmentOverview = new InvestmentOverview(totalInvestmentAmount.get(), overviews, List.of());

        HomeScreenData homeScreenData = new HomeScreenData(expenditureOverview, investmentOverview);

        return new Success(Response.Status.OK, null, homeScreenData);
    }

}
