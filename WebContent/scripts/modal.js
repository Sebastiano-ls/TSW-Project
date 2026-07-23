function showModal(message, onConfirm) {
    let modal = document.getElementById('confirm-modal');
    if (!modal) return;

    let messElem = document.getElementById('confirm-message');
    let yesButton = document.getElementById('confirm-yes');
    let noButton = document.getElementById('confirm-no');

    if (messElem) messElem.innerHTML = message;

    modal.style.display = 'flex';

    yesButton.onclick = function() {
        modal.style.display = 'none';
        if (onConfirm) 
            onConfirm();
    };

    noButton.onclick = function() {
        modal.style.display = 'none';
    };
}

function closeModal() {
    let modal = document.getElementById('confirm-modal');
    if (modal) 
        modal.style.display = 'none';
}

function setupModal() {
    let existing = document.getElementById('confirm-modal');
    if (existing) 
        return;

    let modalHtml = '<div id="confirm-modal" class="modal-overlay" style="display:none">' +
        '<div class="modal-content">' +
        '<p id="confirm-message"></p>' +
        '<div class="modal-actions">' +
        '<button id="confirm-yes" class="btn btn-primary">si</button>' +
        '<button id="confirm-no" class="btn btn-outline">no</button>' +
        '</div></div></div>';

    let div = document.createElement('div');
    div.innerHTML = modalHtml;
    
    document.body.appendChild(div.firstChild);
}
