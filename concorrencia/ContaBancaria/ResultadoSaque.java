package concorrencia.ContaBancaria;

public record ResultadoSaque(Boolean sucesso, double valorSaque, double saldoAnterior, double novoSaldo) {    
}
