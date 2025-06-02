package br.com.joaopedrofazzolo.itenspregao.model;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
public class ConsultaLogModel {
    private String uasg;
    private String tipo;
    private String processo;
    private int quantidadeItens;
    private String timestamp;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public ConsultaLogModel() {
    }

    public ConsultaLogModel(String uasg, String tipo, String processo, int quantidadeItens) {
        this.uasg = uasg;
        this.tipo = tipo;
        this.processo = processo;
        this.quantidadeItens = quantidadeItens;
        this.timestamp = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).format(formatter);
    }
}