package NDAutomat;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Automat {

    @FunctionalInterface
    public interface LinkExecuteListener{
        void execute(Set<AutomatState> oldState, Set<AutomatState> newState, Set<AutomatStateLink> link);
    }

    private static final Random random = new Random();

    private final AutomatConfig config;
    private Set<AutomatState> state;
    private final List<LinkExecuteListener> listeners = new ArrayList<>();

    public Automat(AutomatConfig config){
        this.config = config;
        state = new LinkedHashSet<>();
    }

    public void addListener(LinkExecuteListener listener){
        listeners.add(listener);
    }

    public Set<AutomatState> check(String expression){

        state.clear();
        state.add(config.startState);

        for( int i = 0; i < expression.length(); i++){
            while (addEpsilon());
            Set<AutomatState> tempstate = consume(expression.charAt(i));
            state.clear();
            state.addAll(tempstate);
            if(state.size() == 0){
                return new LinkedHashSet<>();
            }
        }

        return state;
    }

    private boolean addEpsilon(){
        int startSize = state.size();
        Set<AutomatState> addedState = new LinkedHashSet<>();
        for( AutomatState singleState : state ){
            singleState.links.stream().filter( l -> l.isEpsilon && l.from.equals(singleState) )
                    .forEach( l -> addedState.add(l.to));
        }
        state.addAll(addedState);
        return startSize != state.size();
    }

    private Set<AutomatState> consume(Character symbol){

        Set<AutomatStateLink> links = state.stream().flatMap(s -> s.links.stream() )
        .filter( l -> l.isEpsilon || l.keys.contains(symbol.toString()) )
        .filter( l -> l.ordered && state.contains(l.from)).collect(Collectors.toSet());

        changeState(links);

        Set<AutomatState> result = links.stream().map( l -> l.to )
                .collect(Collectors.toSet());

        return result;
    }


    private void changeState(Set<AutomatStateLink> links){

        final Set<AutomatState> fromStates = new LinkedHashSet<>();
        final Set<AutomatState> toStates = new LinkedHashSet<>();

        for( AutomatStateLink link : links) {
            if (link.ordered) {
                toStates.add(link.to);
                fromStates.add(link.from);
            } else {
                if (state.equals(link.from)) {
                    toStates.add(link.to);
                    fromStates.add(link.from);
                } else {
                    toStates.add(link.from);
                    fromStates.add(link.to);
                }
            }
        }

        listeners.forEach( l -> l.execute(fromStates,toStates,links));
    }

}
