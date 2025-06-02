const setupPlaceholderHandler = () => {
    const tipoSelect = document.getElementById("tipo");
    const processoInput = document.getElementById("processo");

    if (!tipoSelect || !processoInput) {
        console.error("Erro: Um ou mais elementos para o placeholder não foram encontrados.");
        return;
    }

    const updateProcessoPlaceholder = () => {
        if (tipoSelect.value === "05") {
            processoInput.placeholder = "Digite o número do processo ex: 900412024";
        } else if (tipoSelect.value === "06") {
            processoInput.placeholder = "Digite o número da dispensa ex: 002142025";
        }
    };

    updateProcessoPlaceholder();
    tipoSelect.addEventListener("change", updateProcessoPlaceholder);

    const formDownload = document.getElementById("formDownload");
    if (formDownload) {
        formDownload.addEventListener("reset", updateProcessoPlaceholder);
    }
};

document.addEventListener("DOMContentLoaded", setupPlaceholderHandler);