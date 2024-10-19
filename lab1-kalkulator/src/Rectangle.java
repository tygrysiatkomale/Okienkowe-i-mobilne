public class Rectangle extends Figure {
    private double width, height;

    public Rectangle(double width, double height) throws IllegalArgumentException {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Invalid width or height");
        }
        this.width = width;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return width * height;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * width * height;
    }

    @Override
    public void print() {
        System.out.println("Rectangle sides: " + width + ", " + height);
        System.out.println("Area: " + calculateArea());
        System.out.println("Perimeter: " + calculatePerimeter());
    }
}