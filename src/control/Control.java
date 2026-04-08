package control;

public class Control {

    public static void main(String[] args) {

        View view = new View();
        Model model = new Model();

        do {
            // returns an array of username[0] and password
            String[] inputArgs = view.login();
        }
        while(DataBase.hasUser(inputArgs));

        view.openDash(model.getActiveUser());
        view.setDatabase(DataBase);

    }

    public void Search(String keyword) {

    }

}
