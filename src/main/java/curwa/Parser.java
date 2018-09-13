package curwa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


public class Parser {

    public static void main(String[] args) {
        String in = "12*13-44*-12";

        String[] operators = {"(",")","+","-","*","/"};
        List<Part> parts = new ArrayList<>();
        String operand = "";
        for (int i=0;i<in.length();i++)
        {
            final int counter = i;
            Character current = in.charAt(counter);
            if (Stream.of(operators).anyMatch(x-> x.equals(current.toString()))) {
                if (!operand.equals("")) {
                    parts.add(new Operand<>(operand));
                    operand = "";
                }
                parts.add(new Operator(current.toString(),1));

            } else {
                operand += current.toString();
            }
        }
        if (!operand.equals(""))
            parts.add(new Operand<>(operand));
        parts.stream().filter();
        System.out.print(parts);

    }



}
