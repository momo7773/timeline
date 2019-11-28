package com.ecnu.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author Yiqing Tao
 * @date 2019-11-26 19:58
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentServiceTest {
    @Autowired
    private CommentService commentService;

    @MockBean
    private CommentRepository commentRepository;

    @Test
    public void shouldGetLaterComment() {
        Comment commentFake = new Comment(1,"momo","testing repository","2019-11-4 21:16:00","/");
        List<Comment> listFake = new ArrayList<>();
        listFake.add(commentFake);
        when(commentRepository.retrieveCommentLaterThanTime("2019-11-4 21:15:00")).thenReturn(listFake);
        List<Comment> commentList = commentService.getAllLateComment(anyString());
        verify(commentRepository, times(1)).retrieveCommentLaterThanTime(anyString());
    }

    @Test
    public void shouldGetEarlyComment() {
        Comment commentFake = new Comment(1,"momo","testing repository","2019-11-4 21:16:00","/");
        List<Comment> listFake = new ArrayList<>();
        listFake.add(commentFake);
        when(commentRepository.retrieveCommentEarlyThanTime("2019-11-4 21:15:00")).thenReturn(listFake);
        commentService.getAllEarlyComment(anyString());
        verify(commentRepository, times(1)).retrieveCommentEarlyThanTime(anyString());
    }

    @Test
    public void shouldGetFirstComment() {
        Comment commentFake = new Comment(1,"momo","testing repository","2019-11-4 21:16:00","/");
        List<Comment> listFake = new ArrayList<>();
        listFake.add(commentFake);
        when(commentRepository.findFirstComment()).thenReturn(listFake);
        commentService.getFirstComment();
        verify(commentRepository, times(1)).findFirstComment();
    }
}
