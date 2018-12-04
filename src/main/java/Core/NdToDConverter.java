package Core;


import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class NdToDConverter {

    private List<Automat.AutomatState> getDTAStates(NDAutomat.AutomatConfig config){
        List<Automat.AutomatState> resultStates = new ArrayList<>();

        resultStates.add(map(config.startState));
        NDAutomat.AutomatState lastProcessed = config.startState;

        while(true){
            Set<NDAutomat.AutomatState> currentStates = new LinkedHashSet<>();
            Set<NDAutomat.AutomatState> possibleStates = new LinkedHashSet<>();
            for
            break;
        }

        return resultStates;
    }

    private List<NDAutomat.AutomatState> getPossibleStates(NDAutomat.AutomatState state){

    }

    private Automat.AutomatState map(NDAutomat.AutomatState state){
        Automat.AutomatState result = new Automat.AutomatState(state.name);
        return  result;
    }
}
