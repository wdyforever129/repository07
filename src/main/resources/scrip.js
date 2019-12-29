function func1(name) {
    print('Hi there from Javascript, ' + name);
    return "hello "+name;
};

var func2 = function (object) {
    print("JS Class Definition: " + Object.prototype.toString.call(object));
};