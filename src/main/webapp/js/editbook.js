function createInputElement(type, name) {
    var i = document.createElement("input");
    i.type = type;
    i.name = name;
    i.classList.add("form-control");
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


function addAuthorFormItem() {
    with(document) {
        var divRow = createElementDivClassRow();


        var divCol1 = createElementDivClassCol();
        var p1 = createElement("p");
        p1.appendChild(createTextNode("First Name"));
        divCol1.appendChild(p1);

        var p2 = createElement("p");
        p2.appendChild(createInputElement("text", "authorsFirstNames"));
        divCol1.appendChild(p2);

        var divCol2 = createElementDivClassCol();
        var p3 = createElement("p");
        p3.appendChild(createTextNode("Last Name"));
        divCol2.appendChild(p3);

        var p4 = createElement("p");
        p4.appendChild(createInputElement("text", "authorsLastNames"));
        divCol2.appendChild(p4);


        var divCol3 = createElementDivClassCol();
        var p5 = createElement("p");
        p5.appendChild(createTextNode("Middle Name"));
        divCol3.appendChild(p5);

        var p6 = createElement("p");
        p6.appendChild(createInputElement("text", "authorsMiddleNames"));
        divCol3.appendChild(p6);


        divRow.appendChild(divCol1);
        divRow.appendChild(divCol2);
        divRow.appendChild(divCol3);


        getElementById("authorItems").appendChild(divRow);
    }
}

function addPublisherFormItem() {
    with(document) {
        var divRow = createElementDivClassRow();
        var divCol = createElementDivClassCol();
        var p = createElement("p");
        p.appendChild(createInputElement("text", "publishersNames"));
        divCol.appendChild(p);
        divRow.appendChild(divCol);
        getElementById("publisherItems").appendChild(divRow);
    }
}

function addGenreFormItem() {
    with(document) {
        var divRow = createElementDivClassRow();
        var divCol = createElementDivClassCol();
        var p = createElement("p");
        p.appendChild(createInputElement("text", "genresNames"));
        divCol.appendChild(p);
        divRow.appendChild(divCol);
        getElementById("genreItems").appendChild(divRow);
    }
}