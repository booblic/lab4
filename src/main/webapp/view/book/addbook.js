function createInputElement(type, name) {
    var i = document.createElement("input");
    i.type = type;
    i.name = name;
    return i;
}

function addBookFormItem() {
    with(document) {
        var div = createElement("div");
        div.appendChild(createTextNode("Book"));
        div.appendChild(createElement("br"));
        div.appendChild(createTextNode("Name"));
        div.appendChild(createInputElement("text", "bookName"));
        div.appendChild(createTextNode("ISBN"));
        div.appendChild(createInputElement("text", "isbn"));
        div.appendChild(createTextNode("Year"));
        div.appendChild(createInputElement("text", "year"));
        getElementById("bookItems").appendChild(div);
    }
}