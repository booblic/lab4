function createInputElement(type, name) {
    var i = document.createElement("input");
    i.type = type;
    i.name = name;
    i.classList.add("form-control");
    return i;
}

function createElementLabel(val) {
    var i = document.createElement('label');
    i.setAttribute('for', val);
    i.innerHTML=val;
    return i;
}

function createElementH4() {
    var i = document.createElement('h4');
    i.setAttribute('class', 'h4 page-header');
    i.innerHTML="Change password:";
    return i;
}

function removeElement(name) {
 var elem = document.getElementById(name);
 elem.parentNode.removeChild(elem);
}

function addUserChangePasswordForm() {
    with(document) {
        var div = createElement("div");
        div.appendChild(createElementH4());
        div.appendChild(createElementLabel("Old password"));
        div.appendChild(createInputElement("text", "oldPassword"));
        div.appendChild(createElement("br"));
        div.appendChild(createElementLabel("New password"));
        div.appendChild(createInputElement("text", "password"));
        div.appendChild(createElement("br"));
        div.appendChild(createElementLabel("Confirmed password"));
        div.appendChild(createInputElement("text", "confirmedPassword"));
        getElementById("passwordItems").appendChild(div);
    }
    removeElement("but");
}

function addRoleForm() {
    with(document) {
        var div = createElement("div");
        div.appendChild(createElement("br"));
        div.appendChild(createTextNode("Role: "));
        div.appendChild(createInputElement("text", "roles"));
        getElementById("roleItems").appendChild(div);
    }
}