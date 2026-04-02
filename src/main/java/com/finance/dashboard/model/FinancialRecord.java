package com.finance.dashboard.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Document(collection = "records")
public class FinancialRecord {
    @Id
    private String id;
    @Indexed
    private String userId;
    
    private Double amount;
    private RecordType type;
    @Indexed
    private String category;
    
    @Indexed
    private LocalDate date;
    private String description;
    
    private LocalDateTime createdAt = LocalDateTime.now();
}
