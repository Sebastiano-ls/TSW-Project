function ajaxUpdateQty(input) {
    var form = input;
    while (form && form.tagName !== "FORM") { form = form.parentElement; }
    if (form) form.submit();
}

document.addEventListener('DOMContentLoaded', function() {
    var qtyInputs = document.querySelectorAll("[data-ajax-qty]");
    for (var i = 0; i < qtyInputs.length; i++) {
        qtyInputs[i].onchange = function() { ajaxUpdateQty(this); };
    }
});
