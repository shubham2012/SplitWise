package domain;

import exception.WrongExpenseException;

import java.util.List;

public class ExactExpense extends Expense {

    public ExactExpense(double amount, User paidBy, List<Split> splits, String description) throws WrongExpenseException {
        super(amount, paidBy, splits, description);

        if (!validate())
            throw new WrongExpenseException(0, String.format("Wrong Exact values added Please check the values", this.toString()));
    }

    public boolean validate() {
        for (Split split : getSplits())
            if (!(split instanceof ExactSplit))
                return false;

        double sumSplitAmount = getSplits().stream()
                .mapToDouble(Split::getAmount)
                .sum();

        if (!((int)getAmount() == (int)sumSplitAmount || (int)getAmount() == (int)sumSplitAmount-1))
            return false;

        return true;
    }

}
