package pl.edu.lab4.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.lab4.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RatingDAO {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Rating rating) {
        em.persist(rating);
    }

    public Rating findById(Long id) {
        return em.find(Rating.class, id);
    }

    public List<Rating> findAll() {
        return em.createQuery("from Rating", Rating.class).getResultList();
    }

    @Transactional
    public void delete(Rating rating) {
        if (!em.contains(rating)) {
            rating = em.merge(rating);
        }
        em.remove(rating);
    }

    @Transactional
    public void update(Rating rating) {
        em.merge(rating);
    }

    public Double getAverageRatingForShelter(Long shelterId) {
        String hql = "select avg(r.value) from Rating r where r.shelter.id = :sid";
        return em.createQuery(hql, Double.class)
                .setParameter("sid", shelterId)
                .getSingleResult();
    }

    public List<Rating> findByShelterId(Long shelterId) {
        return em.createQuery("from Rating r where r.shelter.id = :sid", Rating.class)
                .setParameter("sid", shelterId)
                .getResultList();
    }

    public List<Object[]> getShelterRatingStats() {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Object[].class);
        var ratingRoot = cq.from(Rating.class);

        cq.multiselect(
                ratingRoot.get("shelter"),
                cb.count(ratingRoot),
                cb.avg(ratingRoot.get("value"))
        );
        cq.groupBy(ratingRoot.get("shelter"));

        return em.createQuery(cq).getResultList();
    }
}
