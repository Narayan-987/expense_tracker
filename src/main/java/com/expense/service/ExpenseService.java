package com.expense.service;

import com.expense.dto.CategorySummaryDTO;
import com.expense.dto.ExpenseRequestDTO;
import com.expense.dto.ExpenseResponseDTO;
import com.expense.entity.Category;
import com.expense.entity.Expense;
import com.expense.entity.SummaryResponse;
import com.expense.exceptionHandler.ResourceNotFoundException;
import com.expense.repository.CategoryRepository;
import com.expense.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.stream.Collectors;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<ExpenseResponseDTO> getExpenses(
            String month,
            Long categoryId
    ) {

        List<Expense> expenses;

        if (month != null && categoryId != null) {

            String[] parts = month.split("-");

            int year = Integer.parseInt(parts[0]);
            int mon = Integer.parseInt(parts[1]);

            expenses =
                    expenseRepository
                            .findByDateBetweenAndCategoryId(
                                    LocalDate.of(year, mon, 1),
                                    LocalDate.of(year, mon, 1)
                                            .withDayOfMonth(
                                                    LocalDate.of(year, mon, 1)
                                                            .lengthOfMonth()
                                            ),
                                    categoryId
                            );

        } else if (month != null) {

            String[] parts = month.split("-");

            int year = Integer.parseInt(parts[0]);
            int mon = Integer.parseInt(parts[1]);

            expenses =
                    expenseRepository.findByDateBetween(
                            LocalDate.of(year, mon, 1),
                            LocalDate.of(year, mon, 1)
                                    .withDayOfMonth(
                                            LocalDate.of(year, mon, 1)
                                                    .lengthOfMonth()
                                    )
                    );

        } else if (categoryId != null) {

            expenses =
                    expenseRepository.findByCategoryId(categoryId);

        } else {

            expenses = expenseRepository.findAll();
        }

        return expenses.stream()
                .map(this::mapToDTO)
                .toList();
    }



    public ExpenseResponseDTO getById(Long id) {

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Expense not found"));

        return mapToDTO(expense);
    }

    public ExpenseResponseDTO create(
            ExpenseRequestDTO dto) {

        Category category = categoryRepository.findById(
                        dto.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found"));

        Expense expense = new Expense();

        expense.setDescription(dto.getDescription());
        expense.setAmount(dto.getAmount());
        expense.setDate(dto.getDate());
        expense.setCategory(category);

        Expense savedExpense =
                expenseRepository.save(expense);

        return mapToDTO(savedExpense);
    }

    private ExpenseResponseDTO mapToDTO(
            Expense expense) {

        ExpenseResponseDTO dto =
                new ExpenseResponseDTO();

        dto.setId(expense.getId());
        dto.setDescription(expense.getDescription());
        dto.setAmount(expense.getAmount());
        dto.setDate(expense.getDate());

        if (expense.getCategory() != null) {

            dto.setCategoryId(
                    expense.getCategory().getId());

            dto.setCategoryName(
                    expense.getCategory().getName());
        }

        return dto;
    }
    public ExpenseResponseDTO update(
            Long id,
            ExpenseRequestDTO dto) {

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Expense not found"));

        Category category = categoryRepository
                .findById(dto.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found"));

        expense.setCategory(category);
        expense.setDescription(dto.getDescription());
        expense.setAmount(dto.getAmount());
        expense.setDate(dto.getDate());


        Expense updatedExpense =
                expenseRepository.save(expense);

        return mapToDTO(updatedExpense);
    }

    public void delete(Long id) {

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Expense not found"));

        expenseRepository.delete(expense);
    }

    public SummaryResponse getMonthlySummary(
            String month) {

        List<Expense> expenses =
                expenseRepository.findAll()
                        .stream()

                        .filter(expense ->
                                expense.getDate()
                                        .toString()
                                        .startsWith(month))

                        .toList();

        BigDecimal total = expenses.stream()

                .map(Expense::getAmount)

                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add
                );

        List<CategorySummaryDTO> categorySummaries =
                expenses.stream()

                        .collect(Collectors.groupingBy(
                                expense ->
                                        expense.getCategory()
                                                .getName(),

                                Collectors.mapping(
                                        Expense::getAmount,
                                        Collectors.reducing(
                                                BigDecimal.ZERO,
                                                BigDecimal::add
                                        )
                                )
                        ))

                        .entrySet()

                        .stream()

                        .map(entry ->
                                new CategorySummaryDTO(
                                        entry.getKey(),
                                        entry.getValue()
                                ))

                        .toList();

        return new SummaryResponse(
                month,
                total,
                categorySummaries
        );
    }
}
