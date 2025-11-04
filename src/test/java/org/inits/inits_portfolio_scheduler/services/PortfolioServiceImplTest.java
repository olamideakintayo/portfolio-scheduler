package org.inits.inits_portfolio_scheduler.services;

import org.inits.inits_portfolio_scheduler.data.models.Portfolio;
import org.inits.inits_portfolio_scheduler.data.repositories.PortfolioRepository;
import org.inits.inits_portfolio_scheduler.dtos.requests.PortfolioRequest;
import org.inits.inits_portfolio_scheduler.dtos.responses.PortfolioResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class PortfolioServiceH2Test {

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private PortfolioRepository portfolioRepository;

    private Portfolio savedPortfolio;

    @BeforeEach
    void setUp() {
        portfolioRepository.deleteAll();
        Portfolio portfolio = new Portfolio();
        portfolio.setOwnerEmail("john@example.com");
        portfolio.setPortfolioName("Crypto");
        portfolio.setTotalValue(BigDecimal.valueOf(50000));
        portfolio.setInterestRate(BigDecimal.valueOf(0.15));
        savedPortfolio = portfolioRepository.save(portfolio);
    }

    @Test
    void shouldReturnAllPortfolios() {
        List<PortfolioResponse> portfolios = portfolioService.getAllPortfolios();
        assertThat(portfolios).hasSize(1);
        assertThat(portfolios.get(0).getPortfolioName()).isEqualTo("Crypto");
    }

    @Test
    void shouldReturnPortfolioById() {
        PortfolioResponse response = portfolioService.getPortfolioById(savedPortfolio.getId());
        assertThat(response.getOwnerEmail()).isEqualTo("john@example.com");
    }

    @Test
    void shouldCreateNewPortfolio() {
        PortfolioRequest request = new PortfolioRequest();
        request.setOwnerEmail("jane@example.com");
        request.setPortfolioName("Real Estate");
        request.setTotalValue(BigDecimal.valueOf(120000));

        PortfolioResponse result = portfolioService.createPortfolio(request);

        assertThat(result.getPortfolioName()).isEqualTo("Real Estate");
        assertThat(portfolioRepository.findAll()).hasSize(2);
    }
}