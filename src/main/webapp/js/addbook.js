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
    i.innerHTML="Book:";
    return i;
}

function addBookFormItem() {
    with(document) {
        var div = createElement("div");
        div.appendChild(createElementH4());
        div.appendChild(createElement("br"));
        div.appendChild(createElementLabel("Name"));
        div.appendChild(createInputElement("text", "bookName"));
        div.appendChild(createElementLabel("ISBN"));
        div.appendChild(createInputElement("text", "isbn"));
        div.appendChild(createElementLabel("Year"));
        div.appendChild(createInputElement("text", "year"));
        getElementById("bookItems").appendChild(div);
    }
}