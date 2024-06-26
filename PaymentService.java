

public class PaymentService {
    public void pay(Account account, Transaction transaction) {
        if(account.getCardNumber() != Integer.parseInt(transaction.getCardNumber())) throw new InvalidCardNumberException("Invalid card number");
        if(account.getPin() != Integer.parseInt(transaction.getCardPin())) throw new InvalidPinNumberException("Invalid pin number");
    }
}
