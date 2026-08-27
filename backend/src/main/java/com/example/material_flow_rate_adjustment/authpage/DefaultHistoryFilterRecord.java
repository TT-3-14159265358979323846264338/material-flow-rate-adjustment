package com.example.material_flow_rate_adjustment.authpage;

import java.time.YearMonth;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data 
public class DefaultHistoryFilterRecord {
    @DateTimeFormat(pattern = "yyyy-MM") 
    private YearMonth minTerm;
    @DateTimeFormat(pattern = "yyyy-MM") 
    private YearMonth maxTerm;
    private int targetId;
}