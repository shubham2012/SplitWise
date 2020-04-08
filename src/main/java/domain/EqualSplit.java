package domain;

import lombok.Data;

@Data
public class EqualSplit extends Split {

    public EqualSplit( User user) {
        super( user);
    }
}
