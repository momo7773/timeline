package com.ecnu.demo;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class CommentRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @MockBean
    private CommentRepository commentRepository;

    private Comment newComment;
    @Before
    public void init() {
        newComment = new Comment("momo","testing repository","2019-11-4 21:16:00","/");
    }

    @After
    public void cleanup() {
        this.testEntityManager.clear();
    }

    @Test
    //test the function of get later comments.
    public void test_comment_should_be_retrieved_later_than_time() throws Exception{
        this.testEntityManager.persist(newComment);
        List<Comment> comments = commentRepository.retrieveCommentLaterThanTime("2019-11-4 21:15:00");

        for (Comment comment : comments) {
            Assert.assertEquals(1, comment.getId());
            Assert.assertEquals("momo", comment.getComment_name());
            Assert.assertEquals("testing repository", comment.getComment_content());
            Assert.assertEquals("2019-11-4 21:16:00", comment.getComment_time());
            Assert.assertEquals("/", comment.getComment_img());
        }
    }

    @Test
    public void test_comment_should_be_earlier_than_time() throws Exception{
         this.testEntityManager.persist(newComment);
        List<Comment> comments = commentRepository.retrieveCommentEarlyThanTime("2019-11-4 21:17:00");

         //Verify whether the method has been executed or not.
         verify(commentRepository,never()).findFirstComment();
         verify(commentRepository,never()).retrieveCommentLaterThanTime(Mockito.anyString());
         verify(commentRepository).retrieveCommentEarlyThanTime(Mockito.anyString());

        for (Comment comment : comments) {
            Assert.assertEquals(1, comment.getId());
            Assert.assertEquals("momo", comment.getComment_name());
            Assert.assertEquals("testing repository", comment.getComment_content());
            Assert.assertEquals("2019-11-4 21:16:00", comment.getComment_time());
            Assert.assertEquals("/", comment.getComment_img());
        }
    }

    @Test
    public void test_get_comment_number_should_be_the_size_of_comment_list() throws Exception {
        Comment commentFake1 = new Comment("tyq","test number","2019-11-4 23:25:12","/1.jpg");
        Comment commentFake2 = new Comment("momo","test method 3","2019-11-4 23:26:12","2.jpg");
        this.testEntityManager.persist(commentFake1);
        this.testEntityManager.persist(commentFake2);
        List<Comment> comments = commentRepository.retrieveCommentLaterThanTime("2019-11-4 23:00:00");
        //verify whether the method has been executed or not.
        verify(commentRepository,never()).findFirstComment();
        verify(commentRepository,never()).retrieveCommentEarlyThanTime(Mockito.anyString());
        verify(commentRepository).retrieveCommentLaterThanTime(Mockito.anyString());
        Assert.assertEquals(2,comments.size());
    }
    @Test
    public void comment_list_should_be_exactly_same() throws Exception {
        this.testEntityManager.persist(newComment);
        List<Comment> comments = commentRepository.findFirstComment();
        //verify whether the method has been executed or not.
        int count = 0;
        for (Comment comment : comments) {
            count++;
            Assert.assertEquals(1, comment.getId());
            Assert.assertEquals("momo", comment.getComment_name());
            Assert.assertEquals("testing repository", comment.getComment_content());
            Assert.assertEquals("2019-11-4 21:16:00", comment.getComment_time());
            Assert.assertEquals("/", comment.getComment_img());
        }
        Assert.assertEquals(1, count);
    }
    @Test
    //test the condition of the bean
    public void select_first_comment() {
        Comment firstComment = new Comment("tyq","test number","2019-11-4 23:25:12","/1.jpg");
        List<Comment> commentlst = new ArrayList<>();
        commentlst.add(firstComment);
        CommentRepository mock = mock(CommentRepository.class);
        when(mock.findFirstComment()).thenReturn(commentlst);
        List<Comment> result = mock.findFirstComment();
        Comment firstResult = result.get(0);
        Assert.assertEquals(firstComment.getId(),firstResult.getId());
        Assert.assertEquals(firstComment.getComment_name(),firstResult.getComment_name());
        Assert.assertEquals(firstComment.getComment_content(),firstResult.getComment_content());
        Assert.assertEquals(firstComment.getComment_time(),firstResult.getComment_time());
        Assert.assertEquals(firstComment.getComment_img(),firstResult.getComment_img());
    }

}
