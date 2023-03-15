package com.geekydroid.savestmentbackend.service.home;

import com.geekydroid.savestmentbackend.domain.home.HomeScreenData;
import com.geekydroid.savestmentbackend.utils.models.NetworkResponse;

public interface HomeService {

    NetworkResponse getHomeScreenData(String startDate, String endDate);
}
