import { setupModal } from './modalHandler.js';

const setupDownloadForm = () => {
    const formDownload = document.getElementById("formDownload");
    const btnSubmit = document.getElementById("btnSubmit");
    const tipoSelect = document.getElementById("tipo");
    const processoInput = document.getElementById("processo");

    if (!formDownload || !btnSubmit || !tipoSelect || !processoInput) {
        console.error("Erro: Um ou mais elementos do formulário de download não foram encontrados.");
        return;
    }

    const modal = setupModal();
    if (!modal) return;

    const textoOriginalBotaoDownload = btnSubmit.textContent || "Gerar Planilha Excel para SILOMS";

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

            modal.showModal("Sucesso!", "Sua planilha foi gerada e o download iniciado. Verifique sua pasta de downloads.");
            formDownload.reset();

        } catch (error) {
            console.error("Erro na operação de download:", error);
            const errorMessage = error.message || "Erro desconhecido ao processar a solicitação.";
            modal.showModal("Ocorreu um Erro", errorMessage);
        } finally {
            btnSubmit.disabled = false;
            btnSubmit.textContent = textoOriginalBotaoDownload;
        }
    });
};

document.addEventListener("DOMContentLoaded", setupDownloadForm);