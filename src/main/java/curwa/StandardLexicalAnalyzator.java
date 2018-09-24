package curwa;

import com.nikitarogov.Symbol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
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

	@Override
	public List<Lexeme> checkMinusType(List<Lexeme> lexems) {
		List<Lexeme> ret = lexems;
		for(int i = 0; i < ret.size(); i++) {
			if(ret.get(i).toString().equals("-")) {
				if(i == 0) {
					swapToNegative(lexems, i);
				}
				else {
					Lexeme prev = ret.get(i - 1);
					if(!(prev instanceof Operand) && !prev.toString().equals(")")) {
						swapToNegative(lexems, i);
					}
				}
			}
		}
		return ret;
	}
	
	private List<Lexeme> swapToNegative(List<Lexeme> lexems, int index){
		String value = "-" + lexems.get(index + 1).toString();
		lexems.add(index, new Operand(value));
		lexems.remove(index + 1);
		lexems.remove(index + 1);
		return lexems;
	}

	@Override
	public List<Lexeme> goToPostfix(List<Lexeme> lexems) {
		LinkedList<Lexeme> ret = new LinkedList<>();
		LinkedList<Lexeme> operators = new LinkedList<>();
		for(int i = 0; i < lexems.size(); i++) {
			Lexeme current = lexems.get(i);
			if(current instanceof Operator) {
				if (current.toString().equals("(")) {
					operators.addFirst(current);
				}
				else if(current.toString().equals(")")) {
					while (!operators.peekFirst().toString().equals("(")) {
	                    ret.addLast(operators.pollFirst());
	                }
					operators.pollFirst();
				}
				else {
					while (!operators.isEmpty() && priority(operators.peekFirst())
							>= priority(current)) {
	                    ret.addLast(operators.pollFirst());
	                }
					operators.addFirst(current);
				}
			}
			else {
				ret.addLast(current);
			}
			
		}
		
		while(!operators.isEmpty()) {
            ret.addLast(operators.pollFirst());
        }
		
		lexems.clear();
		lexems.addAll(ret);
		
		return lexems;
	}
	
	private int priority(Lexeme operator) {
        switch (operator.toString()) {
            case "*" :
            case "/" : return 2;
            case "+" :
            case "-" : return 1;
        }
        return 0;
    }
}
