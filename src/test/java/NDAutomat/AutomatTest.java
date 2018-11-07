package NDAutomat;

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
import java.util.Comparator;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class AutomatTest {
    private static String[] dnumbers = {"0","1","2","3","4","5","6","7","8","9"};
    private static String[] bnumbers = {"0","1"};
    private static String[] onumbers = {"0","1","2","3","4","5","6","7"};
    private static String[] hnumbers = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
    private static AutomatConfig getValidationConfig(){
        AutomatConfig config = new AutomatConfig();
        config.startState = new AutomatState("start");
        AutomatState close = new AutomatState("end");
        AutomatState minus = new AutomatState("minus");
        AutomatState bNum = new AutomatState("bnum");
        AutomatState dNum = new AutomatState("dnum");
        AutomatState oNum = new AutomatState("onum");
        AutomatState hNum = new AutomatState("hnum");

        AutomatStateLink.create(config.startState,minus, true,"-");
        AutomatStateLink.createEpsilon(config.startState, bNum, true);
        AutomatStateLink.createEpsilon(config.startState, dNum, true);
        AutomatStateLink.createEpsilon(config.startState, oNum, true);
        AutomatStateLink.createEpsilon(config.startState, hNum, true);
        AutomatStateLink.createEpsilon(minus, bNum, true);
        AutomatStateLink.createEpsilon(minus, dNum, true);
        AutomatStateLink.createEpsilon(minus, oNum, true);
        AutomatStateLink.createEpsilon(minus, hNum, true);

        AutomatStateLink.create(bNum, bNum, true, bnumbers);
        AutomatStateLink.create(dNum, dNum, true, dnumbers);
        AutomatStateLink.create(oNum, oNum, true, onumbers);
        AutomatStateLink.create(hNum, hNum, true, hnumbers);

        config.states.add(config.startState);
        config.states.add(minus);
        config.states.add(bNum);
        config.states.add(dNum);
        config.states.add(oNum);
        config.states.add(hNum);

        return config;
    }

    enum Cls{
        bin,dec,oct,hex
    }

    class Clasifier{
        private final Automat automat;

        Clasifier(Automat automat) {
            this.automat = automat;
        }

        Cls getCls(String expr){
            Cls cls = null;
            Set<AutomatState> sources = automat.check(expr);
            if(sources.size() == 0)
                return null;
            AutomatState state = sources.iterator().next();
            if (sources.size() > 1){
                TreeMap<String,Integer> priority = new TreeMap<>();
                priority.put("bnum",4);
                priority.put("dnum",2);
                priority.put("onum",3);
                priority.put("hnum",1);
                state = sources.stream().max( Comparator.comparing(first -> {
                    try {
                        return priority.get(first.name);
                    }
                    catch (Exception e){
                        return 0;
                    }
                } ) ).orElse(null);
            }
            switch (state.name){
                case "bnum": cls = Cls.bin; break;
                case "dnum": cls = Cls.dec; break;
                case "onum": cls = Cls.oct; break;
                case "hnum": cls = Cls.hex; break;
            }
            return cls;
        }
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
    public void all(){

        Automat automat = new Automat(getValidationConfig());

        Clasifier clasifier = new Clasifier(automat);

        Assert.assertEquals(Cls.bin, clasifier.getCls("10011"));
        Assert.assertEquals(Cls.bin, clasifier.getCls("-10011"));
        Assert.assertEquals(Cls.oct, clasifier.getCls("100112"));
        Assert.assertEquals(Cls.oct, clasifier.getCls("-100112"));
        Assert.assertEquals(Cls.dec, clasifier.getCls("1001129"));
        Assert.assertEquals(Cls.dec, clasifier.getCls("-1001129"));
        Assert.assertEquals(Cls.hex, clasifier.getCls("1001129f"));
        Assert.assertEquals(Cls.hex, clasifier.getCls("-1001129f"));
        Assert.assertEquals(null, clasifier.getCls("-10011!29"));
        Assert.assertEquals(null, clasifier.getCls("1001!129f"));
        Assert.assertEquals(null, clasifier.getCls("-10@011"));
        Assert.assertEquals(null, clasifier.getCls("10011#2"));

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
