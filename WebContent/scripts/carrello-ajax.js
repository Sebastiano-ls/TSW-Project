function ajaxUpdateQuantita(form) {
    let xhr = createXMLHttpRequest();
    if (!xhr)
        return;

    let url = form.action || window.location.href;
    let method = (form.method || "POST").toUpperCase();
    let formData = new FormData(form);

    xhr.onreadystatechange = function(){
        if(this.readyState == 4){
            let alertElem = document.querySelector(".alert-error");
            if(this.status == 0){
                if (alertElem) alertElem.textContent = "Problemi nell'esecuzione della richiesta: nessuna risposta ricevuta nel tempo limite";
            }else if(this.status != 200){
                if (alertElem) alertElem.textContent = "Problemi nell'esecuzione della richiesta:\n" + this.statusText;
            }else{
                if (alertElem) alertElem.textContent = "";
                try {
                    let resp = JSON.parse(this.responseText);
                    if (resp.ok && resp.totale !== undefined) {
                        let totaleElem = document.querySelector(".carrello-totale-valore");
                        if (totaleElem) totaleElem.textContent = "€ " + resp.totale;
                    }
                } catch(e) { /* JSON parse error */ }
            }
        }
    };

    xhr.open(method, url, true);
    xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");

    setTimeout(function(){
        if(xhr.readyState < 4){
            xhr.abort();
        }
    }, 15000);

    xhr.send(formData);
}

window.addEventListener('load', function() {
    var cartForms = document.querySelectorAll("[ajax-quantita]");
    for (let i = 0; i < cartForms.length; i++) {
        var inputs = cartForms[i].querySelectorAll("input[type='number']");
        for (let j = 0; j < inputs.length; j++) {
            inputs[j].onchange = function() { ajaxUpdateQuantita(cartForms[i]); };
        }
    }
});

function createXMLHttpRequest(){
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
                let alertElem = document.querySelector(".alert-error");
                if (alertElem) alertElem.textContent = "Il tuo browser non supporta questa funzione";
            }
        }
    }
    return request;
}
