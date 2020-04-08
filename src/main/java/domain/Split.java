package domain;

import lombok.Data;

@Data
public abstract class Split {
    private Double amount;
    private User user;

    public Split(User user) {
        this.user = user;
    }

    public Split(Double amount, User user) {
        this.amount = format(amount);
        this.user = user;
    }

    private Double format(double val) {
        val *= 100;
        val = Math.round(val);
        val /= 100;
        return val;
    }

    public void setAmount(Double amount) {
        this.amount = format(amount);
    }
}
