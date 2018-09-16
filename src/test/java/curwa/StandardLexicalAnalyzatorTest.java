package curwa;

import org.junit.Test;

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
}