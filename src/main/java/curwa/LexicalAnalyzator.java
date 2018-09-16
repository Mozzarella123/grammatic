package curwa;

import java.util.List;

public interface LexicalAnalyzator {
    List<Lexeme> parse(String in);
    List<Lexeme> checkMinusType(List<Lexeme> lexems);
    List<Lexeme> goToPostfix(List<Lexeme> lexems);
}
