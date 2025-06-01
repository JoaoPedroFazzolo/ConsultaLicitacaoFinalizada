package br.com.joaopedrofazzolo.itenspregao.model;

import com.google.cloud.firestore.annotation.PropertyName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class FeedBackModel {
    @PropertyName("avaliacao")
    private int avaliacao;

    @PropertyName("feedback")
    private String feedback;

    @PropertyName("timestamp")
    private String timestamp;
}