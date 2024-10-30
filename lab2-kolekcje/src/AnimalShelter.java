import java.util.*;

// 3. Klasa AnimalShelter
class AnimalShelter {
    private String shelterName;
    private List<Animal> animalList;
    private int maxCapacity;

    public AnimalShelter(String shelterName, int maxCapacity) {
        this.shelterName = shelterName;
        this.animalList = new ArrayList<>();
        this.maxCapacity = maxCapacity;
    }

    public void addAnimal(Animal animal) {
        if (animalList.size() >= maxCapacity) {
            System.err.println("Nie można dodać więcej zwierząt, osiągnięto maksymalną pojemność.");
            return;
        }
        if (animalList.contains(animal)) {
            System.out.println("Zwierzę już istnieje w schronisku.");
        } else {
            animalList.add(animal);
        }
    }

    public void removeAnimal(Animal animal) {
        animalList.remove(animal);
    }

    public void getAnimal(Animal animal) {
        if (animalList.contains(animal)) {
            animal.setCondition(AnimalCondition.W_TRAKCIE_ADOPCJI);
            animalList.remove(animal);
        }
    }

    public void changeCondition(Animal animal, AnimalCondition condition) {
        animalList.stream().filter(a -> a.equals(animal)).forEach(a -> a.setCondition(condition));
    }

    public void changeAge(Animal animal, int newAge) {
        animalList.stream().filter(a -> a.equals(animal)).forEach(a -> a.setAge(newAge));
    }

    public long countByCondition(AnimalCondition condition) {
        return animalList.stream().filter(a -> a.getCondition() == condition).count();
    }

    public List<Animal> sortByName() {
        return animalList.stream().sorted(Comparator.comparing(Animal::getName)).toList();
    }

    public List<Animal> sortByPrice() {
        return animalList.stream().sorted(Comparator.comparingDouble(Animal::getPrice)).toList();
    }

    public Optional<Animal> search(String name) {
        return animalList.stream()
                .filter(a -> a.getName().equals(name))
                .min(Comparator.comparing(Animal::getName));
    }

    public List<Animal> searchPartial(String keyword) {
        return animalList.stream().filter(a -> a.getName().contains(keyword) || a.getSpecies().contains(keyword)).toList();
    }

    public void summary() {
        System.out.printf("Schronisko: %s, Maksymalna pojemność: %d, Obecna liczba zwierząt: %d\n", shelterName, maxCapacity, animalList.size());
        animalList.forEach(Animal::print);
    }

    public Animal max() {
        return Collections.max(animalList, Comparator.comparingDouble(Animal::getPrice));
    }

    public List<Animal> getAnimalList() {
        return animalList;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }
}