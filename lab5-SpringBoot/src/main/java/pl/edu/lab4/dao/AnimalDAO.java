package pl.edu.lab4.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.lab4.entity.Animal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AnimalDAO {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Animal animal) {
        em.persist(animal);
    }

    public Animal findById(Long id) {
        return em.find(Animal.class, id);
    }

    public List<Animal> findAll() {
        return em.createQuery("from Animal", Animal.class).getResultList();
    }

    @Transactional
    public void delete(Animal animal) {
        if (!em.contains(animal)) {
            animal = em.merge(animal);
        }
        em.remove(animal);
    }

    @Transactional
    public void update(Animal animal) {
        em.merge(animal);
    }

    public List<Animal> findByName(String name) {
        return em.createQuery("from Animal a where a.name = :name", Animal.class)
                .setParameter("name", name)
                .getResultList();
    }

    public List<Animal> searchPartial(String keyword) {
        return em.createQuery("from Animal a where a.name like :keyword or a.species like :keyword", Animal.class)
                .setParameter("keyword", "%" + keyword + "%")
                .getResultList();
    }
}
