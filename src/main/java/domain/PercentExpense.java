package domain;

import exception.WrongExpenseException;

import java.util.List;

public class PercentExpense extends Expense {

    public PercentExpense(double amount, User paidBy, List<Split> splits, String description) throws WrongExpenseException {

        super(amount, paidBy, splits, description);

        if (!validate())
            throw new WrongExpenseException(0, String.format("Wrong Percent added Please check the values", this.toString()));

        for (Split split : splits) {
            PercentSplit percentSplit = (PercentSplit) split;
            double splitAmount = (amount * percentSplit.getPercent()) / 100.0;
            split.setAmount(splitAmount);
        }
    }

    @Override
    public boolean validate() {
        for (Split split : getSplits())
            if (!(split instanceof PercentSplit))
                return false;

        double percentSum = getSplits().stream()
                .mapToDouble(x -> ((PercentSplit) x).getPercent())
                .sum();

        if (percentSum != 100)
            return false;

        return true;
    }
}
