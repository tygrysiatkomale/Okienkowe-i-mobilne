
public class Main {
    public static void main(String[] args) {
        ShelterManager manager = new ShelterManager();
        manager.addShelter("Central Shelter", 5);

        AnimalShelter shelter = manager.getShelters().get("Central Shelter");

        Animal animal1 = new Animal("Burek", "Pies", AnimalCondition.ZDROWE, 5, 250.00);
        Animal animal2 = new Animal("Misia", "Kot", AnimalCondition.CHORE, 2, 150.00);
        Animal animal3 = new Animal("Rex", "Pies", AnimalCondition.W_TRAKCIE_ADOPCJI, 3, 300.00);

        shelter.addAnimal(animal1);
        shelter.addAnimal(animal2);
        shelter.addAnimal(animal3);
        shelter.summary();

        shelter.changeCondition(animal1, AnimalCondition.KWARANTANNA);
        shelter.changeAge(animal2, 3);
        System.out.println("Liczba zwierząt chorych: " + shelter.countByCondition(AnimalCondition.CHORE));

        System.out.println("\nPosortowane zwierzęta po imieniu:");
        shelter.sortByName().forEach(Animal::print);

        System.out.println("\nPosortowane zwierzęta po cenie:");
        shelter.sortByPrice().forEach(Animal::print);

        System.out.println("\nWyszukiwanie zwierzęcia o nazwie 'Misia':");
        shelter.search("Misia").ifPresent(Animal::print);

        System.out.println("\nWyszukiwanie zwierząt zawierających 'Pies':");
        shelter.searchPartial("Pies").forEach(Animal::print);

        System.out.println("\nNajdroższe zwierzę:");
        shelter.max().print();

        System.out.println("\nPodsumowanie schronisk:");
        manager.summary();
    }
}
