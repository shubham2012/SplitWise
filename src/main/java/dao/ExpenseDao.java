package dao;

import domain.Expense;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class ExpenseDao {

    private static Map<UUID, Expense> expenses;

    public ExpenseDao() {
        this.expenses = new LinkedHashMap<>();
    }

    public Map<UUID, Expense> getAllExpenses() {
        return expenses;
    }

    public Expense getExpense(UUID uuid) {
        return expenses.get(uuid);
    }

    public void addExpenses(Expense expense) {
        this.expenses.put(expense.getId(), expense);
    }

}
