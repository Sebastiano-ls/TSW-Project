function ajaxUpdateQuantita(input) {
    let form = input;
    while (form && form.tagName !== "FORM" && form.tagName !== "form") {
        form = form.parentElement;
    }
    
    if (!form) 
        return;

    let xhr = createXMLHttpRequest(form);
    if(!xhr)
        return;

    let url = form.getAttribute("action")|| window.location.href;
    let method = (form.method || "post").toLowerCase();
    let formData = new FormData(form);
    let bodyString = new URLSearchParams(formData).toString();

    xhr.onreadystatechange = function(){
        if(this.readyState == 4){
            let alertElem = form.querySelector(".alert-error");

            if(this.status == 0){
                if (alertElem) 
                    alertElem.innerHTML = "Problemi nell'esecuzione della richiesta: nessuna risposta ricevuta nel tempo limite";
            } else if(this.status != 200){ 
                if (alertElem) 
                    alertElem.innerHTML = "Problemi nell'esecuzione della richiesta:\n" + this.statusText;
            } else {
                try {
                    let resp = JSON.parse(this.responseText);
                    if(!resp.ok){
                        if(alertElem) 
                            alertElem.textContent = resp.errore || "Errore sconosciuto";
                        return;
                    }

                    if(resp.rimosso){
                        window.location.reload();
                        return;
                    }

                    let riga = form.closest(".carrello-riga");
                    
                    if (riga && resp.subtotale !== undefined) {
                        let subtotaleElem = riga.querySelector(".carrello-subtotale");
                        if (subtotaleElem) {
                            subtotaleElem.innerHTML = resp.subtotale + " €";
                        }
                    }

                    if (resp.totale !== undefined) {
                        let totaleElem = document.querySelector(".carrello-totale-valore");
                        if (totaleElem)
                            totaleElem.innerHTML = resp.totale + " €";
                    }

                    if (alertElem) 
                        alertElem.textContent = "";
                } catch (e) {
                    if (alertElem) 
                        alertElem.textContent = "Errore nell'elaborazione della risposta dal server.";
                }
            }
        }
    };

    xhr.open(method, url, true);

    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
    xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");

    setTimeout(function(){
        if(xhr.readyState < 4){
            xhr.abort();
        }
    }, 15000);

    xhr.send(bodyString);
}

window.onload = function() {
    var quantitaInputs = document.querySelectorAll("[ajax-quantita]");

    for (let i = 0; i < quantitaInputs.length; i++) {
        quantitaInputs[i].onchange = function() { ajaxUpdateQuantita(this); };
    }
};

function createXMLHttpRequest(form){
    var request;

    try{
        request = new XMLHttpRequest();
    }catch (e){
        try{
            request = new ActiveXObject("Msxml2.XMLHTTP");
        }catch(e){
            try{
                request = new ActiveXObject("Microsoft.XMLHTTP");
            }catch(e){
                let alertElem = form.querySelector('.alert-error');
                if (alertElem) 
                    alertElem.innerHTML = "Il tuo browser non supporta questa funzione";
            }
        }
    }
    
    return request;
}