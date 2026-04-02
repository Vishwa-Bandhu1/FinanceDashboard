package com.finance.dashboard.controller;

import com.finance.dashboard.model.FinancialRecord;
import com.finance.dashboard.model.RecordType;
import com.finance.dashboard.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/records")
@RequiredArgsConstructor
public class RecordController {
    
    private final RecordService recordService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public FinancialRecord create(@RequestBody FinancialRecord record) {
        return recordService.createRecord(record);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public FinancialRecord update(@PathVariable String id, @RequestBody FinancialRecord record) {
        return recordService.updateRecord(id, record);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable String id) {
        recordService.deleteRecord(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ANALYST', 'ADMIN')")
    public List<FinancialRecord> getAll() {
        return recordService.getAllRecords();
    }

    @GetMapping("/filter/date")
    @PreAuthorize("hasAnyRole('ANALYST', 'ADMIN')")
    public List<FinancialRecord> getByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return recordService.filterByDate(start, end);
    }

    @GetMapping("/filter/category")
    @PreAuthorize("hasAnyRole('ANALYST', 'ADMIN')")
    public List<FinancialRecord> getByCategory(@RequestParam String category) {
        return recordService.filterByCategory(category);
    }

    @GetMapping("/filter/type")
    @PreAuthorize("hasAnyRole('ANALYST', 'ADMIN')")
    public List<FinancialRecord> getByType(@RequestParam RecordType type) {
        return recordService.filterByType(type);
    }
}
