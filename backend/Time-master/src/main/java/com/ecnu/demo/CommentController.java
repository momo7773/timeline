package com.ecnu.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Yiqing Tao
 * @Date 2019-10-25
 * spring controller, receive request from frontend.
 */
@org.springframework.stereotype.Controller

public class CommentController {
    @Autowired
    private CommentRepository commentRepository;

    //give the latest comments that haven't been shown on the screen.
    //call the repository method: retrieveCommentLaterThanTime
    @RequestMapping(value = "/timeline", method= RequestMethod.GET)
    @ResponseBody
    public List<Comment> getAllComment(@RequestParam(name = "time") String time) {
        System.out.println("receiver get");
        System.out.println("get time: "+ time);
        List<Comment> commentlst = commentRepository.retrieveCommentLaterThanTime(time);
        for (Comment currComment:commentlst) {
            System.out.println("comment name is "+ currComment.getComment_name());
        }
        System.out.println("In the funciton");
        return commentlst;

    }

    //give the number of latest comment to update the digit in the front end.
    //call the repository method: retrieveCommentLaterThanTime
    @RequestMapping(value = "/getNumber", method= RequestMethod.GET)
    @ResponseBody
    public int getNewNumber(@RequestParam(name = "time") String time) {
        System.out.println("get time: "+ time);
        List<Comment> commentlst = commentRepository.retrieveCommentLaterThanTime(time);
        for (Comment currComment:commentlst) {
            System.out.println("comment name is "+ currComment.getComment_name());
        }
        System.out.println("In the funciton");
        return commentlst.size();

    }

    //get the early comments that have not shown on the screen.
    //call the method retrieveCommentEarlyThanTime.
    @GetMapping(path = "/getEarly")
    @ResponseBody
    public List<Comment> retreiveEarlyComment(@RequestParam(name = "time") String time) {
        System.out.println("get early time" + time);
        List<Comment> commentLst = commentRepository.retrieveCommentEarlyThanTime(time);
        for (Comment currComment:commentLst) {
            System.out.println("comment name is "+ currComment.getComment_name());
        }
        return commentLst;

    }
    //get the initial comments to the frontend.
    //call the repository method find first comment.
    @GetMapping(path = "/initialize")
    @ResponseBody
    public List<Comment> getFirstComment() {
        List<Comment> commentLst = commentRepository.findFirstComment();
        for (Comment currComment:commentLst) {
            System.out.println("comment name is "+ currComment.getComment_name());
        }
        return commentLst;
    }
}
