package org.inits.inits_portfolio_scheduler.controller;

import lombok.RequiredArgsConstructor;
import org.inits.inits_portfolio_scheduler.dtos.requests.PortfolioRequest;
import org.inits.inits_portfolio_scheduler.dtos.responses.PortfolioResponse;
import org.inits.inits_portfolio_scheduler.services.PortfolioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/portfolios")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping
    public ResponseEntity<List<PortfolioResponse>> getAll() {
        return ResponseEntity.ok(portfolioService.getAllPortfolios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PortfolioResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(portfolioService.getPortfolioById(id));
    }

    @PostMapping
    public ResponseEntity<PortfolioResponse> create(@RequestBody PortfolioRequest request) {
        return ResponseEntity.ok(portfolioService.createPortfolio(request));

    }

}
