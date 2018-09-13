package com.nikitarogov;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public abstract class Grammatic {
    private final List<Symbol> terminals;
    private final List<Symbol> nonterminals;
    private final NonTerminalSymbol axiome;
    private final List<Rule> rules;

    public Grammatic(List<Symbol> terminals, List<Symbol> nonterminals, NonTerminalSymbol axiome, List<Rule> rules) {
        this.terminals = terminals;
        this.nonterminals = nonterminals;
        this.axiome = axiome;
        this.rules = rules;
        if (!rules.stream().allMatch(r -> r.validate())
                && nonterminals.stream().anyMatch(s -> s.getName().equals(axiome.getName()))) {
            throw new IllegalArgumentException();
        }
    }

    private Boolean applyRule(List<Symbol> symbols, Rule rule) {
        List<Symbol> nonT = symbols.stream().filter(s -> s instanceof NonTerminalSymbol
                && rule.getLeftpart().stream()
                .anyMatch(sym -> sym.equals(s))).collect(Collectors.toList());
        if (nonT.isEmpty()) {
            return false;
        }
        for (Symbol s : nonT) {
            int i = symbols.indexOf(s);
            symbols.addAll(i + 1, rule.getRightpart());
            symbols.remove(i);
        }
        return true;
    }

    public List<Symbol> apply(int length) {
        final Random r = new Random();
        List<Symbol> current = new ArrayList<>();
        current.add(axiome);
        for (int i = 0; i < length; i++) {
            List<Rule> validrules = rules.stream()
                    .filter(rl-> current.stream()
                            .anyMatch(s-> s.equals(rl.getLeftpart().get(0))))
                    .collect(Collectors.toList());
            if (validrules.isEmpty()) {
                break;
            } else {
                applyRule(current, validrules.get(r.nextInt(validrules.size())));
            }
        }
        //обработать случай, когда нет правил
        List<Rule> nonTRules = rules.stream()
                .filter(rule -> rule.getRightpart().stream()
                        .anyMatch(s -> s instanceof TerminalSymbol))
                .collect(Collectors.toList());
        for (Rule rule : nonTRules)
        {
            applyRule(current, rule);
        }
        return current;
    }
}
