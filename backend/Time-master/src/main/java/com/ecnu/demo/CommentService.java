package com.ecnu.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Yiqing Tao
 * @date 2019-11-26 19:51
 */

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> getAllLateComment(String time) {
        return commentRepository.retrieveCommentLaterThanTime(time);
    }

    public List<Comment> getAllEarlyComment(String time) {
        return commentRepository.retrieveCommentEarlyThanTime(time);
    }

    public List<Comment> getFirstComment() {
        return commentRepository.findFirstComment();
    }
}
