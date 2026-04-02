package com.finance.dashboard.controller;

import com.finance.dashboard.model.FinancialRecord;
import com.finance.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('VIEWER', 'ANALYST', 'ADMIN')")
public class DashboardController {

    private final DashboardService service;

    @GetMapping("/summary")
    public Map<String, Object> getSummary() {
        return service.getSummary();
    }

    @GetMapping("/category")
    public List<Map> getCategory() {
        return service.getCategorySummary();
    }

    @GetMapping("/monthly")
    public List<Map> getMonthly() {
        return service.getMonthlyTrends();
    }

    @GetMapping("/weekly")
    public List<Map> getWeekly() {
        return service.getWeeklyTrends();
    }

    @GetMapping("/recent")
    public List<FinancialRecord> getRecent() {
        return service.getRecentTransactions();
    }
}
