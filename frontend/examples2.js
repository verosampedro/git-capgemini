var o1 = new MyClass("99", "Testing object");
var o2 = new MyClass("6", "Another test");
o1 = new Persona("99", "Testing object");
o2 = new Persona("6", "Another test");
console.log(o1.showId == o2.showId)
o1.showId()
o2.showId()
var id = 66666
o1.showId = () => console.log("El ID es " + this.id);
MyClass.prototype.showId = () => console.log("Hello world!")
o1.showId()
o2.showId.call(o1)
console.log(o1.showId == o2.showId)
console.log(++o1.cont, o2.cont++)
console.log(typeof MyClass, typeof Persona)