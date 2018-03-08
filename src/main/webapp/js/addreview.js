 function createTextAreaElement(name, id, rows, cols, style) {
     var i = document.createElement("textarea");
     i.name = name;
     i.id = id;
     i.rows = rows;
     i.cols = cols;
     i.style = style;
     return i;
 }

 function createNumberElement(type, name, size, min, max) {
     var i = document.createElement("input");
     i.type = type;
     i.name = name;
     i.size = size;
     i.min = min;
     i.max = max;
     return i;
 }


 function addBookFormReview() {
     with(document) {
         var div = createElement("div");
         div.appendChild(createTextNode("Review:"));
         div.appendChild(createElement("br"));
         div.appendChild(createTextAreaElement("textReview", "textReview", "5", "200", "width: 400px"));
         div.appendChild(createElement("br"));
	    div.appendChild(createElement("br"));
	    div.appendChild(createTextNode("Rating:"));
         div.appendChild(createNumberElement("number", "rating", "1", "1", "5"));
	    div.appendChild(createElement("br"));
	    div.appendChild(createElement("br"));
	    div.appendChild(createElement("br"));
         getElementById("bookReview").appendChild(div);
     }
 }