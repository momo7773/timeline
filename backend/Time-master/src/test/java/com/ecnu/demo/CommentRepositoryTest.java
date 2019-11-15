package com.ecnu.demo;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
@WebMvcTest
@AutoConfigureMockMvc
public class CommentRepositoryTest {
    @Autowired
    protected MockMvc mockMvc;
    @MockBean
    private CommentRepository commentRepository;
    @Test
    //test the function of get later comments.
    public void test_comment_should_be_retrieved_later_than_time() throws Exception{
        Comment commentFake = new Comment(1,"momo","testing repository","2019-11-4 21:16:00","/");
        List<Comment> listFake = new ArrayList<>();
        listFake.add(commentFake);
        //first stub the commentRepository to mock the behaviour when called retrieveCommentLaterThanTime method.
        when(commentRepository.retrieveCommentLaterThanTime(Mockito.anyString())).thenReturn(listFake);

        //then use mockMvc to imitate the http request, give fake request to controller layer.
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/timeline")
                .param("time","2019-11-2 10:11:12")
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        //get the response given by the controller.
        String response = result.getResponse().getContentAsString();
        JSONArray jsonArray = new JSONArray(response);
        JSONObject jsonObject = jsonArray.getJSONObject(0);

        //test whether the content of the response equals to the original one.
        Assert.assertEquals(1,jsonObject.getInt("id"));
        Assert.assertEquals("momo",jsonObject.getString("comment_name"));
        Assert.assertEquals("testing repository",jsonObject.getString("comment_content"));
        Assert.assertEquals("2019-11-4 21:16:00",jsonObject.getString("comment_time"));
        Assert.assertEquals("/",jsonObject.getString("comment_img"));
    }
    @Test
    public void test_comment_should_be_earlier_than_time() throws Exception{
         Comment commentFake = new Comment(1,"momo","testing repository","2019-11-4 21:16:00","/");
         List<Comment> commentList = new ArrayList<>();
         commentList.add(commentFake);

         //first stub the behavior when called retrieveCommentEarlyThanTime.
         when(commentRepository.retrieveCommentEarlyThanTime(Mockito.anyString())).thenReturn(commentList);
         //Then mock the http request to give specific params.
         MvcResult result = mockMvc.perform(
                 MockMvcRequestBuilders.get("/getEarly")
                 .param("time","2019-11-4 23:20:11")
         ).andExpect(MockMvcResultMatchers.status().isOk())
                 .andDo(MockMvcResultHandlers.print())
                 .andReturn();
         //get response given by the controller
         String response = result.getResponse().getContentAsString();
         JSONArray jsonArray = new JSONArray(response);
         JSONObject jsonObject = jsonArray.getJSONObject(0);

         //Verify whether the method has been executed or not.
         verify(commentRepository,never()).findFirstComment();
         verify(commentRepository,never()).retrieveCommentLaterThanTime(Mockito.anyString());
         verify(commentRepository).retrieveCommentEarlyThanTime(Mockito.anyString());

         //Assert the content equals to the original one.
        Assert.assertEquals(1,jsonObject.getInt("id"));
        Assert.assertEquals("momo",jsonObject.getString("comment_name"));
        Assert.assertEquals("testing repository",jsonObject.getString("comment_content"));
        Assert.assertEquals("2019-11-4 21:16:00",jsonObject.getString("comment_time"));
        Assert.assertEquals("/",jsonObject.getString("comment_img"));
    }
    @Test
    public void test_get_comment_number_should_be_the_size_of_comment_list() throws Exception {
        Comment commentFake1 = new Comment(2,"tyq","test number","2019-11-4 23:25:12","/1.jpg");
        Comment commentFake2 = new Comment(3,"momo","test method 3","2019-11-4 23:26:12","2.jpg");
        List<Comment> commentList = new ArrayList<>();
        commentList.add(commentFake1);
        commentList.add(commentFake2);

        //first mock the repository behavior when called retrieveCommentLaterThanTime
        when(commentRepository.retrieveCommentLaterThanTime(Mockito.anyString())).thenReturn(commentList);
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/getNumber")
                .param("time","2019-11-4 19:10:11")
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        //get the response given by the controller
        String response = result.getResponse().getContentAsString();
        int number = Integer.parseInt(response);
        //verify whether the method has been executed or not.
        verify(commentRepository,never()).findFirstComment();
        verify(commentRepository,never()).retrieveCommentEarlyThanTime(Mockito.anyString());
        verify(commentRepository).retrieveCommentLaterThanTime(Mockito.anyString());
        Assert.assertEquals(commentList.size(),number);
    }
    @Test
    public void comment_list_should_be_exactly_same() throws Exception {
        Comment commentFake1 = new Comment();
        commentFake1.setId(2);
        commentFake1.setComment_name("tyq");
        commentFake1.setComment_content("test_initialize");
        commentFake1.setComment_time("2019-11-4 23:25:12");
        commentFake1.setComment_img("/1.jpg");
        List<Comment> commentList = new ArrayList<>();
        commentList.add(commentFake1);
        //mock the repository behaviour when called findFirstComment method.
        when(commentRepository.findFirstComment()).thenReturn(commentList);

        //mock the http request
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/initialize")
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String response = result.getResponse().getContentAsString();
        JSONArray jsonArray = new JSONArray(response);
        JSONObject jsonObject = jsonArray.getJSONObject(0);

        //verify whether the method has been executed or not.
        verify(commentRepository).findFirstComment();
        verify(commentRepository,never()).retrieveCommentLaterThanTime(Mockito.anyString());
        verify(commentRepository,never()).retrieveCommentEarlyThanTime(Mockito.anyString());
        Assert.assertEquals(commentList.size(),jsonArray.length());
        Assert.assertEquals(commentList.get(0).getId(),jsonObject.getInt("id"));
        Assert.assertEquals(commentList.get(0).getComment_name(),jsonObject.getString("comment_name"));
        Assert.assertEquals(commentList.get(0).getComment_content(),jsonObject.getString("comment_content"));
        Assert.assertEquals(commentList.get(0).getComment_time(),jsonObject.getString("comment_time"));
        Assert.assertEquals(commentList.get(0).getComment_img(),jsonObject.getString("comment_img"));
    }
    @Test
    //test the condition of the bean
    public void select_first_comment() {
        Comment firstComment = new Comment(2,"tyq","test number","2019-11-4 23:25:12","/1.jpg");
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
