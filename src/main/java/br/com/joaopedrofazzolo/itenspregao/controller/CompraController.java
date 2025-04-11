package br.com.joaopedrofazzolo.itenspregao.controller;

import br.com.joaopedrofazzolo.itenspregao.dto.CompraResponseDTO;
import br.com.joaopedrofazzolo.itenspregao.service.CompraService;
import br.com.joaopedrofazzolo.itenspregao.service.ExcelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Tag(name = "Busca Licitação", description = "Busca o resultado de uma licitação e gera o excel")
public class CompraController {
    private static final Logger logger = LoggerFactory.getLogger(CompraController.class);
    @Autowired
    private CompraService compraService;

    @Autowired
    private ExcelService excelService;

    @Operation(summary = "Gera um relatório em Excel",
            description = "Este endpoint gera e retorna um arquivo Excel",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Arquivo Excel gerado com sucesso",
                            content = @Content(mediaType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                                    schema = @Schema(type = "string", format = "binary"))),
                    @ApiResponse(responseCode = "500", description = "Erro ao gerar o arquivo Excel")
            })
    @GetMapping("/gerarExcel")
    public ResponseEntity<byte[]> gerarExcel(@RequestParam String uasg, @RequestParam String tipo, @RequestParam String processo) throws IOException{
        uasg = uasg.replaceAll(" ", "");
        processo = processo.replaceAll(" ", "").replaceAll("/","");
        String idCompra = uasg + tipo + processo;
        logger.info("UASG: " + uasg + " Tipo: " + tipo + " Processo: " + processo);

        CompraResponseDTO compraResponseDTO = compraService.obterDadosCompra(idCompra);

        if (compraResponseDTO == null ||
                compraResponseDTO.getResultado() == null ||
                compraResponseDTO.getResultado().isEmpty()) {
            logger.error("Dados da licitação vazios ou não encontrados para o ID: " + idCompra);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Não foi possível gerar a planilha: dados da licitação não encontrados".getBytes());
        }

        byte[] excel = excelService.gerarExcel(compraResponseDTO);


        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=compra_" + processo + ".xlsx");
        return  ResponseEntity.ok().headers(headers).body(excel);
    }
}
