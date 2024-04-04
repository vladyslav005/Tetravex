package tetravex.data.service.serviceimpl.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import tetravex.data.service.CommentService;
import tetravex.data.service.serviceimpl.CommentServiceTest;

public class CommentServiceJDBCTest extends CommentServiceTest {
    @Override
    @Autowired
    @Qualifier("commentJDBC")
    protected void setTestedClass(CommentService commentService) {
        this.commentService = commentService;
    }
}
