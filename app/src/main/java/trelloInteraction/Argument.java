package trelloInteraction;

public class Argument {

    private String argName;
    private String argValue;

    public static Argument arg(String argName, String argValue) {
        return new Argument(argName,argValue);
    }
    private Argument(String argName, String argValue) {
        this.argName = argName;
        this.argValue = argValue;
    }

    public String getArgName() {
        return argName;
    }

    public String getArgValue() {
        return argValue;
    }
}


