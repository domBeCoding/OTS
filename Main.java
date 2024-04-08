

public class Main {

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    private LoginService loginService;
    private AccountController accountController;
    public static Account sessionAccount;
    private String accountType = "";

    public void start(){

    Utility.printHeaderMessage("Hello, welcome to the Online Ticketing System portal");

    accountType = LoginController.selectUserLoginPrompt();
    loginService = LoginServiceBuilder.build(accountType);

    LoginController.inputCredentialsPrompt(loginService);
    sessionAccount = loginService.getAccount();

    Utility.printHeaderMessage("Welcome " + sessionAccount.getName() + ", you have logged in as a " + sessionAccount.getClass().getSimpleName());
    accountController = new AccountController(sessionAccount);
    accountController.selectAccountMenuPrompt();
    }
}
