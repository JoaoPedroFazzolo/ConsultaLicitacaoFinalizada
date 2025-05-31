document.addEventListener("DOMContentLoaded", () => {
    const formDownload = document.getElementById("formDownload");
    const btnSubmit = document.getElementById("btnSubmit");

    const feedbackModal = document.getElementById("feedbackModal");
    const modalTitle = document.getElementById("modal-title");
    const modalMessage = document.getElementById("modal-message");
    const closeButtonModal = document.querySelector(".close-button");
    const modalOkButton = document.getElementById("modal-ok-button");

    const textoOriginalBotao = btnSubmit ? btnSubmit.textContent : "Gerar Excel";

    // --- Funções Auxiliares do Modal ---
    const showModal = (title, message) => {
        if (!feedbackModal || !modalTitle || !modalMessage) {
            console.error("Erro: Elementos do modal não encontrados no DOM.");
            return;
        }
        modalTitle.textContent = title;
        modalMessage.textContent = message;
        feedbackModal.style.display = "block";
    };

    const hideModal = () => {
        if (!feedbackModal) return;
        feedbackModal.style.display = "none";
    };

    if (feedbackModal) {
        closeButtonModal.addEventListener("click", hideModal);
        modalOkButton.addEventListener("click", hideModal);

        window.addEventListener("click", (event) => {
            if (event.target === feedbackModal) {
                hideModal();
            }
        });
    }

    formDownload.addEventListener("submit", async (event) => {
        event.preventDefault();

        if (btnSubmit) {
            btnSubmit.disabled = true;
            btnSubmit.textContent = "Gerando planilha, aguarde...";
        }

        const params = new URLSearchParams(new FormData(formDownload));

        try {
            const response = await fetch(`/gerarExcel?${params.toString()}`, {
                method: "GET"
            });

            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(errorText || "Ocorreu um erro ao gerar a planilha. Por favor, confirme os dados inseridos e tente novamente.");
            }

            const blob = await response.blob();
            const contentDisposition = response.headers.get("Content-Disposition");

            let filename = "relatorio.xlsx";
            if (contentDisposition && contentDisposition.includes("filename=")) {
                filename = contentDisposition
                    .split("filename=")[1]
                    .replace(/['"]/g, "")
                    .trim();
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

        } catch (error) {
            console.error("Erro na operação:", error);
            showModal("Ocorreu um Erro", error.message);
        } finally {
            if (btnSubmit) {
                btnSubmit.disabled = false;
                btnSubmit.textContent = textoOriginalBotao;
            }
        }
    });
});