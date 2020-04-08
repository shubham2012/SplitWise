package domain;

import lombok.Data;

@Data
public class ExactSplit extends Split {

    public ExactSplit(Double amount, User user) {
        super(amount, user);
    }

}
