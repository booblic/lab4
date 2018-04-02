 function createTextArea(name, id, rows) {
     var textarea = document.createElement("textarea");
     textarea.name = name;
     textarea.id = id;
     textarea.rows = rows;
     textarea.classList.add("form-control");
     return textarea;
 }

 function createNumber(type, name, min, max) {
     var i = document.createElement("input");
     i.type = type;
     i.name = name;
     i.min = min;
     i.max = max;
     i.classList.add("form-control");
     return i;
 }

 function createSaveButton() {
   var button = document.createElement('button');
   button.type = 'submit';
   button.className='btn btn-success';
   button.innerHTML="Save review";
   return button;
  }

 function createDivClassCol() {
      var i = document.createElement("div");
      i.classList.add("col-md-1");
      return i;
 }

function createLabel(val, f) {
     var i = document.createElement('label');
     i.setAttribute('for', f);
     i.innerHTML=val;
     return i;
}

 function removeElement() {
  var elem = document.getElementById('but');
  elem.parentNode.removeChild(elem);
 }


 function addBookFormReview() {
     with(document) {

         var div = createElement("div");

         var p1 = createElement("p");
         p1.appendChild(createLabel("Text review", "textReview"));

         var p2 = createElement("p");
         p2.appendChild(createTextArea("textReview", "textReview", "4"));

         var p3 = createElement("p");
         p3.appendChild(createLabel("Rating", "rating"));

         var p4 = createElement("p");
         p4.appendChild(createNumber("number", "rating", "1", "5"));

         var divCol = createDivClassCol();
         divCol.appendChild(p4);

         var p5 = createElement("p");
         p5.appendChild(createSaveButton());

         var div = getElementById("bookReview");
         div.appendChild(p1);
         div.appendChild(p2);
         div.appendChild(p3);
         div.appendChild(p4);
         div.appendChild(p5);
     }
     removeElement();
 }