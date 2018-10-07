package curwa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Calculator {
    private static Map<Operator, Function<List<Operand>, Double>> functions;

    static {
        functions = new HashMap<>();
        functions.put(new Operator("+"),
                (operands ->{
                	Operand op1 = operands.get(operands.size()-2);
                	Operand op2 = operands.get(operands.size()-1);
                	operands.remove(op1);
                	operands.remove(op2);
                	return Double.parseDouble(op1.value) + Double.parseDouble(op2.value);
                }));
        functions.put(new Operator("-"),
                (operands -> {
                	Operand op1 = operands.get(operands.size()-2);
                	Operand op2 = operands.get(operands.size()-1);
                	operands.remove(op1);
                	operands.remove(op2);
                	return Double.parseDouble(op1.value) - Double.parseDouble(op2.value);
                }));
        functions.put(new Operator("*"),
                (operands ->{
                	Operand op1 = operands.get(operands.size()-2);
                	Operand op2 = operands.get(operands.size()-1);
                	operands.remove(op1);
                	operands.remove(op2);
                	return Double.parseDouble(op1.value) * Double.parseDouble(op2.value);
                }));
        functions.put(new Operator("/"),
                (operands -> {
                	Operand op1 = operands.get(operands.size()-2);
                	Operand op2 = operands.get(operands.size()-1);
                	operands.remove(op1);
                	operands.remove(op2);
                	return Double.parseDouble(op1.value) / Double.parseDouble(op2.value);
                }));
        Operator uMinus = new Operator("-");
        uMinus.operationType = OperatorType.UNARY;
        functions.put(uMinus,
                (operands -> {
                	Operand op = operands.get(operands.size()-1);
                	operands.remove(op);
                	return - Double.parseDouble(op.value);
                }));
    }

    public double calculate(List<Lexeme> lexemes) {
        if (lexemes.size() > 1) {
            int i = 0;
            List<Operand> operands = new ArrayList<>();
            while (!(lexemes.get(i) instanceof Operator)) {
                operands.add((Operand) lexemes.get(i));
                i++;
            }
            Operator op = (Operator) lexemes.get(i);
            List<Lexeme> newLexems = lexemes.subList(i + 1, lexemes.size());

            Operand newO = new Operand(functions.get(op).apply(operands).toString());
            newLexems.add(0, newO);
            for(Operand operand : operands) {
            	newLexems.add(0,operand);
            }
            return calculate(newLexems);
        } else {
            return Double.parseDouble(lexemes.get(0).value);
        }
    }
}
