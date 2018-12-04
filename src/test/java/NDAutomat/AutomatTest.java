package NDAutomat;

import Core.AutomatState;
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
import java.util.Set;
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
        AutomatState bFin = new AutomatState("bFin");
        AutomatState dFin = new AutomatState("dFin");
        AutomatState oFin = new AutomatState("oFin");
        AutomatState hFin = new AutomatState("hFin");

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

        AutomatStateLink.create(bNum,bFin,true,"b");
        AutomatStateLink.create(dNum,dFin,true,"$");
        AutomatStateLink.create(oNum,oFin,true,"o");
        AutomatStateLink.create(hNum,hFin,true,"h");

        AutomatStateLink.create(bFin,bFin,true,"$");
        AutomatStateLink.create(dFin,dFin,true,"$");
        AutomatStateLink.create(oFin,oFin,true,"$");
        AutomatStateLink.create(hFin,hFin,true,"$");

        config.states.add(config.startState);
        config.states.add(minus);
        config.states.add(bNum);
        config.states.add(dNum);
        config.states.add(oNum);
        config.states.add(hNum);
        config.states.add(bFin);
        config.states.add(dFin);
        config.states.add(oFin);
        config.states.add(hFin);

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
            Set<AutomatState> sources = automat.check(expr).stream().filter( s -> s.name.endsWith("Fin"))
                    .collect(Collectors.toSet());
            if(sources.size() == 0)
                return null;
            AutomatState state = sources.iterator().next();
            switch (state.name){
                case "bFin": cls = Cls.bin; break;
                case "dFin": cls = Cls.dec; break;
                case "oFin": cls = Cls.oct; break;
                case "hFin": cls = Cls.hex; break;
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


        Assert.assertEquals(Cls.oct, clasifier.getCls("10011o"));
        Assert.assertEquals(Cls.bin, clasifier.getCls("10011b"));
        Assert.assertEquals(Cls.bin, clasifier.getCls("-10011b"));
        Assert.assertEquals(Cls.oct, clasifier.getCls("100112o"));
        Assert.assertEquals(Cls.oct, clasifier.getCls("-100112o"));
        Assert.assertEquals(Cls.dec, clasifier.getCls("1001129"));
        Assert.assertEquals(Cls.dec, clasifier.getCls("-1001129"));
        Assert.assertEquals(Cls.hex, clasifier.getCls("1001129fh"));
        Assert.assertEquals(Cls.hex, clasifier.getCls("-1001129fh"));
        Assert.assertEquals(null, clasifier.getCls("-10011!29"));
        Assert.assertEquals(null, clasifier.getCls("1001!129fh"));
        Assert.assertEquals(null, clasifier.getCls("-10@011b"));
        Assert.assertEquals(null, clasifier.getCls("10011#2o"));
        Assert.assertEquals(null, clasifier.getCls("10012b"));
        Assert.assertEquals(null, clasifier.getCls("10012a"));

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
