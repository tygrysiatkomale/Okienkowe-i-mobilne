public class Pyramid {
    private Figure base;
    private double height;

    public Pyramid(Figure base, double height) throws IllegalArgumentException {
        if (height <= 0) {
            throw new IllegalArgumentException("Invalid height");
        }
        this.base = base;
        this.height = height;
    }

    public double calculateSurfaceArea() {
        // Zakładamy, że obliczamy tylko podstawę i wysokość (dla uproszczenia)
        return base.calculateArea() + (base.calculatePerimeter() * height / 2);
    }

    public double calculateVolume() {
        return (1.0 / 3) * base.calculatePerimeter() * height;
    }

    public void print() {
        System.out.println("Pyramid with base:");
        base.print();
        System.out.println("Height: " + height);
        System.out.println("Surface area: " + calculateSurfaceArea());
        System.out.println("Volume: " + calculateVolume());
    }
}