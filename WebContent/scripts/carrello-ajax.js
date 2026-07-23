function ajaxUpdateQuantita(input) {
    let form = input;
    while (form && form.tagName !== "FORM") {
        form = form.parentElement;
    }
    
    if (!form) 
        return;

    let xhr = createXMLHttpRequest();
    if(!xhr)
        return;

    let url = form.action || window.location.href;
    let method = (form.method || "POST").toUpperCase();
    let formData = new FormData(form);

    xhr.onreadystatechange = function(){
        if(this.readyState == 4){
            if(this.status == 0){
                let alertElem = document.getElementById("alert-error");
                if (alertElem) alertElem.innerHTML = "Problemi nell'esecuzione della richiesta: nessuna risposta ricevuta nel tempo limite";
            }else if(this.status != 200){ 
                let alertElem = document.getElementById("alert-error");
                if (alertElem) alertElem.innerHTML = "Problemi nell'esecuzione della richiesta:\n" + this.statusText;
            }else{
                let alertElem = document.getElementById("alert-error");
                if (alertElem) alertElem.innerHTML = "";
            }
        }
    };

    xhr.open(method, url, true);

    setTimeout(function(){
        if(xhr.readyState < 4){
            xhr.abort();
        }
    }, 15000);

    xhr.send(formData);
}

window.onload = function() {
    var quantitaInputs = document.querySelectorAll("[ajax-quantita]");

    for (let i = 0; i < quantitaInputs.length; i++) {
        quantitaInputs[i].onchange = function() { ajaxUpdateQuantita(this); };
    }
};

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
                let alertElem = document.getElementById("alert-error");
                if (alertElem) alertElem.innerHTML = "Il tuo browser non supporta questa funzione";
            }
        }
    }

    return request;
}
