package CLI;

public class CLI {
    private CLI(){}

    private static class CliHolder {
        private static CLI INSTANCE = new CLI();
    }

    public static CLI getInstance(){
        return CliHolder.INSTANCE;
    }

    public void clear(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void Update(){

    }
}
