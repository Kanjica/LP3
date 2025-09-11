public class Calc {

    private int n1,n2,res = 0;
    private char op;

    public Calc(){
        this.n1 = 0;
        this.n2 = 0;
        this.op = '\u0000';
    }

    public Calc(int n1, int n2, char op) {
        this.n1 = n1;
        this.n2 = n2;
        this.op = op;
    }

    private int getN1() {
        return n1;
    }

    public void setN1(int n1) {
        this.n1 = n1;
    }

    private int getN2() {
        return n2;
    }

    public void setN2(int n2) {
        this.n2 = n2;
    }

    private char getOp() {
        return op;
    }

    public void setOp(char op) {
        this.op = op;
    }

    private void add(){res = this.getN1()+this.getN2();}
    private void sub(){res = this.getN1()-this.getN2();}
    private void div(){res = (this.getN1() == 0 || this.getN2() == 0 )? 0: this.getN1()/this.getN2();}
    private void mul(){res = this.getN1()*this.getN2();}

    public int calcular(){
        switch(op){
            case '+'->{
                this.add();
            }
            case '-'->{
                this.sub();
            }
            case '*'->{
                this.mul();
            }
            case '/'->{
                this.div();
            }

        }
        return res;
    }

}

