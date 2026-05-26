package com.expense.controller;

import com.expense.dto.ExpenseResponseDTO;
import com.expense.dto.ExpenseRequestDTO;
import com.expense.entity.SummaryResponse;
import com.expense.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin("*")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    public ResponseEntity<List<ExpenseResponseDTO>> getExpenses(
            @RequestParam(required = false) String month,
            @RequestParam(required = false) Long categoryId) {

        return ResponseEntity.ok(
                expenseService.getExpenses(month, categoryId)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                expenseService.getById(id)
        );
    }

    @PostMapping
    public ResponseEntity<ExpenseResponseDTO> create(
            @Valid @RequestBody ExpenseRequestDTO dto) {

        return new ResponseEntity<>(
                expenseService.create(dto),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ExpenseRequestDTO dto) {

        return ResponseEntity.ok(
                expenseService.update(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        expenseService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/summary")
    public ResponseEntity<SummaryResponse> summary(
            @RequestParam String month) {

        return ResponseEntity.ok(
                expenseService.getMonthlySummary(month)
        );
    }
}
