package domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public abstract class Expense {
    private UUID id;
    private double amount;
    private User paidBy;
    private List<Split> splits;
    private String description;
    private LocalDateTime createdOn;

    public Expense(double amount, User paidBy, List<Split> splits, String description) {
        this.amount = amount;
        this.paidBy = paidBy;
        this.splits = splits;
        this.description = description;
        this.id = UUID.randomUUID();
        this.createdOn = LocalDateTime.now();
    }

    public abstract boolean validate();
}
