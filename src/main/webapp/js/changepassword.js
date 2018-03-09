function createInputElement(type, name) {
    var i = document.createElement("input");
    i.type = type;
    i.name = name;
    return i;
}

function removeElement() {
 var elem = document.getElementById('but');
 elem.parentNode.removeChild(elem);
}

function addUserChangePasswordForm() {
    with(document) {
        var div = createElement("div");
        div.appendChild(createElement("br"));
        div.appendChild(createTextNode("New Password: "));
        div.appendChild(createInputElement("text", "password"));
        div.appendChild(createElement("br"));
        div.appendChild(createElement("br"));
        div.appendChild(createTextNode("Confirmed New Password: "));
        div.appendChild(createInputElement("text", "confirmedPassword"));
        getElementById("passwordItems").appendChild(div);
    }
    removeElement();
}