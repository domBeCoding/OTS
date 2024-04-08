import java.util.List;

public class CustomerReader extends FileReader<Customer> {
    public CustomerReader() {
        super(Customer.class);
    }

    public List<Ticket> getTickets(String userId) {
        read();
        return objectList.stream()
                .filter(x -> x.getUserId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new NoSuchAccountException("Customer not found: " + userId))
                .getPurchasedTickets();
    }

    public Customer get(String userId) {
        read();
        return objectList.stream()
                .filter(x -> x.getUserId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new NoSuchAccountException("Customer not found: " + userId));
    }
}
