package exercicioGemini4;

public class DadoBruto {
    
    private Long id;
    private int payload;

    public DadoBruto(Long id, int payload){
        this.id = id;
        this.payload = payload;
    }

    public Long getId() {
        return id;
    }
    public int getPayload() {
        return payload;
    }
}
