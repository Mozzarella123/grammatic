package NDAutomat;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlType(namespace = "au")
@XmlRootElement(namespace = "au")
public class AutomatConfig {

    @XmlElement(type = AutomatState.class,namespace = "au")
    public List<AutomatState> states = new ArrayList<>();

    @XmlIDREF
    @XmlAttribute(required = true,name = "start",namespace = "au")
    public AutomatState startState;
}
