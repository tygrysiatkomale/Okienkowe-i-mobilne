import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Choose a figure (1: Triangle, 2: Rectangle, 3: Circle, 4: Pyramid, 0: Exit): ");
            int choice = scanner.nextInt();

            if (choice == 0) {
                System.out.println("Exiting...");
                break;
            }

            switch (choice) {
                case 1 -> {
                    System.out.println("Enter sides of the triangle: ");
                    double a = scanner.nextDouble();
                    double b = scanner.nextDouble();
                    double c = scanner.nextDouble();
                    try {
                        Triangle triangle = new Triangle(a, b, c);
                        triangle.print();
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 2 -> {
                    System.out.println("Enter sides of the rectangle: ");
                    double width = scanner.nextDouble();
                    double height = scanner.nextDouble();
                    try {
                        Rectangle rectangle = new Rectangle(width, height);
                        rectangle.print();
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 3 -> {
                    System.out.println("Enter radius of the circle: ");
                    double radius = scanner.nextDouble();
                    try {
                        Circle circle = new Circle(radius);
                        circle.print();
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 4 -> {
                    System.out.println("Choose a base for the pyramid (1: Triangle, 2: Rectangle, 3: Circle): ");
                    int baseChoice = scanner.nextInt();
                    Figure base = null;
                    switch (baseChoice) {
                        case 1 -> {
                            System.out.println("Enter sides of the triangle: ");
                            double a = scanner.nextDouble();
                            double b = scanner.nextDouble();
                            double c = scanner.nextDouble();
                            try {
                                base = new Triangle(a, b, c);
                            } catch (IllegalArgumentException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        case 2 -> {
                            System.out.println("Enter sides of the rectangle: ");
                            double width = scanner.nextDouble();
                            double height = scanner.nextDouble();
                            try {
                                base = new Rectangle(width, height);
                            } catch (IllegalArgumentException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        case 3 -> {
                            System.out.println("Enter radius of the circle: ");
                            double radius = scanner.nextDouble();
                            try {
                                base = new Circle(radius);
                            } catch (IllegalArgumentException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    }
                    if (base != null) {
                        System.out.println("Enter the height of the pyramid: ");
                        double height = scanner.nextDouble();
                        try {
                            Pyramid pyramid = new Pyramid(base, height);
                            pyramid.print();
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }
}



























