package tetravex.data.service.serviceimpl.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import tetravex.data.entity.Comment;
import tetravex.data.exceptions.CommentException;
import tetravex.data.service.CommentService;

import java.util.List;


@Transactional
public class CommentServiceJPA implements CommentService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addComment(Comment comment) throws CommentException {
        entityManager.persist(comment);
    }

    @Override
    public List<Comment> getComments(String game) throws CommentException {
        return
                entityManager.createQuery("SELECT a FROM Comment a", Comment.class).getResultList();
    }

    @Override
    public void reset() throws CommentException {
        entityManager.createQuery("DELETE Comment", Comment.class);

    }
}