package br.com.joaopedrofazzolo.itenspregao.service;

import br.com.joaopedrofazzolo.itenspregao.dto.FeedBackDTO;
import br.com.joaopedrofazzolo.itenspregao.model.FeedBackModel;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class FeedBackService {
    private static final Logger logger = LoggerFactory.getLogger(FeedBackService.class);
    private final Firestore db;
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public FeedBackService() {
        this.db = FirestoreClient.getFirestore();
    }

    public String saveFeedback(FeedBackDTO feedbackDTO) {
        try {
            logger.info("Convertendo FeedBackDTO para FeedBackModel: avaliacao={}, feedback={}, email={}",
                    feedbackDTO.getAvaliacao(), feedbackDTO.getFeedback(), feedbackDTO.getEmail());

            FeedBackModel feedbackModel = new FeedBackModel();
            feedbackModel.setAvaliacao(feedbackDTO.getAvaliacao());
            feedbackModel.setFeedback(feedbackDTO.getFeedback());
            feedbackModel.setEmail(feedbackDTO.getEmail());
            feedbackModel.setTimestamp(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).format(formatter));

            String id = db.collection("feedbacks")
                    .add(feedbackModel)
                    .get()
                    .getId();
            logger.info("Feedback salvo no Firestore com ID: {}, timestamp: {}", id, feedbackModel.getTimestamp());
            return id;
        } catch (Exception e) {
            logger.error("Erro ao salvar feedback no Firestore: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to save feedback: " + e.getMessage(), e);
        }
    }
}