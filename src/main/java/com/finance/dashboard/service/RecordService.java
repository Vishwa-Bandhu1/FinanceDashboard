package com.finance.dashboard.service;

import com.finance.dashboard.exception.ResourceNotFoundException;
import com.finance.dashboard.model.FinancialRecord;
import com.finance.dashboard.model.RecordType;
import com.finance.dashboard.repository.FinancialRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecordService {
    private final FinancialRecordRepository repository;

    public FinancialRecord createRecord(FinancialRecord record) {
        return repository.save(record);
    }

    public FinancialRecord updateRecord(String id, FinancialRecord newRecord) {
        return repository.findById(id).map(r -> {
            r.setAmount(newRecord.getAmount());
            r.setType(newRecord.getType());
            r.setCategory(newRecord.getCategory());
            r.setDate(newRecord.getDate());
            r.setDescription(newRecord.getDescription());
            return repository.save(r);
        }).orElseThrow(() -> new ResourceNotFoundException("Record not found"));
    }

    public void deleteRecord(String id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Record not found");
        repository.deleteById(id);
    }

    public List<FinancialRecord> getAllRecords() {
        return repository.findAll();
    }

    public List<FinancialRecord> filterByDate(LocalDate start, LocalDate end) {
        return repository.findByDateBetween(start, end);
    }

    public List<FinancialRecord> filterByCategory(String category) {
        return repository.findByCategoryIgnoreCase(category);
    }
    
    public List<FinancialRecord> filterByType(RecordType type) {
        return repository.findByType(type);
    }
}
