 function createInputElement(what, name, id, rows, cols, style) {
                var i = document.createElement(what);
                i.name = name;
                i.id = id;
                i.rows = rows;
                i.cols = cols;
                i.style = style;
                return i;
            }


            function addBookFormReview() {
                with(document) {
                    var div = createElement("div");
                    div.appendChild(createTextNode("Review"));
                    div.appendChild(createElement("br"));
                    div.appendChild(createInputElement("textarea", "bookReview", "bookReview", "5", "200", "width: 400px"));
                    getElementById("bookReview").appendChild(div);
                }
                }