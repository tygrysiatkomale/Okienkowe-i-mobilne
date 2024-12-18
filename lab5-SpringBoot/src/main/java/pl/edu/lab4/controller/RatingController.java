package pl.edu.lab4.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pl.edu.lab4.dto.RatingDTO;
import pl.edu.lab4.service.RatingService;

@RestController
@RequestMapping("/api")
public class RatingController {

    private RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    // POST - adds new rate for shelter
    @PostMapping("/rating")
    public ResponseEntity<?> addRating(@RequestBody RatingDTO dto) {
        ratingService.addRating(dto);
        return ResponseEntity.ok("Rating added");
    }

}
