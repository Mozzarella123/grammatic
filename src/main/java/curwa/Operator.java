package curwa;

import java.util.Objects;

public class Operator extends Lexeme {
    public Operator(String value) {
        super(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Operator)) return false;
        Operator operator = (Operator) o;
        return operationType == operator.operationType && value.equals(((Operator) o).value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationType);
    }

    public OperatorType operationType;
    
}
