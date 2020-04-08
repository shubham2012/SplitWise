package service;

import dao.BalanceSheet;
import dao.ExpenseDao;
import dao.UserDao;
import domain.EqualExpense;
import domain.ExactExpense;
import domain.ExactSplit;
import domain.Expense;
import domain.ExpenseType;
import domain.PercentExpense;
import domain.Split;
import domain.User;
import exception.WrongExpenseException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ExpenseServiceImpl implements ExpenseService {

    private BalanceSheet balanceSheet;
    private ExpenseDao expenseDao;
    private UserDao userDao;

    public ExpenseServiceImpl() {
        this.balanceSheet = new BalanceSheet();
        this.expenseDao = new ExpenseDao();
        this.userDao = new UserDao();
    }

    @Override
    public void addUser(User user) {
        userDao.addUsers(user);
    }

    @Override
    public Expense addExpense(ExpenseType type, Double amount, User paiBy, List<Split> splits, String description) throws WrongExpenseException {
        Expense expense = createExpense(type, amount, paiBy, splits, description);
        for (Split split : expense.getSplits()) {
            if (paiBy.getId() == split.getUser().getId())
                continue;
            balanceSheet.addExpense(paiBy.getId(), split.getUser().getId(), split.getAmount());
        }
        expenseDao.addExpenses(expense);
        return expense;
    }

    @Override
    public Expense settleBalance(Double amount, User paiBy, User to, String description) throws WrongExpenseException {
        ExpenseType type = ExpenseType.SETTLEMENT;
        ExactSplit split = new ExactSplit(amount, to);
        Expense expense = createExpense(type, amount, paiBy, Arrays.asList(split), description);
        balanceSheet.addExpense(paiBy.getId(), split.getUser().getId(), split.getAmount());
        expenseDao.addExpenses(expense);
        return expense;
    }


    @Override
    public void showBalance(UUID userId) {
        double totalBalance = 0.0;
        System.out.println(String.format("Printing Balance for user : %s", userDao.getUser(userId).getName()));
        boolean isEmpty = true;
        for (Map.Entry<UUID, Double> userBalance : balanceSheet.getBalanceSheet(userId).entrySet()) {
            if (userBalance.getValue() != 0) {
                isEmpty = false;
                printBalance(userId, userBalance.getKey(), userBalance.getValue());
                totalBalance += userBalance.getValue();
            }
        }
        if (isEmpty) {
            System.out.println("No balances");
        } else {
            System.out.println(String.format("Overall balance for user : %.2f", totalBalance));
        }
    }

    @Override
    public void showBalances() {
        System.out.println("Printing Balance for all the users:");

        for (Map.Entry<UUID, Map<UUID, Double>> allBalances : balanceSheet.getEntireBalanceSheet().entrySet()) {
            for (Map.Entry<UUID, Double> userBalance : allBalances.getValue().entrySet()) {
                printBalance(allBalances.getKey(), userBalance.getKey(), userBalance.getValue());
            }
        }
    }

    public void showAllTransactions() {
        System.out.println("All Transactions");
        expenseDao.getAllExpenses().entrySet().stream()
                .map(Map.Entry::getValue)
                .forEach(System.out::println);
    }

    public void showAllTransactionsForUser(UUID userId) {
        System.out.println(String.format("Transactions for user %s", userDao.getUser(userId).getName()));
        expenseDao.getAllExpenses().entrySet().stream()
                .map(Map.Entry::getValue)
                .filter(x -> x.getPaidBy().equals(userId))
                .forEach(System.out::println);
        System.out.println();
    }

    private Expense createExpense(ExpenseType type, Double amount, User paiBy, List<Split> splits, String description) throws WrongExpenseException {
        switch (type) {
            case EQUAL:
                return new EqualExpense(amount, paiBy, splits, description);
            case PERCENT:
                return new PercentExpense(amount, paiBy, splits, description);
            case SETTLEMENT:
            case EXACT:
                return new ExactExpense(amount, paiBy, splits, description);
            default:
                return null;
        }
    }

    private void printBalance(UUID user1, UUID user2, double amount) {
        String user1Name = userDao.getUser(user1).getName();
        String user2Name = userDao.getUser(user2).getName();
        if (amount < 0) {
            System.out.println(user1Name + " ows to " + user2Name + ": " + Math.abs(amount));
        } else if (amount > 0) {
            System.out.println(user1Name + " gets from " + user2Name + ": " + amount);
        }
    }


}
