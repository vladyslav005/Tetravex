package tetravex.data.service.serviceimpl.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import tetravex.data.service.RatingService;
import tetravex.data.service.serviceimpl.RatingServiceTest;

public class RatingServiceJDBCTest extends RatingServiceTest {

    @Override
    @Autowired
    @Qualifier("ratingJDBC")
    protected void setTestedClass(RatingService ratingService) {
        this.ratingService = ratingService;
    }
}
