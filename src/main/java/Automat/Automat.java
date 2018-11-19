package Automat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Automat {

    @FunctionalInterface
    public interface LinkExecuteListener{
        void execute(AutomatState oldState, AutomatState newState, AutomatStateLink link);
    }

    private static final Random random = new Random();

    private final AutomatConfig config;
    private AutomatState state;
    private final List<LinkExecuteListener> listeners = new ArrayList<>();

    public Automat(AutomatConfig config){
        this.config = config;
        state = config.startState;
    }

    public void addListener(LinkExecuteListener listener){
        listeners.add(listener);
    }

    public boolean check(String expression){

        for( int i = 0; i < expression.length(); i++){
            if(!consume(expression.charAt(i))){
                return false;
            }
        }

        return true;
    }

    private boolean consume(Character symbol){
        List<AutomatStateLink> links = state.links.stream()
                .filter( l -> l.keys.stream().anyMatch( key ->key.equals(symbol.toString()) ) )
                .filter( l -> l.ordered && state.equals(l.from))
                .collect(Collectors.toList());
        if( links.size() == 0 ){
            return false;
        }
        else{
            AutomatStateLink link = links.get(random.nextInt(links.size()));

            changeState(link);

            return true;
        }
    }

    private void changeState(AutomatStateLink link){

        AutomatState newState;

        if(link.ordered){
            newState = link.to;
        }
        else{
            if( state.equals(link.from) )
            {
                newState = link.to;
            }
            else
            {
                newState = link.from;
            }
        }

        listeners.forEach( l -> l.execute(state,newState,link));
        state = newState;
    }

}
