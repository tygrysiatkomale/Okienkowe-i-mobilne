package pl.edu.lab4.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.lab4.entity.AnimalShelter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AnimalShelterDAO {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(AnimalShelter shelter) {
        em.persist(shelter);
    }

    public AnimalShelter findById(Long id) {
        return em.find(AnimalShelter.class, id);
    }

    public List<AnimalShelter> findAll() {
        return em.createQuery("from AnimalShelter", AnimalShelter.class).getResultList();
    }

    @Transactional
    public void delete(AnimalShelter shelter) {
        if (!em.contains(shelter)) {
            shelter = em.merge(shelter);
        }
        em.remove(shelter);
    }

    @Transactional
    public void update(AnimalShelter shelter) {
        em.merge(shelter);
    }

    public AnimalShelter findByName(String name) {
        return em.createQuery("from AnimalShelter s where s.shelterName = :name", AnimalShelter.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public List<AnimalShelter> findEmpty() {
        return em.createQuery("select s from AnimalShelter s where size(s.animalList) = 0", AnimalShelter.class)
                .getResultList();
    }

    public void printSummary() {
        List<AnimalShelter> shelters = findAll();
        for (AnimalShelter shelter : shelters) {
            int currentCount = shelter.getAnimalList().size();
            int maxCapacity = shelter.getMaxCapacity();
            double occupancy = maxCapacity > 0 ? ((double) currentCount / maxCapacity) * 100 : 0.0;
            System.out.printf("Schronisko: %s, Zapełnienie: %.2f%%, Aktualna liczba zwierząt: %d, Maksymalna: %d%n",
                    shelter.getShelterName(), occupancy, currentCount, maxCapacity);
        }
    }
}
