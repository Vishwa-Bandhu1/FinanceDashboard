package com.finance.dashboard.repository;

import com.finance.dashboard.model.FinancialRecord;
import com.finance.dashboard.model.RecordType;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDate;
import java.util.List;

public interface FinancialRecordRepository extends MongoRepository<FinancialRecord, String> {
    List<FinancialRecord> findByDateBetween(LocalDate startDate, LocalDate endDate);
    List<FinancialRecord> findByCategoryIgnoreCase(String category);
    List<FinancialRecord> findByType(RecordType type);
    List<FinancialRecord> findTop10ByOrderByDateDesc();
}
