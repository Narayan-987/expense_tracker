package com.expense.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseResponseDTO {

    private Long id;
    private String description;
    private BigDecimal amount;
    private LocalDate date;

    private Long categoryId;
    private String categoryName;
}
