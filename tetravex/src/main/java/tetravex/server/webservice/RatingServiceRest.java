package tetravex.server.webservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tetravex.data.entity.Rating;
import tetravex.data.service.RatingService;

import java.util.List;

@RestController
@RequestMapping("/api/rating")
public class RatingServiceRest {

    @Autowired
    private RatingService ratingService;

    @GetMapping("/{game}")
    private Integer getRating (@PathVariable String game) {
        return ratingService.getAverageRating(game);
    }


    @PostMapping
    private void addRating(@RequestBody Rating rating ) {
        ratingService.setRating(rating);
    }


    @GetMapping("/{game}/{player}")
    private int getSpecicPlayerRating (@PathVariable String game, @PathVariable String player) {
        return ratingService.getRating(game, player);
    }
}
