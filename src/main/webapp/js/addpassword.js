function createInputElement(type, name) {
    var i = document.createElement("input");
    i.type = type;
    i.name = name;
    i.classList.add("form-control");
    return i;
}

function createElementLabel(val, f) {
    var i = document.createElement('label');
    i.setAttribute('for', f);
    i.innerHTML=val;
    return i;
}

function createElementH4() {
    var i = document.createElement('h4');
    i.setAttribute('class', 'h4 page-header');
    i.innerHTML="Change password:";
    return i;
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

function removeElement(name) {
    var elem = document.getElementById(name);
    elem.parentNode.removeChild(elem);
}

function addUserChangePasswordForm() {
    with(document) {

        var divRow1 = createElementDivClassRow();

        var divCol1 = createElementDivClassCol();

        divRow1.appendChild(createElementH4());

        var p1 = createElement("p");
        p1.appendChild(createElementLabel("Old Password", "oldPassword"));

        var p2 = createElement("p")
        p2.appendChild(createInputElement("password", "oldPassword"));

        divCol1.appendChild(p1);
        divCol1.appendChild(p2);
        divRow1.appendChild(divCol1);


        var divRow2 = createElementDivClassRow();

        var divCol2 = createElementDivClassCol();

        var divCol3 = createElementDivClassCol();

        var p3 = createElement("p");
        p3.appendChild(createElementLabel("New Password", "newPassword"));

        var p4 = createElement("p");
        p4.appendChild(createInputElement("password", "password"));

        divCol2.appendChild(p3);
        divCol2.appendChild(p4);

        var divCol3 = createElementDivClassCol();

        var p5 = createElement("p");
        p5.appendChild(createElementLabel("Confirmed New Password", "newPassword"));

        var p6 = createElement("p");
        p6.appendChild(createInputElement("password", "confirmedPassword"));

        divCol3.appendChild(p5);
        divCol3.appendChild(p6);

        divRow2.appendChild(divCol2);
        divRow2.appendChild(divCol3);

        var passwordItems = getElementById("passwordItems");

        passwordItems.appendChild(divRow1);
        passwordItems.appendChild(divRow2);
    }
    removeElement("but");
}