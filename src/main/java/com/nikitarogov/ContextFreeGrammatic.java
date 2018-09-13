package com.nikitarogov;

import java.util.List;

public class ContextFreeGrammatic extends Grammatic {

    public ContextFreeGrammatic(List<Symbol> terminals, List<Symbol> nonterminals, NonTerminalSymbol axiome, List<Rule> rules) {
        super(terminals, nonterminals, axiome, rules);
    }

    @Override
    public List<Symbol> apply(int length) {
        return null;
    }
}


