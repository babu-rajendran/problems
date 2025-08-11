package dev.babu.solid;

// Single Responsibility Principle

public interface PaymentMethod {

    void pay(double amount);

}

// Interface Segregation Principle
// If some payments need refunds and others don’t
interface RefundablePayment {
    void refund(double amount);
}

class PaypalRefundablePayment extends PaypalPayment implements RefundablePayment {
    @Override
    public void refund(double amount) {
        System.out.println("Refunded $" + amount + " via PayPal");
    }
}

class CreditCardPayment implements PaymentMethod {
    @Override
    public void pay(double amount) {
        System.out.println("Paid " + amount + " using Credit Card");
    }
}

class PaypalPayment implements PaymentMethod {
    @Override
    public void pay(double amount) {
        System.out.println("Paid " + amount + " using PayPal");
    }
}
