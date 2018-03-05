function createInputElement(type, name) {
    var i = document.createElement("input");
    i.type = type;
    i.name = name;
    return i;
}

function addAuthorFormItem() {
    with(document) {
        var div = createElement("div");
        div.appendChild(createElement("br"));
        div.appendChild(createTextNode(" First Name "));
        div.appendChild(createInputElement("text", "firstName"));
        div.appendChild(createTextNode(" Last Name "));
        div.appendChild(createInputElement("text", "lastName"));
        div.appendChild(createTextNode(" Middle Name "));
        div.appendChild(createInputElement("text", "middleName"));
        getElementById("authorItems").appendChild(div);
    }
}

function addPublisherFormItem() {
    with(document) {
        var div = createElement("div");
        div.appendChild(createElement("br"));
        div.appendChild(createTextNode("Publisher Name "));
        div.appendChild(createInputElement("text", "publisherName"));
        getElementById("publisherItems").appendChild(div);
    }
}

function addGenreFormItem() {
    with(document) {
        var div = createElement("div");
        div.appendChild(createElement("br"));
        div.appendChild(createTextNode("Genre Name "));
        div.appendChild(createInputElement("text", "genreName"));
        getElementById("genreItems").appendChild(div);
    }
}