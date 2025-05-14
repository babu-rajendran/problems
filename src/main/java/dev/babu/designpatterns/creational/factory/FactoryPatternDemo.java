package dev.babu.designpatterns.creational.factory;

interface Shape {
    void draw();
}

class Circle implements Shape {
    public void draw() {
        System.out.println("Drawing a Circle.");
    }
}

class Square implements Shape {
    public void draw() {
        System.out.println("Drawing a Square.");
    }
}

class Triangle implements Shape {
    public void draw() {
        System.out.println("Drawing a Triangle.");
    }
}

class ShapeFactory {
    public Shape getShape(String shapeType) {
        if (shapeType == null) return null;

        switch (shapeType.toLowerCase()) {
            case "circle": return new Circle();
            case "square": return new Square();
            case "triangle": return new Triangle();
            default: return null;
        }
    }
}

public class FactoryPatternDemo {

    public static void main(String[] args) {
        ShapeFactory factory = new ShapeFactory();

        Shape shape1 = factory.getShape("circle");
        shape1.draw();

        Shape shape2 = factory.getShape("square");
        shape2.draw();

        Shape shape3 = factory.getShape("triangle");
        shape3.draw();

        Shape shape4 = factory.getShape("hexagon");
        if (shape4 == null) {
            System.out.println("Unknown shape requested.");
        }
    }

}
