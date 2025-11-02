package ContaBancaria;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ContaBancaria {
    private String nome;
    private double saldo;
    private ReadWriteLock lock;

    public ContaBancaria(String nome, double saldoInicial) {
        this.nome = nome;
        this.saldo = saldoInicial;
        this.lock = new ReentrantReadWriteLock();
    }

    public double sacar(double valor) throws SaldoInsuficienteException {
        lock.writeLock().lock();
        try{
            if (saldo >= valor) {
                saldo -= valor;
                return saldo;
            } 
            throw new SaldoInsuficienteException(valor);
        }finally{
            lock.writeLock().unlock();
        }
    }

    
    public void depositar(double valor) {
        lock.writeLock().lock();
        try {
            saldo += valor;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public String getNome() {
        return nome;
    }

    public double getSaldo() {
        lock.readLock().lock();  
        try {
            return saldo;
        } finally {
            lock.readLock().unlock();
        }
    }

    public ResultadoSaque sacarComInformacao(double valor){
        lock.writeLock().lock();

        try{
            double saldoAnterior = saldo;
            if (saldo < valor){
                return new ResultadoSaque(false, valor, saldoAnterior, saldo);
            }
            saldo -= valor;
            return new ResultadoSaque(true, valor, saldoAnterior, saldo);

        } finally {
            lock.writeLock().unlock();
        }

    }    

    public boolean transferir(ContaBancaria alvo, double valor) {
        ContaBancaria primeira = this.nome.compareTo(alvo.nome) < 0 ? this : alvo;
        ContaBancaria segunda = this.nome.compareTo(alvo.nome) < 0 ? alvo : this;
        
        primeira.lock.writeLock().lock();
        try {
            segunda.lock.writeLock().lock();
            try {
                System.out.println(nome + " transferindo R$ " + String.format("%.2f", valor) + " para " + alvo.getNome());
                
                if (this.saldo >= valor) {
                    this.saldo -= valor;  
                    alvo.saldo += valor;  
                    System.out.println("Transferência bem sucedida");
                    return true;
                } else {
                    System.out.println("Transferência mal sucedida - saldo insuficiente");
                    return false;
                }
            } finally {
                segunda.lock.writeLock().unlock();
            }
        } finally {
            primeira.lock.writeLock().unlock();
        }
    }
}
