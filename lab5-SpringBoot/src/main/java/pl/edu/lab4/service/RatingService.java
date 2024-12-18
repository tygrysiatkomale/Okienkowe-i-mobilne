package pl.edu.lab4.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.lab4.dao.RatingDAO;
import pl.edu.lab4.dto.RatingDTO;
import pl.edu.lab4.entity.Rating;
import pl.edu.lab4.exception.EntityNotFoundException;

@Service
public class RatingService {

    private final RatingDAO ratingDAO;
    private final AnimalShelterService shelterService;

    public RatingService(RatingDAO ratingDAO, AnimalShelterService shelterService) {
        this.ratingDAO = ratingDAO;
        this.shelterService = shelterService;
    }

    @Transactional
    public void addRating(RatingDTO dto) {
        var shelter = shelterService.getShelterById(dto.getShelterId());
        if (shelter == null) throw new EntityNotFoundException("Shelter not found");
        Rating rating = new Rating(dto.getValue(), dto.getComment(), dto.getRatingDate());
        rating.setShelter(shelter);
        ratingDAO.save(rating);
    }
}
