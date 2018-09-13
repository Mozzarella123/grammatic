package com.nikitarogov;

public abstract class Symbol {
    public String getName() {
        return name;
    }

    private final String name;

    public Symbol(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return ((Symbol) obj).name.equals(name);
    }
}
