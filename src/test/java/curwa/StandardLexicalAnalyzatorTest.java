package curwa;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class StandardLexicalAnalyzatorTest {

    @Test
    public void parseWithoutDelimiters() {
        //arrange
        LexicalAnalyzator la = new StandardLexicalAnalyzator();
        String expression = "1+2";
        //act
        List<Lexeme> actual = la.parse(expression);
        System.out.println(actual);
        //assert
        assertEquals(3, actual.size());
    }
    @Test
    public void parseWithDelimiters() {
        //arrange
        LexicalAnalyzator la = new StandardLexicalAnalyzator();
        String expression = "1 + 2";
        //act
        List<Lexeme> actual = la.parse(expression);
        System.out.println(actual);
        //assert
        assertEquals(3, actual.size());
    }
    @Test
    public void parseComposite() {
        //arrange
        LexicalAnalyzator la = new StandardLexicalAnalyzator();
        String expression = "1 + 22";
        //act
        List<Lexeme> actual = la.parse(expression);
        System.out.println(actual);
        //assert
        assertEquals(3, actual.size());
    }
    @Test
    public void parseComplex() {
        //arrange
        LexicalAnalyzator la = new StandardLexicalAnalyzator();
        String expression = "(1 + 22 - 33)*-2";
        //act
        List<Lexeme> actual = la.parse(expression);
        System.out.println(actual);
        //assert
        assertEquals(10, actual.size());
    }
    
    @Test
    public void minusCheck() {
    	LexicalAnalyzator la = new StandardLexicalAnalyzator();
    	List<Lexeme> lexems = new ArrayList<>();
    	lexems.add( LexemeFactory.createLexeme("10") );
    	lexems.add( LexemeFactory.createLexeme("*") );
    	lexems.add( LexemeFactory.createLexeme("-") );
    	lexems.add( LexemeFactory.createLexeme("2") );
    	
    	la.checkMinusType(lexems);
    	
    	assertEquals(4, lexems.size());
    	assertEquals( OperatorType.UNARY ,((Operator) lexems.get(lexems.size() - 2)).operationType);
    }
    
    @Test
    public void goToSimplePostfix() {
    	LexicalAnalyzator la = new StandardLexicalAnalyzator();
    	List<Lexeme> lexems = new ArrayList<>();
    	lexems.add( LexemeFactory.createLexeme("10") );
    	lexems.add( LexemeFactory.createLexeme("*") );
    	lexems.add( LexemeFactory.createLexeme("-") );
    	lexems.add( LexemeFactory.createLexeme("2") );
    	
    	
    	la.checkMinusType(lexems);
    	List<Lexeme> postfix = new ArrayList<>();
    	postfix.add( LexemeFactory.createLexeme("10") );
    	postfix.add( LexemeFactory.createLexeme("2") );
    	postfix.add( LexemeFactory.createLexeme("-") );
    	postfix.add( LexemeFactory.createLexeme("*") );
    	
    	la.goToPostfix(lexems);
    	
    	System.out.println(lexems);
    	assertEquals(postfix.toString(), lexems.toString());
    }
    
    @Test
    public void goToPostfix() {
    	LexicalAnalyzator la = new StandardLexicalAnalyzator();
    	List<Lexeme> lexems = new ArrayList<>();
    	lexems.add( LexemeFactory.createLexeme("10") );
    	lexems.add( LexemeFactory.createLexeme("*") );
    	lexems.add( LexemeFactory.createLexeme("-") );
    	lexems.add( LexemeFactory.createLexeme("2") );
    	lexems.add( LexemeFactory.createLexeme("-") );
    	lexems.add( LexemeFactory.createLexeme("7") );
    	
    	la.checkMinusType(lexems);
    	List<Lexeme> postfix = new ArrayList<>();
    	postfix.add( LexemeFactory.createLexeme("10") );
    	postfix.add( LexemeFactory.createLexeme("2") );
    	postfix.add( LexemeFactory.createLexeme("-") );
    	postfix.add( LexemeFactory.createLexeme("*") );
    	postfix.add( LexemeFactory.createLexeme("7") );
    	postfix.add( LexemeFactory.createLexeme("-") );
 	
    	la.goToPostfix(lexems);
    	
    	System.out.println(lexems);
    	assertEquals(postfix.toString(), lexems.toString());
    }
    
    @Test
    public void functionaalTest() {
    	LexicalAnalyzator la = new StandardLexicalAnalyzator();
    	List<Lexeme> lexems = la.parse("(- 1) * ( 12 + 23 ) - ( 4 / 5 )");
    	la.checkMinusType(lexems);
    	la.goToPostfix(lexems);
    	
    	System.out.println(lexems);
    	
    	Calculator calc = new Calculator();
    	System.out.println(calc.calculate(lexems));
    }
    
}