package br.com.joaopedrofazzolo.itenspregao.service;

import br.com.joaopedrofazzolo.itenspregao.dto.CompraResponseDTO;
import br.com.joaopedrofazzolo.itenspregao.model.ConsultaLog;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.firebase.cloud.FirestoreClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class CompraService {
    private static final Logger logger = LoggerFactory.getLogger(CompraService.class);
    private static final String API_URL = "https://dadosabertos.compras.gov.br/modulo-contratacoes/2.1_consultarItensContratacoes_PNCP_14133_Id?tipo=idCompra&";
    public CompraResponseDTO obterDadosCompra(String idCompra) {
        RestTemplate restTemplate = new RestTemplate();
        String url = API_URL + "?tipo=idCompra&codigo=" + idCompra;
    

        CompraResponseDTO response = restTemplate.getForObject(url, CompraResponseDTO.class);

        return response;
    }
    public void registrarConsulta(String uasg, String tipo, String processo, int quantidadeItens) {
        try {
            logger.info("Tentando registrar consulta com uasg={}, tipo={}, processo={}, quantidadeItens={}",
                    uasg, tipo, processo, quantidadeItens);
            Firestore db = FirestoreClient.getFirestore();
            logger.info("Firestore instanciado com sucesso");
            ConsultaLog log = new ConsultaLog(uasg, tipo, processo, quantidadeItens);
            logger.info("ConsultaLog criado: uasg={}, tipo={}, processo={}, quantidadeItens={}, timestamp={}",
                    log.getUasg(), log.getTipo(), log.getProcesso(), log.getQuantidadeItens(), log.getTimestamp());
            db.collection("consultas").add(log).get();
            logger.info("Consulta registrada: uasg={}, tipo={}, processo={}, quantidadeItens={}",
                    uasg, tipo, processo, quantidadeItens);
        } catch (Exception e) {
            logger.error("Erro ao salvar no Firestore: {}", e.getMessage(), e);
        }
    }
}

