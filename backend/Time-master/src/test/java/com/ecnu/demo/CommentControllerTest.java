package com.ecnu.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Yiqing Tao
 * @date 2019-11-26 19:58
 */
@RunWith(SpringRunner.class)
@WebMvcTest
public class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CommentService commentService;
    CommentController commentController;
    @Test
    public void shouldGetEarlyComment() throws Exception {
        ResultActions perform = mockMvc.perform(get("/getEarly").param("time", "2019-11-4 23:20:11"));
        perform.andExpect(status().isOk());
        verify(commentService, times(1)).getAllEarlyComment(anyString());
    }

    @Test
    public void shouldGetLateComment() throws Exception {
        ResultActions perform = mockMvc.perform(get("/timeline").param("time", "2019-11-4 23:20:11"));
        perform.andExpect(status().isOk());
        verify(commentService,times(1)).getAllLateComment(anyString());
    }

    @Test
    public void shouldGetNumber() throws Exception {
        ResultActions perform = mockMvc.perform(get("/getNumber").param("time", "2019-11-4 23:20:11"));
        perform.andExpect(status().isOk());
        verify(commentService,times(1)).getAllLateComment(anyString());
    }

    @Test
    public void shouldInitialize() throws Exception {
        ResultActions perform = mockMvc.perform(get("/initialize"));
        perform.andExpect(status().isOk());
        verify(commentService,times(1)).getFirstComment();
    }
}
