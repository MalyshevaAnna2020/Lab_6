import java.io.Serializable;

public class Command implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;//wow! :()
    private NameOfCommand nameOfCommand;
    private SpaceMarine spaceMarine;
    private String key;
    private int i;

    public NameOfCommand getNameOfCommand() {
        return nameOfCommand;
    }

    public void setNameOfCommand(NameOfCommand nameOfCommand) {
        this.nameOfCommand = nameOfCommand;
    }

    public SpaceMarine getSpaceMarine() {
        return spaceMarine;
    }

    public void setSpaceMarine(SpaceMarine spaceMarine) {
        this.spaceMarine = spaceMarine;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
}
