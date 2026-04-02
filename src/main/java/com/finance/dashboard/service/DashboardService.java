package com.finance.dashboard.service;

import com.finance.dashboard.model.FinancialRecord;
import com.finance.dashboard.model.RecordType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final MongoTemplate mongoTemplate;

    public Map<String, Object> getSummary() {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.group("type").sum("amount").as("total")
        );
        AggregationResults<Map> results = mongoTemplate.aggregate(agg, "records", Map.class);
        
        double income = 0;
        double expense = 0;
        
        for (Map result : results.getMappedResults()) {
            if (RecordType.INCOME.name().equals(result.get("_id"))) {
                income = ((Number) result.get("total")).doubleValue();
            } else if (RecordType.EXPENSE.name().equals(result.get("_id"))) {
                expense = ((Number) result.get("total")).doubleValue();
            }
        }
        
        Map<String, Object> summary = new HashMap<>();
        summary.put("totalIncome", income);
        summary.put("totalExpense", expense);
        summary.put("netBalance", income - expense);
        return summary;
    }

    public List<Map> getCategorySummary() {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.group("category").sum("amount").as("totalAmount"),
                Aggregation.project("totalAmount").and("_id").as("category")
        );
        return mongoTemplate.aggregate(agg, "records", Map.class).getMappedResults();
    }

    public List<Map> getMonthlyTrends() {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.project("amount", "type")
                        .andExpression("month(date)").as("month")
                        .andExpression("year(date)").as("year"),
                Aggregation.group("year", "month", "type").sum("amount").as("total"),
                Aggregation.project("total", "type").and("_id.year").as("year").and("_id.month").as("month"),
                Aggregation.sort(Sort.Direction.ASC, "year", "month")
        );
        return mongoTemplate.aggregate(agg, "records", Map.class).getMappedResults();
    }
    
    public List<Map> getWeeklyTrends() {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.project("amount", "type")
                        .andExpression("isoWeek(date)").as("week")
                        .andExpression("isoWeekYear(date)").as("year"),
                Aggregation.group("year", "week", "type").sum("amount").as("total"),
                Aggregation.project("total", "type").and("_id.year").as("year").and("_id.week").as("week"),
                Aggregation.sort(Sort.Direction.ASC, "year", "week")
        );
        return mongoTemplate.aggregate(agg, "records", Map.class).getMappedResults();
    }

    public List<FinancialRecord> getRecentTransactions() {
        Query query = new Query().with(Sort.by(Sort.Direction.DESC, "date")).limit(10);
        return mongoTemplate.find(query, FinancialRecord.class);
    }
}
