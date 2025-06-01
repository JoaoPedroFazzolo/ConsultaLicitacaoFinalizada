package br.com.joaopedrofazzolo.itenspregao.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ConsultaLogModel {
    private String uasg;
    private String tipo;
    private String processo;
    private int quantidadeItens;
    private String timestamp;

    public ConsultaLogModel() {
    }

    public ConsultaLogModel(String uasg, String tipo, String processo, int quantidadeItens) {
        this.uasg = uasg;
        this.tipo = tipo;
        this.processo = processo;
        this.quantidadeItens = quantidadeItens;
        this.timestamp = LocalDateTime.now().toString();
    }
}