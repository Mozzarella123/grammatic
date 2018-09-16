package curwa;

public abstract class  Lexeme {
    private String value;
    public Lexeme(String value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return value;
    }
}
