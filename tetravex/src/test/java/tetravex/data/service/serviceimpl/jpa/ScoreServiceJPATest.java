package tetravex.data.service.serviceimpl.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import tetravex.data.service.ScoreService;
import tetravex.data.service.serviceimpl.ScoreServiceTest;

public class ScoreServiceJPATest extends ScoreServiceTest {
    @Override
    @Autowired
    @Qualifier("scoreJPA")
    protected void setTestedClass(ScoreService scoreService) {
        this.scoreService = scoreService;
    }
}
