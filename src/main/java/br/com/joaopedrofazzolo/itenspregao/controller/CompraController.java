package br.com.joaopedrofazzolo.itenspregao.controller;

import br.com.joaopedrofazzolo.itenspregao.dto.CompraResponseDTO;
import br.com.joaopedrofazzolo.itenspregao.service.CompraService;
import br.com.joaopedrofazzolo.itenspregao.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class CompraController {

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

        byte[] excel = excelService.gerarExcel(compraResponseDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=compra_" + pregao + ".xlsx");

        return  ResponseEntity.ok().headers(headers).body(excel);
    }
}
