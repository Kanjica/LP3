package concorrencia.cinema;

public class Cadeira {
    
    private int nCadeira;
    private int x, y;
    private boolean disponivel;

    public Cadeira(int nCadeira){
        this.nCadeira = nCadeira;
        this.x = -1;
        this.y = -1;
        this.disponivel = true;
    }

    public int getX() {
        return x;
    }
    public int getnCadeira() {
        return nCadeira;
    }
    public int getY() {
        return y;
    }
    
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel){
        this.disponivel = disponivel;
    }
}

