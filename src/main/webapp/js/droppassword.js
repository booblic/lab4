function createCheckbox(type, name, value) {
    var element = document.createElement("input");
    element.type = type;
    element.name = name;
    element.value = value;
    element.classList.add("form-control");
    return element;
}

function createElementDivClassRow() {
    var i = document.createElement("div");
    i.classList.add("row");
    return i;
}

function createElementDivClassCol() {
    var i = document.createElement("div");
    i.classList.add("col-md-3");
    return i;
}

function createElementH4() {
    var i = document.createElement('h4');
    i.setAttribute('class', 'h4 page-header');
    i.innerHTML="Change password:";
    return i;
}

function createElementLabel(val, f) {
    var i = document.createElement('label');
    i.setAttribute('for', f);
    i.innerHTML=val;
    return i;
}

function removeElement(name) {
    var element = document.getElementById(name);
    element.parentNode.removeChild(element);
}

function addDropPasswordCheckbox() {
    with(document) {
        var divRol = createElementDivClassRow();

        var divCol = createElementDivClassCol();

        var label = createElementLabel("Drop password", "dropPassword");

        var p1 = createElement("p");

        p1.appendChild(label);

        var p2 = createElement("p");

        var checkbox = createCheckbox("checkbox", "dropPassword", "yes");

        p2.appendChild(checkbox);

        divCol.appendChild(p1);
        divCol.appendChild(p2);
        divRol.appendChild(divCol);

        getElementById("passwordItems").appendChild(divRol);
    }
    removeElement("but");
 }