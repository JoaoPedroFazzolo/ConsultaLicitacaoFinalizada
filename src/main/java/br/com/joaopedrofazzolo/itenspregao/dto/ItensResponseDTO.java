package br.com.joaopedrofazzolo.itenspregao.dto;

import br.com.joaopedrofazzolo.itenspregao.model.CompraItemModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ItensResponseDTO {
    private List<CompraItemModel> resultado;
}
