package com.expense.controller;

import com.expense.service.CategoryService;
import com.expense.service.ExpenseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    private final ExpenseService expenseService;
    private final CategoryService categoryService;

    public ViewController(
            ExpenseService expenseService,
            CategoryService categoryService
    ) {
        this.expenseService = expenseService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/expenses";
    }

    @GetMapping("/expenses")
    public String expenses(Model model) {

        model.addAttribute(
                "expenses",
                expenseService.getExpenses(null, null)
        );

        model.addAttribute(
                "categories",
                categoryService.getAll()
        );

        return "expenses";
    }

    @GetMapping("/categories")
    public String categories(Model model) {

        model.addAttribute(
                "categories",
                categoryService.getAll()
        );

        return "categories";
    }

    @GetMapping("/summary")
    public String summary() {
        return "summary";
    }
}
