import { setupModal } from './modalHandler.js';

const setupFeedbackForm = () => {
    const formFeedback = document.getElementById("formFeedback");
    const btnFeedbackSubmit = document.getElementById("btnFeedbackSubmit");

    if (!formFeedback || !btnFeedbackSubmit) {
        console.error("Erro: Um ou mais elementos do formulário de feedback não foram encontrados.");
        return;
    }

    const modal = setupModal();
    if (!modal) return;

    const textoOriginalBotaoFeedback = btnFeedbackSubmit.textContent || "Enviar Feedback";

    formFeedback.addEventListener("submit", async (event) => {
        event.preventDefault();

        btnFeedbackSubmit.disabled = true;
        btnFeedbackSubmit.textContent = "Enviando feedback, aguarde...";

        const avaliacaoInput = document.querySelector('input[name="avaliacao"]:checked');
        if (!avaliacaoInput) {
            modal.showModal("Erro", "Por favor, selecione uma avaliação de 1 a 5.");
            btnFeedbackSubmit.disabled = false;
            btnFeedbackSubmit.textContent = textoOriginalBotaoFeedback;
            return;
        }

        const feedbackData = {
            avaliacao: parseInt(avaliacaoInput.value),
            feedback: document.getElementById("feedback").value
        };

        try {
            const response = await fetch("/api/feedback", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(feedbackData)
            });

            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(errorText.length > 0 ? errorText : "Ocorreu um erro ao enviar o feedback. Por favor, tente novamente.");
            }

            const responseText = await response.text();
            modal.showModal("Sucesso!", responseText || "Feedback enviado com sucesso!");

            formFeedback.reset();

        } catch (error) {
            console.error("Erro na operação de feedback:", error);
            const errorMessage = error.message || "Erro desconhecido ao enviar o feedback.";
            modal.showModal("Ocorreu um Erro", errorMessage);
        } finally {
            btnFeedbackSubmit.disabled = false;
            btnFeedbackSubmit.textContent = textoOriginalBotaoFeedback;
        }
    });
};

document.addEventListener("DOMContentLoaded", setupFeedbackForm);