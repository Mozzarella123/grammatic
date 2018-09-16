package curwa;

import java.util.List;

public interface LexicalAnalyzator {
    List<Lexeme> parse(String in);
}
