package Automat;

import org.junit.Assert;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;

public class AutomatTest {
    private static String[] numbers = {"0","1","2","3","4","5","6","7","8","9"};
    private static String[] operators = {"+","-","*","/"};
    private static AutomatConfig getValidationConfig(){
        AutomatConfig config = new AutomatConfig();
            config.startState = new AutomatState("start");
            AutomatState unarMinusState = new AutomatState("unar minus");
        AutomatStateLink.create(config.startState,unarMinusState,true,"-");
            AutomatState number = new AutomatState("number");
        AutomatStateLink.create(unarMinusState,number,true,numbers);
        AutomatStateLink.create(number,number,true,numbers);
        AutomatStateLink.create(config.startState,number,true,numbers);
            AutomatState open = new AutomatState("open");
        AutomatStateLink.create(config.startState,open,true,"(");
        AutomatStateLink.create(unarMinusState,open,true,"(");
        AutomatStateLink.create(open,open,true,"(");
            AutomatState operator = new AutomatState("operator");
        AutomatStateLink.create(number,operator,true,operators);
            AutomatState close = new AutomatState("close");
        AutomatStateLink.create(number,close,true,")");
        AutomatStateLink.create(close,close,true,")");
        AutomatStateLink.create(close,operator,true,operators);
        AutomatStateLink.create(operator,unarMinusState,true,"-");
        AutomatStateLink.create(operator,number,true,numbers);
        AutomatStateLink.create(operator,open,true,"(");
        AutomatStateLink.create(open,unarMinusState,true,"-");
        AutomatStateLink.create(open,number,true,numbers);

        config.states.add(config.startState);
        config.states.add(unarMinusState);
        config.states.add(number);
        config.states.add(open);
        config.states.add(operator);
        config.states.add(close);

        return config;
    }

    private static AutomatConfig getValidationConfigFromFile(){

        JAXBContext context = null;
        AutomatConfig config = null;
        try {
            context = JAXBContext.newInstance(AutomatConfig.class, AutomatState.class, AutomatStateLink.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            try (FileReader reader = new FileReader("AutomatConfig.xml")) {
                config = (AutomatConfig)unmarshaller.unmarshal(reader);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (JAXBException e) {
        e.printStackTrace();
    }

        return config;
    }

    @Test
    public void simpleCorrectExpr(){

        Automat automat = new Automat(getValidationConfigFromFile());

        boolean result = automat.check("12+3");

        Assert.assertTrue(result);

    }

    @Test
    public void correctExpr(){

        Automat automat = new Automat(getValidationConfigFromFile());

        boolean result = automat.check("-12+3*(5/6+(2--3))");

        Assert.assertTrue(result);

    }

    @Test
    public void incorrectExpr(){

        Automat automat = new Automat(getValidationConfigFromFile());

        boolean result = automat.check("-12+3*(*5/6+(2--3))");

        Assert.assertFalse(result);

    }

    @Test
    public void printXML() throws JAXBException {

        JAXBContext context = JAXBContext.newInstance( AutomatConfig.class, AutomatState.class, AutomatStateLink.class);

        Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        StringWriter writer = new StringWriter();

        marshaller.marshal(getValidationConfig() , writer);

        System.out.println(writer.toString());

    }
}
