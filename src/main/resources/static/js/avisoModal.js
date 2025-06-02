document.addEventListener("DOMContentLoaded", () => {
    const modal = document.getElementById("modalAviso");
    const closeButton = document.getElementById("modal-ok-button-aviso");

    if (modal && closeButton) {
        modal.style.display = "block";

        closeButton.addEventListener("click", () => {
            modal.style.display = "none";
        });

        window.addEventListener("click", (event) => {
            if (event.target === modal) {
                modal.style.display = "none";
            }
        });
    }
});
