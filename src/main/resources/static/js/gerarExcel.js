document.addEventListener("DOMContentLoaded", () => {
    const formDownload = document.getElementById("formDownload");
    const btnSubmit = document.getElementById("btnSubmit");
    const formFeedback = document.getElementById("formFeedback");
    const btnFeedbackSubmit = document.getElementById("btnFeedbackSubmit");

    const feedbackModal = document.getElementById("feedbackModal");
    const modalTitle = document.getElementById("modal-title");
    const modalMessage = document.getElementById("modal-message");
    const closeButtonModal = document.querySelector(".close-button");
    const modalOkButton = document.getElementById("modal-ok-button");

    if (!formDownload || !btnSubmit || !formFeedback || !btnFeedbackSubmit || !feedbackModal || !modalTitle || !modalMessage || !closeButtonModal || !modalOkButton) {
        console.error("Erro: Um ou mais elementos do DOM não foram encontrados.");
        return;
    }

    const textoOriginalBotaoDownload = btnSubmit.textContent || "Gerar Planilha Excel para SILOMS";
    const textoOriginalBotaoFeedback = btnFeedbackSubmit.textContent || "Enviar Feedback";

    const showModal = (title, message) => {
        modalTitle.textContent = title;
        modalMessage.textContent = message;
        feedbackModal.style.display = "block";
    };

    const hideModal = () => {
        feedbackModal.style.display = "none";
    };

    closeButtonModal.addEventListener("click", hideModal);
    modalOkButton.addEventListener("click", hideModal);

    window.addEventListener("click", (event) => {
        if (event.target === feedbackModal) {
            hideModal();
        }
    });

    formDownload.addEventListener("submit", async (event) => {
        event.preventDefault();

        btnSubmit.disabled = true;
        btnSubmit.textContent = "Gerando planilha, aguarde...";

        const params = new URLSearchParams(new FormData(formDownload));

        try {
            const response = await fetch(`/gerarExcel?${params.toString()}`, {
                method: "GET"
            });

            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(errorText.length > 0 ? errorText : "Ocorreu um erro ao gerar a planilha. Por favor, confirme os dados inseridos e tente novamente.");
            }

            const blob = await response.blob();
            const contentDisposition = response.headers.get("Content-Disposition");

            let filename = "relatorio.xlsx";
            if (contentDisposition && contentDisposition.includes("filename=")) {
                filename = contentDisposition
                    .split("filename=")[1]
                    .replace(/['"]/g, "")
                    .trim();
            } else {
                filename = `relatorio_${new Date().toISOString().split('T')[0]}.xlsx`;
            }

            const url = window.URL.createObjectURL(blob);
            const link = document.createElement("a");
            link.href = url;
            link.download = filename;

            document.body.appendChild(link);
            link.click();
            link.remove();
            window.URL.revokeObjectURL(url);

            showModal("Sucesso!", "Sua planilha foi gerada e o download iniciado. Verifique sua pasta de downloads.");
            formDownload.reset();

        } catch (error) {
            console.error("Erro na operação de download:", error);
            const errorMessage = error.message || "Erro desconhecido ao processar a solicitação.";
            showModal("Ocorreu um Erro", errorMessage);
        } finally {
            btnSubmit.disabled = false;
            btnSubmit.textContent = textoOriginalBotaoDownload;
        }
    });

    formFeedback.addEventListener("submit", async (event) => {
        event.preventDefault();

        btnFeedbackSubmit.disabled = true;
        btnFeedbackSubmit.textContent = "Enviando feedback, aguarde...";

        const avaliacaoInput = document.querySelector('input[name="avaliacao"]:checked');
        const feedbackText = document.getElementById("feedback").value;
        const emailValue = document.getElementById("email").value;

        if (!avaliacaoInput) {
            showModal("Erro", "Por favor, selecione uma avaliação de 1 a 5.");
            btnFeedbackSubmit.disabled = false;
            btnFeedbackSubmit.textContent = textoOriginalBotaoFeedback;
            return;
        }

        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(emailValue)) {
            showModal("Erro", "Por favor, insira um e-mail válido.");
            btnFeedbackSubmit.disabled = false;
            btnFeedbackSubmit.textContent = textoOriginalBotaoFeedback;
            return;
        }

        const feedbackData = {
            avaliacao: parseInt(avaliacaoInput.value),
            feedback: feedbackText,
            email: emailValue
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
            showModal("Sucesso!", responseText || "Feedback enviado com sucesso!");
            formFeedback.reset();

        } catch (error) {
            console.error("Erro na operação de feedback:", error);
            const errorMessage = error.message || "Erro desconhecido ao enviar o feedback.";
            showModal("Ocorreu um Erro", errorMessage);
        } finally {
            btnFeedbackSubmit.disabled = false;
            btnFeedbackSubmit.textContent = textoOriginalBotaoFeedback;
        }
    });
});
