function MyClass(Id, Name) {
    var attr = this
    this.id = Id;
    this.name = Name;
   
    this.showId = function () {
        console.log("El ID es " + attr.id);
    }
}
MyClass.prototype.cont = 0
MyClass.prototype.showId = function () {
    console.log("El ID es " + this.id);
}
MyClass.prototype.getName = function (nom) {
    this.name = nom.toUpperCase();
}

class Persona {
    constructor(id, name) {
        this.id = id
        this.name = name
    }
    showId() {
        console.log("El ID es " + this.id);
    }
    getName(nom) {
        this.name = nom.toUpperCase();
    }
}