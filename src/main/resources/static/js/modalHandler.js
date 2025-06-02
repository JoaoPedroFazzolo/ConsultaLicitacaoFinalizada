const setupModal = () => {
    const feedbackModal = document.getElementById("feedbackModal");
    const modalTitle = document.getElementById("modal-title");
    const modalMessage = document.getElementById("modal-message");
    const closeButtonModal = document.querySelector(".close-button");
    const modalOkButton = document.getElementById("modal-ok-button");

    if (!feedbackModal || !modalTitle || !modalMessage || !closeButtonModal || !modalOkButton) {
        console.error("Erro: Um ou mais elementos do modal não foram encontrados.");
        return null;
    }

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

    return { showModal, hideModal };
};

export { setupModal };