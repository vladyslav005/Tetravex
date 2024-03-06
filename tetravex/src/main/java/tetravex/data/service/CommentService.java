package tetravex.data.service;


import tetravex.data.entity.Comment;
import tetravex.data.service.exceptions.CommentException;

import java.util.List;

public interface CommentService {
    void addComment(Comment comment) throws CommentException;
    List<Comment> getComments(String game) throws CommentException;
    void reset() throws CommentException;
}
