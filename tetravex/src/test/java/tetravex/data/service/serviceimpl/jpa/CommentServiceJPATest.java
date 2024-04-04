package tetravex.data.service.serviceimpl.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import tetravex.data.service.CommentService;
import tetravex.data.service.serviceimpl.CommentServiceTest;

public class CommentServiceJPATest extends CommentServiceTest {

    @Override
    @Autowired
    @Qualifier("commentJPA")
    protected void setTestedClass(CommentService commentService) {
        this.commentService = commentService;
    }
}
