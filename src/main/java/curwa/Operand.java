package curwa;

class Operand<T> extends Part {
    T value;
    public Operand(T value)
    {
        this.value = value;
    }
    @Override
    public String toString()
    {
        return value.toString();
    }
}

