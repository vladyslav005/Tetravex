package tetravex.data.service.serviceimpl.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import tetravex.data.service.ScoreService;
import tetravex.data.service.serviceimpl.ScoreServiceTest;


public class ScoreServiceJDBCTest extends ScoreServiceTest {

    @Override
    @Autowired
    @Qualifier("scoreJDBC")
    protected void setTestedClass(ScoreService scoreService) {
        this.scoreService = scoreService;
    }
}
