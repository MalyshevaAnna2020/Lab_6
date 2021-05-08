import java.io.Serializable;

public class Command implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L; // wow :()
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

    public byte[] getBytes(){
        byte [] b = new byte[4];
        if (this.getNameOfCommand() == NameOfCommand.HELP){
            b[0] = 1;
        }else if(this.getNameOfCommand() == NameOfCommand.INFO){
            b[0] = 2;
        }else if(this.getNameOfCommand() == NameOfCommand.SHOW){
            b[0] = 3;
        }else if(this.getNameOfCommand() == NameOfCommand.INSERT){
            b[0] = 4;
        }else if(this.getNameOfCommand() == NameOfCommand.UPDATE){
            b[0] = 5;
        }else if(this.getNameOfCommand() == NameOfCommand.REMOVE_KEY){
            b[0] = 6;
        }else if(this.getNameOfCommand() == NameOfCommand.CLEAR){
            b[0] = 7;
        }else if(this.getNameOfCommand() == NameOfCommand.EXECUTE_SCRIPT){
            b[0] = 8;
        }else if(this.getNameOfCommand() == NameOfCommand.REMOVE_LOWER){
            b[0] = 9;
        }else if(this.getNameOfCommand() == NameOfCommand.REMOVE_GREATER_KEY){
            b[0] = 10;
        }else if(this.getNameOfCommand() == NameOfCommand.REMOVE_LOWER_KEY){
            b[0] = 11;
        }else if(this.getNameOfCommand() == NameOfCommand.REMOVE_ANY_BY_CHAPTER){
            b[0] = 12;
        }else if(this.getNameOfCommand() == NameOfCommand.FILTER_GREATER_THAN_ACHIEVEMENTS){
            b[0] = 13;
        }else if(this.getNameOfCommand() == NameOfCommand.PRINT_FIELD_DESCENDING_CATEGORY){
            b[0] = 14;
        }else{
            b[0] = 0;
        }

        return b;
    }
}
