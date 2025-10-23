package com.ideau.cadastroProdutos.model;

public class RespostaGenericaHTTP {
    String strMensagem;

    public RespostaGenericaHTTP(String strMensagem) {
        this.strMensagem = strMensagem;
    }
    public String getStrMensagem() {
        return this.strMensagem;
    }
}
