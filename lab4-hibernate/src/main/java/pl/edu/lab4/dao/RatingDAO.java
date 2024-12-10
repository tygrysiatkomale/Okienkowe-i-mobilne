package pl.edu.lab4.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.edu.lab4.entity.Rating;
import pl.edu.lab4.util.HibernateUtil;

import java.util.List;

public class RatingDAO {
    public void save(Rating rating) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(rating);
            tx.commit();
        }
    }

    public Rating findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Rating.class, id);
        }
    }

    public List<Rating> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Rating", Rating.class).list();
        }
    }

    public void delete(Rating rating) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(rating);
            tx.commit();
        }
    }

    public void update(Rating rating) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(rating);
            tx.commit();
        }
    }

    public Double getAverageRatingForShelter(Long shelterId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "select avg(r.value) from Rating r where r.shelter.id = :sid";
            return session.createQuery(hql, Double.class)
                    .setParameter("sid", shelterId)
                    .uniqueResult();
        }
    }

    public List<Rating> findByShelterId(Long shelterId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Rating r where r.shelter.id = :sid", Rating.class)
                    .setParameter("sid", shelterId)
                    .list();
        }
    }
}