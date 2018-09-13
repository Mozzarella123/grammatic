package com.nikitarogov;

import java.util.List;

public abstract class Rule {
    protected List<Symbol> leftpart;
    protected List<Symbol> rightpart;

    public Rule(List<Symbol> leftpart, List<Symbol> rightpart) {
        this.leftpart = leftpart;
        this.rightpart = rightpart;
    }

    public List<Symbol> getLeftpart() {
        return leftpart;
    }

    public List<Symbol> getRightpart() {
        return rightpart;
    }

    public abstract Boolean validate();
}
