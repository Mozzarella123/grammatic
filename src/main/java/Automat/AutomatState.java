package Automat;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlType(name = "state",namespace = "au")
public class AutomatState {

    public AutomatState(){

    }

    public AutomatState(String name){
        this(name, new AutomatStateLink[0]);
    }

    public AutomatState(String name, AutomatStateLink ... links){
        this.name = name;
        this.links = new ArrayList<AutomatStateLink>();
        for( AutomatStateLink link : links){
            this.links.add(link);
        }
    }

    @XmlID
    @XmlAttribute
    public String name;

    @XmlElement(type = AutomatStateLink.class,namespace = "au")
    public List<AutomatStateLink> links;
}
