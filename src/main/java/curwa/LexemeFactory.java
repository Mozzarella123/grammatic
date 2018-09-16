package curwa;

public class LexemeFactory {
    public static Lexeme createLexeme(String value) {
        try {
            Integer.parseInt(value);
            return new Operand(value);
        }
        catch (Exception e) {
            return new Operand(value);
        }
    }

}
