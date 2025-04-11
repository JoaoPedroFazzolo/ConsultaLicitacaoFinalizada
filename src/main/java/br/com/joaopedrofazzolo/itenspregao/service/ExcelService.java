package br.com.joaopedrofazzolo.itenspregao.service;

import br.com.joaopedrofazzolo.itenspregao.model.CompraItemModel;
import br.com.joaopedrofazzolo.itenspregao.dto.CompraResponseDTO;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ExcelService {
    private static final Logger log = LoggerFactory.getLogger(ExcelService.class);

    public byte[] gerarExcel(CompraResponseDTO compraResponseDTO) throws IOException {

        if (compraResponseDTO == null) {
            log.error("Resposta da API nula. Não foi possível gerar a planilha Excel.");
            return null;
        }
        List<CompraItemModel> itens = compraResponseDTO.getResultado();
        if (itens == null || itens.isEmpty()) {
            log.error("Lista de itens vazia ou nula na resposta da API");
            return null;
        }

        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet();

            Row headerRow = sheet.createRow(0);
            String[] headers = {
                    "LOTE", "ITEM", "REQUISIÇÃO", "CNPJ", "EMPRESA", "QTDE", "UND",
                    "VALOR UNIT LIC", "VALOR TOTAL LICI", "PRAZO", "DESCRIÇÃO", "SITUAÇÃO",
                    "FORNECEDOR", "MODELO/VERSAO", "MARCA"
            };

            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            itens.sort((item1, item2) -> Integer.compare(item1.getNumeroItemPncp(), item2.getNumeroItemPncp()));
            int rowNum = 1;
            for (CompraItemModel item : itens) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue("");
                row.createCell(1).setCellValue(item.getNumeroItemPncp());
                row.createCell(2).setCellValue("");
                row.createCell(3).setCellValue(item.getCodFornecedor() != null ? item.getCodFornecedor() : "");
                row.createCell(4).setCellValue("");
                row.createCell(5).setCellValue(item.getQuantidadeResultado() > 0 ? item.getQuantidadeResultado() : 0);
                row.createCell(6).setCellValue("");
                row.createCell(7).setCellValue(formatarFloat(item.getValorUnitarioResultado() > 0 ? item.getValorUnitarioResultado() : 0));
                row.createCell(8).setCellValue(formatarFloat(item.getValorTotalResultado() > 0 ? item.getValorTotalResultado() : 0));
                if (!(item.getSituacaoCompraItemNome().equals("Deserto") ||
                        item.getSituacaoCompraItemNome().equals("Fracassado") ||
                        item.getSituacaoCompraItemNome().equals("Em andamento") ||
                        item.getSituacaoCompraItemNome().equals("Anulado/Revogado/Cancelado"))) {
                    row.createCell(9).setCellValue(30);
                }
                row.createCell(10).setCellValue("");
                row.createCell(11).setCellValue("");
                row.createCell(12).setCellValue("");
                row.createCell(13).setCellValue("");
                row.createCell(14).setCellValue("");
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            workbook.write(byteArrayOutputStream);
            workbook.close();
            log.info("planilha criado com sucesso");
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            log.error("Erro ao gerar planilha Excel", e);
            throw new RuntimeException("Erro ao gerar excel", e);
        } catch (Exception e) {
            log.error("Erro inesperado ao gerar planilha Excel", e);
            return null;
        }
    }

    private double formatarFloat(float valor) {
        return new BigDecimal(valor).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
