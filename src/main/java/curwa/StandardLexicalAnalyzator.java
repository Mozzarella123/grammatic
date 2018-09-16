package curwa;

import com.nikitarogov.Symbol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class StandardLexicalAnalyzator implements LexicalAnalyzator {

    private static String[] delimiters = {
            " ",
    };
    private static String[] operators = {
            "+", "-", "(", ")", "*", "/"
    };

    @Override
    public List<Lexeme> parse(String in) {
        String currentLexeme = "";
        List<Lexeme> lexemes = new ArrayList<>();
        for (int i = 0; i < in.length(); i++) {
            String symbol = ((Character) in.charAt(i)).toString();
            if (Arrays.stream(delimiters).anyMatch(symbol::equals)) {
                if (!currentLexeme.equals("")) {
                    lexemes.add(LexemeFactory.createLexeme(currentLexeme));
                    currentLexeme = "";
                }
            } else {
                if (isComposite(symbol)) {
                    currentLexeme += symbol;
                } else {
                    if (!currentLexeme.equals("")) {
                        lexemes.add(LexemeFactory.createLexeme(currentLexeme));
                        currentLexeme = "";
                    }
                    lexemes.add(LexemeFactory.createLexeme(symbol));
                }
            }
        }
        if (!currentLexeme.equals(""))
            lexemes.add(LexemeFactory.createLexeme(currentLexeme));
        return lexemes;
    }

    private boolean isComposite(String s) {
        return !(Arrays.stream(operators).anyMatch(s::equals));

    }

    public static class Rule {
        private Predicate<String> condition;

        public Rule(Predicate<String> p) {
            condition = p;
        }

        public boolean validate(String value) {
            return condition.test(value);
        }
    }
}
