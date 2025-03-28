package br.com.joaopedrofazzolo.itenspregao.service;

import br.com.joaopedrofazzolo.itenspregao.dto.CompraResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CompraService {
    private static final String API_URL = "https://dadosabertos.compras.gov.br/modulo-contratacoes/2.1_consultarItensContratacoes_PNCP_14133_Id";

    public CompraResponseDTO obterDadosCompra(String idCompra) {
        RestTemplate restTemplate = new RestTemplate();
        String url = API_URL + "?idCompra=" + idCompra;

        CompraResponseDTO response = restTemplate.getForObject(url, CompraResponseDTO.class);

        return response;
    }
}
