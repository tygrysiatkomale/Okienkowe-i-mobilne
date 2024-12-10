package pl.edu.lab4.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.edu.lab4.entity.Animal;
import pl.edu.lab4.util.HibernateUtil;

import java.util.List;

public class AnimalDAO {
    public void save(Animal animal) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(animal);
            tx.commit();
        }
    }

    public Animal findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Animal.class, id);
        }
    }

    public List<Animal> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Animal", Animal.class).list();
        }
    }

    public void delete(Animal animal) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(animal);
            tx.commit();
        }
    }

    public void update(Animal animal) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(animal);
            tx.commit();
        }
    }

    public List<Animal> findByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Animal a where a.name = :name", Animal.class)
                    .setParameter("name", name)
                    .list();
        }
    }

    public List<Animal> searchPartial(String keyword) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "from Animal a where a.name like :keyword or a.species like :keyword", Animal.class)
                    .setParameter("keyword", "%" + keyword + "%")
                    .list();
        }
    }
}
