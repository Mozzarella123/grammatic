package curwa;

class Operand extends Lexeme {

    public Operand(String value) {
        super(value);
    }
    
    public double getValue() {
    	return Double.parseDouble(toString());
    }
}

