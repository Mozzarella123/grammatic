package com.nikitarogov;

import java.util.List;

public class ContextFreeRule extends Rule {
    public ContextFreeRule(List<Symbol> leftpart, List<Symbol> rightpart) {
        super(leftpart, rightpart);
    }

    @Override
    public Boolean validate() {
        return leftpart.stream().allMatch(symbol -> symbol instanceof NonTerminalSymbol);
    }
}
