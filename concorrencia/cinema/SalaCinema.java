package cinema;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SalaCinema {
    
    private List<Cadeira> cadeiras;
    private AtomicInteger ingressosVendidos;
    private Lock lock;
    
    public SalaCinema(){
        // criando a lista de cadeiras com stream
        this.cadeiras = IntStream.rangeClosed(0, 20) 
                .mapToObj(numero -> new Cadeira(numero)) 
                .collect(Collectors.toCollection(ArrayList::new));
        this.ingressosVendidos = new AtomicInteger(0);
        this.lock = new ReentrantLock();
    }

    public Cadeira comprarIngresso(){
        lock.lock();
        try{
            //verifica se a cadeira está disponivel
            Optional<Cadeira> cadeiraDisponivel = this.cadeiras.stream()
                                                .filter(Cadeira::isDisponivel).findFirst();
            //se disponivel muda o estado e define a fileira e coluna da cadeira
            cadeiraDisponivel.ifPresent(cadeira->{
                cadeira.setDisponivel(false);
                int nCadeira = cadeira.getnCadeira();
                int cadeirasPorFileira = cadeiras.size();
                int fileira = (nCadeira / cadeirasPorFileira) + 1;///x
                int coluna = (nCadeira % cadeirasPorFileira) + 1; //y
                cadeira.setX(fileira);
                cadeira.setY(coluna);
                //aumenta a quantidade de ingressos vendidos
                ingressosVendidos.getAndIncrement();
            });
            //retorna a cadeira, caso não consiga retorna null
            return cadeiraDisponivel.orElse(null);
        } finally{
            lock.unlock();
        }
    }
}
