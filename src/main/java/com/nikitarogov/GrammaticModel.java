package com.nikitarogov;

import java.util.List;

public class GrammaticModel {
    public List<TerminalSymbol> terminalSymbols;
    public List<NonTerminalSymbol> nonTerminalSymbols;
    public NonTerminalSymbol axiom;
    public List<Rule> rules;
}
