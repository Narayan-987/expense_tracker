package com.expense.entity;

import com.expense.dto.CategorySummaryDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SummaryResponse {

    private String month;

    private BigDecimal total;

    private List<CategorySummaryDTO> categories;
}