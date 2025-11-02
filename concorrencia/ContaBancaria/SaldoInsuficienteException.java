package ContaBancaria;

public class SaldoInsuficienteException extends Exception {
    public SaldoInsuficienteException(double valor) {
        super("Saldo insuficiente para sacar R$ " + String.format("%.2f", valor));
    }
}
