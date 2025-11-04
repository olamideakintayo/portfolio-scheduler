package org.inits_portfolio_scheduler.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.inits_portfolio_scheduler.data.repositories.PortfolioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j

public class PortfolioServiceImpl {

    private final PortfolioRepository portfolioRepository;



    @Value("${portfolio.interest.rate:0.15}")
    private BigDecimal annualInterestRate;




}
