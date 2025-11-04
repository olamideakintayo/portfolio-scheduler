package org.inits.inits_portfolio_scheduler.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.inits.inits_portfolio_scheduler.data.models.Portfolio;
import org.inits.inits_portfolio_scheduler.data.repositories.PortfolioRepository;
import org.inits.inits_portfolio_scheduler.dtos.requests.PortfolioRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PortfolioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Portfolio savedPortfolio;

    @BeforeEach
    void setUp() {
        portfolioRepository.deleteAll();

        Portfolio portfolio = new Portfolio();
        portfolio.setOwnerEmail("test@example.com");
        portfolio.setPortfolioName("Crypto");
        portfolio.setTotalValue(BigDecimal.valueOf(20000));
        portfolio.setInterestRate(BigDecimal.valueOf(0.15));
        savedPortfolio = portfolioRepository.save(portfolio);
    }

    @Test
    void shouldGetAllPortfolios() throws Exception {
        mockMvc.perform(get("/api/portfolios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ownerEmail").value("test@example.com"));
    }

    @Test
    void shouldGetPortfolioById() throws Exception {
        mockMvc.perform(get("/api/portfolios/" + savedPortfolio.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.portfolioName").value("Crypto"));
    }

    @Test
    void shouldCreateNewPortfolio() throws Exception {
        PortfolioRequest dto = new PortfolioRequest();
        dto.setOwnerEmail("new@example.com");
        dto.setPortfolioName("Savings");
        dto.setTotalValue(BigDecimal.valueOf(40000));

        mockMvc.perform(post("/api/portfolios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.portfolioName").value("Savings"));
    }
}
