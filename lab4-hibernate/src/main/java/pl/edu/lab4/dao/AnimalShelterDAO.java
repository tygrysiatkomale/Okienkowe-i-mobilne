package pl.edu.lab4.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.edu.lab4.entity.AnimalShelter;
import pl.edu.lab4.util.HibernateUtil;

import java.util.List;

public class AnimalShelterDAO {
    public void save(AnimalShelter shelter) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(shelter);
            tx.commit();
        }
    }

    public AnimalShelter findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(AnimalShelter.class, id);
        }
    }

    public List<AnimalShelter> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from AnimalShelter", AnimalShelter.class).list();
        }
    }

    public void delete(AnimalShelter shelter) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(shelter);
            tx.commit();
        }
    }

    public void update(AnimalShelter shelter) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(shelter);
            tx.commit();
        }
    }

    public AnimalShelter findByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from AnimalShelter s where s.shelterName = :name", AnimalShelter.class)
                    .setParameter("name", name)
                    .uniqueResult();
        }
    }

    public List<AnimalShelter> findEmpty() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("select s from AnimalShelter s where size(s.animalList) = 0", AnimalShelter.class)
                    .list();
        }
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
