package domain;

import exception.WrongExpenseException;

import java.util.List;

public class EqualExpense extends Expense {

    public EqualExpense(double amount, User paidBy, List<Split> splits, String description) throws WrongExpenseException {
        super(amount, paidBy, splits, description);

        if (!validate())
            throw new WrongExpenseException(0, String.format("Wrong Exact added Please check the values", this.toString()));

        int totalSplits = splits.size();
        for (Split split : splits)
            split.setAmount(amount / totalSplits);
    }

    public boolean validate() {
        for (Split split : getSplits())
            if (!(split instanceof EqualSplit))
                return false;
        return true;
    }
}
