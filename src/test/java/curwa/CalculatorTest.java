package curwa;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CalculatorTest {

    @Test
    public void calculate2Operands() {
        //a
        Calculator c = new Calculator();
        //a
        List<Lexeme> lexemes = new ArrayList<>();
        lexemes.add(new Operand("1"));
        lexemes.add(new Operand("2"));
        lexemes.add(new Operator("+"));
        double res = c.calculate(lexemes);
        //a
        assertEquals(3,res,0);
    }
    @Test
    public void calculateMultiOperands() {
        //a
        Calculator c = new Calculator();
        //a
        List<Lexeme> lexemes = new ArrayList<>();
        lexemes.add(new Operand("1"));
        lexemes.add(new Operand("2"));
        lexemes.add(new Operator("+"));
        lexemes.add(new Operand("2"));
        lexemes.add(new Operator("*"));
        double res = c.calculate(lexemes);
        //a
        assertEquals(6,res,0);
    }
}