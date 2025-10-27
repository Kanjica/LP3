package cinema;

public class Cliente implements Runnable{

    private String nome;
    private SalaCinema salaCinema;
    public Cliente(String nome, SalaCinema salaCinema){
        this.nome = nome;
        this.salaCinema = salaCinema;
    }

    @Override
    public void run() {
        Cadeira cadeira = salaCinema.comprarIngresso();

        if(cadeira == null){
            System.out.println("O cliente "+ this.nome +" não conseguiu um lugar na sala...");
        }
        else{
            System.out.println("O cliente "+ this.nome +" se sentou na cadeira " + cadeira.getnCadeira() + ".");
        }
    }
    
}
