function showModal(button, message) {
    var modal = document.getElementById('modal');
    var msgEl = document.getElementById('modal-msg');
    var confirmBtn = document.getElementById('modal-confirm');
    if (!modal || !msgEl || !confirmBtn) return;

    msgEl.innerHTML = message;
    modal.style.display = 'flex';

    var form = button;
    while (form && form.tagName !== "FORM") { form = form.parentElement; }

    confirmBtn.onclick = function() {
        modal.style.display = 'none';
        if (form) form.submit();
    };
}

function closeModal() {
    var modal = document.getElementById('modal');
    if (modal) modal.style.display = 'none';
}
