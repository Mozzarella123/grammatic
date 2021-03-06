package NDAutomat;

import Core.AutomatState;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlType(name = "link",namespace = "au")
public class AutomatStateLink {

    public static AutomatStateLink create(AutomatState from, AutomatState to, boolean ordered, String ... keys){
        AutomatStateLink link = new AutomatStateLink();
        link.from = from;
        if( !from.links.contains(link) )
            from.links.add(link);
        link.to = to;
        if( !to.links.contains(link) )
            to.links.add(link);
        link.ordered = ordered;
        if( keys.length > 0 ) {
            link.keys = new ArrayList<>();
            for (String key : keys) {
                link.keys.add(key);
            }
        }
        else{
            link.isEpsilon = true;
        }
        return link;
    }

    public static AutomatStateLink createEpsilon(AutomatState from, AutomatState to, boolean ordered){
        AutomatStateLink link = new AutomatStateLink();
        link.from = from;
        if( !from.links.contains(link) )
            from.links.add(link);
        link.to = to;
        if( !to.links.contains(link) )
            to.links.add(link);
        link.ordered = ordered;
        link.isEpsilon = true;
        return link;
    }

    @XmlIDREF
    public AutomatState from;

    @XmlIDREF
    public AutomatState to;

    @XmlAttribute
    public boolean ordered;

    @XmlAttribute
    public boolean isEpsilon = false;

    @XmlElement
    public List<String> keys;
}
