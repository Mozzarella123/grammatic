
import NDAutomat.AutomatConfig;

public interface IAutomation {
    void reset();
    void add();
    boolean accepts();
    void load(AutomatConfig config);
    void save();
    boolean check();
}
