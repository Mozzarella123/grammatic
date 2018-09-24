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
}
