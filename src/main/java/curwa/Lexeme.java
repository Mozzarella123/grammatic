package curwa;

public abstract class  Lexeme {
    protected String value;
    public Lexeme(String value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return value;
    }
    
    @Override
    public boolean equals(Object obj) {
    	if(obj instanceof Lexeme) {
    		return obj.toString().equals(toString());
    	}
    	return false;
    }
}
