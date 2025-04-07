document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("formDownload");
    const button = document.getElementById("btnSubmit");
    const mensagem = document.getElementById("mensagem");

    form.addEventListener("submit", async function (e) {
        e.preventDefault();

        button.disabled = true;
        button.textContent = "Gerando..."; // <- troca o texto
        mensagem.style.display = "block";

        const params = new URLSearchParams(new FormData(form));

        try {
            const response = await fetch(`/gerarExcel?${params.toString()}`, {
                method: "GET"
            });

            if (!response.ok) throw new Error("Erro ao gerar planilha");

            const blob = await response.blob();

            const contentDisposition = response.headers.get("Content-Disposition");
            let filename
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
        } catch (err) {
            alert("Erro ao gerar a planilha.");
        } finally {
            button.disabled = false;
            button.textContent = "Gerar Excel"; // <- volta ao texto original
            mensagem.style.display = "none";
        }
    });
});
