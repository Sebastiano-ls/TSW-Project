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

function setupModal() {
    let existing = document.getElementById('modal');
    if (existing) return;

    let div = document.createElement('div');
    div.innerHTML =
        '<div class="modal-overlay" id="modal" style="display:none">' +
            '<div class="modal-box">' +
                '<p id="modal-msg"></p>' +
                '<div class="modal-azioni">' +
                    '<button id="modal-confirm" class="btn btn-danger">Conferma</button>' +
                    '<button class="btn btn-outline" onclick="closeModal()">Annulla</button>' +
                '</div>' +
            '</div>' +
        '</div>';

    document.body.appendChild(div.firstChild);
}
