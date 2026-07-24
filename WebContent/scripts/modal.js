function showModal(button, msg) {
    let modal = document.getElementById('modal');
    if (!modal) return;

    let msgElem = document.getElementById('modal-msg');
    let confirmBtn = document.getElementById('modal-confirm');

    if (msgElem) msgElem.textContent = msg;
    modal.style.display = 'flex';

    let form = button.form || button.closest('form');

    confirmBtn.onclick = function() {
        modal.style.display = 'none';
        if (form && form.submit) form.submit();
    };
}

function closeModal() {
    let modal = document.getElementById('modal');
    if (modal) modal.style.display = 'none';
}
