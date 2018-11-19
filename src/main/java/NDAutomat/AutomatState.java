package NDAutomat;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AutomatState that = (AutomatState) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(links, that.links);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, links);
    }
}
