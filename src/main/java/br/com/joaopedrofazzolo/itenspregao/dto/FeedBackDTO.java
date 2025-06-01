package br.com.joaopedrofazzolo.itenspregao.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class FeedBackDTO {
    @NotNull(message = "Avaliação é obrigatória")
    @Min(value = 1, message = "Avaliação deve ser no mínimo 1")
    @Max(value = 5, message = "Avaliação deve ser no máximo 5")
    private Integer avaliacao;

    @NotBlank(message = "Feedback não pode ser vazio")
    private String feedback;
}