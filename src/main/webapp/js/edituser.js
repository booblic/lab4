function createInputElement(type, name) {
    var i = document.createElement("input");
    i.type = type;
    i.name = name;
    return i;
}

function removeElement(name) {
 var elem = document.getElementById(name);
 elem.parentNode.removeChild(elem);
}

function addUserChangePasswordForm() {
    with(document) {
        var div = createElement("div");
        div.appendChild(createElement("br"));
        div.appendChild(createTextNode("Old Password: "));
        div.appendChild(createInputElement("text", "oldPassword"));
        div.appendChild(createElement("br"));
        div.appendChild(createElement("br"));
        div.appendChild(createTextNode("New Password: "));
        div.appendChild(createInputElement("text", "password"));
        div.appendChild(createElement("br"));
        div.appendChild(createElement("br"));
        div.appendChild(createTextNode("Confirmed New Password: "));
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