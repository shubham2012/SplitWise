package service;

import domain.Expense;
import domain.ExpenseType;
import domain.Split;
import domain.User;
import exception.WrongExpenseException;

import java.util.List;
import java.util.UUID;

public interface ExpenseService {

    void addUser(User user);

    Expense addExpense(ExpenseType type, Double amount, User paiBy, List<Split> splits, String description) throws WrongExpenseException;

    Expense settleBalance(Double amount, User paiBy, User to, String description) throws WrongExpenseException;

    void showBalances();

    void showBalance(UUID userId);

}
