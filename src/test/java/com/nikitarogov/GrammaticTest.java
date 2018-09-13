package com.nikitarogov;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GrammaticTest {
    @Test
    public void applyTest()
    {

        List<Symbol> t = new ArrayList<>();
        t.add(new TerminalSymbol("a"));
        t.add(new TerminalSymbol("b"));
        t.add(new TerminalSymbol("c"));
        List<Symbol> nt = new ArrayList<>();
        nt.add(new NonTerminalSymbol("A"));
        nt.add(new NonTerminalSymbol("B"));
        nt.add(new NonTerminalSymbol("C"));
        nt.add(new NonTerminalSymbol("S"));
        NonTerminalSymbol a = new NonTerminalSymbol("S");
        List<Rule> r = new ArrayList<>();
        List<Symbol> lp = new ArrayList<>();
        List<Symbol> rp = new ArrayList<>();
        lp.add(new NonTerminalSymbol("A"));
        rp.add(new TerminalSymbol("a"));
        r.add(new ContextFreeRule(lp,rp));
        lp = new ArrayList<>();
        rp = new ArrayList<>();
        lp.add(new NonTerminalSymbol("S"));
        rp.add(new NonTerminalSymbol("A"));
        rp.add(new NonTerminalSymbol("B"));
        rp.add(new NonTerminalSymbol("C"));
        r.add(new ContextFreeRule(lp,rp));
        lp = new ArrayList<>();
        rp = new ArrayList<>();
        lp.add(new NonTerminalSymbol("A"));
        rp.add(new NonTerminalSymbol("A"));
        rp.add(new TerminalSymbol("a"));
        r.add(new ContextFreeRule(lp,rp));
        lp = new ArrayList<>();
        rp = new ArrayList<>();
        lp.add(new NonTerminalSymbol("B"));
        rp.add(new TerminalSymbol("b"));
        r.add(new ContextFreeRule(lp,rp));
        lp = new ArrayList<>();
        rp = new ArrayList<>();
        lp.add(new NonTerminalSymbol("B"));
        rp.add(new NonTerminalSymbol("B"));
        rp.add(new TerminalSymbol("b"));
        r.add(new ContextFreeRule(lp,rp));
        lp = new ArrayList<>();
        rp = new ArrayList<>();
        lp.add(new NonTerminalSymbol("C"));
        rp.add(new NonTerminalSymbol("C"));
        rp.add(new TerminalSymbol("c"));
        r.add(new ContextFreeRule(lp,rp));
        lp = new ArrayList<>();
        rp = new ArrayList<>();
        lp.add(new NonTerminalSymbol("C"));
        rp.add(new TerminalSymbol("c"));
        r.add(new ContextFreeRule(lp,rp));
        Grammatic g = new Grammatic(t,nt,a,r) {
            @Override
            public List<Symbol> apply(int length) {
                return super.apply(length);
            }
        };
        List<Symbol> s = g.apply(10);
        s.forEach( e -> System.out.print(e.getName()));
        assertEquals("a",s.get(0).getName());
        assertTrue( s.size() <= 10);
    }
}