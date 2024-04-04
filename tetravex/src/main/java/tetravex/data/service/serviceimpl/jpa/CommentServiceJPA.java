package tetravex.data.service.serviceimpl.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
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
        return entityManager.createQuery("SELECT a FROM Comment a WHERE a.game = '?'"
                        .replace("?", game),
                Comment.class).getResultList();
    }


    @Override
    public void reset() throws CommentException {
        entityManager.createNativeQuery("DELETE FROM comment").executeUpdate();
    }
}
