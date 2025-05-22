package br.com.joaopedrofazzolo.itenspregao.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ConsultaLog {
    private String uasg;
    private String tipo;
    private String processo;
    private int quantidadeItens;
    private long timestamp;

    public ConsultaLog() {
    }

    public ConsultaLog(String uasg, String tipo, String processo, int quantidadeItens) {
        this.uasg = uasg;
        this.tipo = tipo;
        this.processo = processo;
        this.quantidadeItens = quantidadeItens;
        this.timestamp = System.currentTimeMillis();
    }
}