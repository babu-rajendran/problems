package dev.babu.solid;

// If a new CryptoPayment is added, no change in PaymentService.
// Open Closed Principle
public class PaymentService {

    private PaymentMethod paymentMethod;

    //Dependency Inversion Principle
    // PaymentService depends on the PaymentMethod abstraction, not concrete classes

    public PaymentService(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void process(double amount) {
        paymentMethod.pay(amount);
    }

    public static void main(String[] args) {
        // Liskov Substitution Principle
        PaymentService service = new PaymentService(new PaypalPayment());
        service.process(100.0); // Works exactly like CreditCardPayment
    }
}
