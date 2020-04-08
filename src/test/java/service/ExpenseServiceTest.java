package service;

import domain.EqualSplit;
import domain.ExactSplit;
import domain.Expense;
import domain.ExpenseType;
import domain.PercentSplit;
import domain.Split;
import domain.User;
import exception.WrongExpenseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ExpenseServiceTest {

    private ExpenseService service = new ExpenseServiceImpl();
    private User user1, user2, user3, user4;

    @BeforeEach
    void setUp() {
        user1 = new User("Ramesh", "ramesh@gmail.com", 9999999991L);
        user2 = new User("Suresh", "Suresh@gmail.com", 9999999992L);
        user3 = new User("Kamlesh", "Kamlesh@gmail.com", 9999999993L);
        user4 = new User("Nilesh", "Nilesh@gmail.com", 9999999994L);
        service.addUser(user1);
        service.addUser(user2);
        service.addUser(user3);
        service.addUser(user4);
    }

    @Test
    void test_happy_flow_with_expense_with_2_contributor_exact() throws WrongExpenseException {
        List<Split> splits = new ArrayList<>();
        ExactSplit es1 = new ExactSplit(1000.00, user1);
        ExactSplit es2 = new ExactSplit(1000.00, user2);
        splits.addAll(Arrays.asList(es1, es2));

        Expense breakfastExpense = service.addExpense(ExpenseType.EXACT, 2000.00, user1, splits, "BreakFast");
        service.showBalances();
        service.showBalance(user1.getId());
    }

    @Test
    void test_happy_flow_with_expense_with_2_contributor_equal() throws WrongExpenseException {
        List<Split> splits = new ArrayList<>();
        EqualSplit es1 = new EqualSplit( user1);
        EqualSplit es2 = new EqualSplit( user2);
        splits.addAll(Arrays.asList(es1, es2));

        Expense breakfastExpense = service.addExpense(ExpenseType.EQUAL, 2000.00, user1, splits, "BreakFast");
        service.showBalances();
    }

    @Test
    void test_happy_flow_with_expense_with_2_contributor_percent() throws WrongExpenseException {
        List<Split> splits = new ArrayList<>();
        PercentSplit es1 = new PercentSplit( user1, 30);
        PercentSplit es2 = new PercentSplit( user2, 40);
        PercentSplit es3 = new PercentSplit( user3, 30);
        splits.addAll(Arrays.asList(es1, es2, es3));

        Expense breakfastExpense = service.addExpense(ExpenseType.PERCENT, 2000.00, user1, splits, "BreakFast");
        service.showBalances();
    }

    @Test
    void test_happy_flow_with_expense_with_2_contributor_percent_Settlement() throws WrongExpenseException {
        List<Split> splits = new ArrayList<>();
        PercentSplit es1 = new PercentSplit( user1, 30);
        PercentSplit es2 = new PercentSplit( user2, 40);
        PercentSplit es3 = new PercentSplit( user3, 30);
        splits.addAll(Arrays.asList(es1, es2, es3));

        service.addExpense(ExpenseType.PERCENT, 2000.00, user1, splits, "BreakFast");
        service.showBalances();
        service.settleBalance(600.00, user2, user1, "Paid in cash");
        System.out.println("After settlement");
        service.showBalances();
    }

    @Test
    void test_happy_flow_with_expense() throws WrongExpenseException {
        List<Split> splits1 = new ArrayList<>();
        ExactSplit es1 = new ExactSplit(500.00, user1);
        ExactSplit es2 = new ExactSplit(500.00, user2);
        ExactSplit es3 = new ExactSplit(500.00, user3);
        ExactSplit es4 = new ExactSplit(500.00, user4);
        splits1.addAll(Arrays.asList(es1, es2, es3, es4));

        List<Split> splits2 = new ArrayList<>();
        PercentSplit ps1 = new PercentSplit( user1, 20);
        PercentSplit ps2 = new PercentSplit( user2, 30);
        PercentSplit ps3 = new PercentSplit( user3, 50);
        splits2.addAll(Arrays.asList(ps1, ps2, ps3));


        List<Split> splits3 =  new ArrayList<>();
        EqualSplit ex1 = new EqualSplit(user1);
        EqualSplit ex3 = new EqualSplit(user2);
        EqualSplit ex4 = new EqualSplit(user3);
        splits3.addAll(Arrays.asList(ex1, ex3, ex4));

        List<Split> splits4 =  new ArrayList<>();
        EqualSplit eq1 = new EqualSplit(user1);
        EqualSplit eq2 = new EqualSplit(user2);
        EqualSplit eq3 = new EqualSplit(user2);
        EqualSplit eq4 = new EqualSplit(user3);
        splits4.addAll(Arrays.asList(eq1, eq2, eq3, eq4));

        service.addExpense(ExpenseType.EXACT, 2000.00, user1, splits1, "BreakFast");
        service.showBalance(user1.getId());
        service.addExpense(ExpenseType.PERCENT, 3000.00, user1, splits2, "Lunch");
        service.showBalance(user1.getId());
        service.addExpense(ExpenseType.EQUAL, 4000.00, user2, splits3, "Dinner");
        service.showBalance(user1.getId());
        service.addExpense(ExpenseType.EQUAL, 300.00, user3, splits4, "Ice-cream");
        service.showBalances();
    }
}