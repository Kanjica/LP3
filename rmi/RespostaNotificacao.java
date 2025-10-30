package rmi;

import java.io.Serializable;

public class RespostaNotificacao implements Serializable {
    private static final long serialVersionUID = 1L; 

    private String mensagem;
    private boolean retorno; 

    public RespostaNotificacao(String mensagem, boolean retorno) {
        this.mensagem = mensagem;
        this.retorno = retorno; 
    }

    public String getMensagem() {
        return mensagem;
    }

    public boolean getRetorno() {
        return retorno;
    }

    public String toString(){
        return mensagem+  " || " + retorno;
    }
}