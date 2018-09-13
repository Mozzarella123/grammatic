package curwa;

public class Operator  extends  Part{
    int type;
    String value;
    public Operator(String o, int type) {
        this.type = type;
        value = o;
    }
    @Override
    public String toString()
    {
        return value;
    }

}
