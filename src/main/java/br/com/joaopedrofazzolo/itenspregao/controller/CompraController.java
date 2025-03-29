package br.com.joaopedrofazzolo.itenspregao.controller;

import br.com.joaopedrofazzolo.itenspregao.dto.CompraResponseDTO;
import br.com.joaopedrofazzolo.itenspregao.service.CompraService;
import br.com.joaopedrofazzolo.itenspregao.service.ExcelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class CompraController {
    private static final Logger logger = LoggerFactory.getLogger(CompraController.class);
    @Autowired
    private CompraService compraService;

    @Autowired
    private ExcelService excelService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/gerarExcel")
    public ResponseEntity<byte[]> gerarExcel(@RequestParam String uasg, @RequestParam String tipo, @RequestParam String pregao) throws IOException{
        String idCompra = uasg + tipo + pregao;
        CompraResponseDTO compraResponseDTO = compraService.obterDadosCompra(idCompra);
        logger.info("UASG: " + uasg + " Tipo: " + tipo + " Pregao: " + pregao);
        byte[] excel = excelService.gerarExcel(compraResponseDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=compra_" + pregao + ".xlsx");
        logger.info("planilha criado com sucesso");
        return  ResponseEntity.ok().headers(headers).body(excel);
    }
}
