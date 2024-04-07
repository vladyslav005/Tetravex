package tetravex.data.service.serviceimpl.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import tetravex.data.service.RatingService;
import tetravex.data.service.serviceimpl.RatingServiceTest;

public class RatingServiceJPATest extends RatingServiceTest {

    @Override
    @Autowired
    @Qualifier("ratingJPA")
    protected void setTestedClass(RatingService commentService) {
        this.ratingService = commentService;
    }
}
