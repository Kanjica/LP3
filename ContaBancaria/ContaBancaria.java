package ContaBancaria;

public class ContaBancaria {
    private double saldo;

    public ContaBancaria(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    public synchronized void sacar(double valor) throws SaldoInsuficienteException {
        if (saldo >= valor) {
            saldo -= valor;
        } else {
            throw new SaldoInsuficienteException(valor);
        }
    }

    public synchronized void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
        }
    }

    public synchronized double getSaldo() {
        return saldo;
    }
}
