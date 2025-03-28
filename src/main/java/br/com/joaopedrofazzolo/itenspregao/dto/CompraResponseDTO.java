package br.com.joaopedrofazzolo.itenspregao.dto;

import br.com.joaopedrofazzolo.itenspregao.model.CompraItemModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CompraResponseDTO {
    private List<CompraItemModel> resultado;
    private int totalRegistros;
    private int totalPaginas;
    private int paginasRestantes;
}
