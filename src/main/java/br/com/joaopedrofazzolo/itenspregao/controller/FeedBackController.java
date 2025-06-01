package br.com.joaopedrofazzolo.itenspregao.controller;

import br.com.joaopedrofazzolo.itenspregao.dto.FeedBackDTO;
import br.com.joaopedrofazzolo.itenspregao.service.FeedBackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedback")
@Tag(name = "Feedback", description = "Gerencia feedbacks de usuários")
public class FeedBackController {

    private static final Logger logger = LoggerFactory.getLogger(FeedBackController.class);

    @Autowired
    private FeedBackService feedBackService;

    @Operation(summary = "Cria um novo feedback",
            description = "Registra um feedback com avaliação e comentário. O timestamp é gerado automaticamente pelo servidor.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Feedback salvo com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
                    @ApiResponse(responseCode = "500", description = "Erro interno ao salvar o feedback")
            })
    @PostMapping
    public ResponseEntity<String> createFeedback(@Valid @RequestBody FeedBackDTO feedbackDTO) {
        logger.info("Recebida solicitação para criar feedback: avaliacao={}, feedback={}",
                feedbackDTO.getAvaliacao(), feedbackDTO.getFeedback());

        try {
            String id = feedBackService.saveFeedback(feedbackDTO);
            logger.info("Feedback salvo com sucesso. ID: {}", id);
            return ResponseEntity.ok("Feedback enviado, muito obrigado");
        } catch (IllegalArgumentException e) {
            logger.error("Erro de validação ao salvar feedback: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro de validação: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Erro ao salvar feedback: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar feedback: " + e.getMessage());
        }
    }
}