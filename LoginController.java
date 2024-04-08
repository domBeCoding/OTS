public class LoginController {

    public static String selectUserLoginPrompt() {
        String sessionUserType = "";

        boolean validOptionSelected = false;

        while (!validOptionSelected) {
            Utility.printSpace();
            System.out.println("Menu:");
            Utility.printMessage("1 - Customer interface");
            Utility.printMessage("2 - Ticket agent management");
            Utility.printMessage("3 - Venue management");
            Utility.printSpace();
            Utility.printMessage("Please select a portal for login");

            try {
                sessionUserType = InputReader.readWithSetOptions(3);
                validOptionSelected = true;

            } catch (RuntimeException e) {
                Utility.printHeaderMessage("Invalid input, please try again");
            }
        }

        return sessionUserType;
    }

    public static void inputCredentialsPrompt(LoginService loginService) {
        String password;
        String sessionUserId;
        boolean isLoggedIn = false;

        while (!isLoggedIn) {
            Utility.printSpace();
            Utility.printMessage("To proceed, please enter your username:");
            sessionUserId = InputReader.read().trim();

            Utility.printMessage("Please enter you password:");
            password = InputReader.read().trim();

            boolean loginResult = loginService.login(new Credentials(sessionUserId, password));

            if (!loginResult) {
                Utility.printHeaderMessage("Your login credentials are invalid, please try again");
            }

            isLoggedIn = loginResult;
        }
    }
}
