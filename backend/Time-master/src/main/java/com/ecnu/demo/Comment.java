package com.ecnu.demo;
import javax.persistence.*;
import java.util.Date;

/**
 * @author Yiqing Tao
 * @Date 2019-10-25
 * the entity class comment, same as database design
 */
@Entity
@Table(name="comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int id;

    @Column(name = "comment_name")
    private String comment_name;
    @Column(name = "comment_content")
    private String comment_content;
    @Column(name = "comment_time")
    private String comment_time;
    @Column(name = "comment_img")
    private String comment_img;

    public Comment(){

    }
    public Comment(int id,String comment_name,String comment_content,String comment_time,String comment_img){
        this.id = id;
        this.comment_name = comment_name;
        this.comment_content = comment_content;
        this.comment_time = comment_time;
        this.comment_img = comment_img;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment_name() {
        return comment_name;
    }

    public void setComment_name(String comment_name) {
        this.comment_name = comment_name;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public String getComment_time() {
        return comment_time;
    }

    public void setComment_time(String comment_time) {
        this.comment_time = comment_time;
    }

    public String getComment_img() {
        return comment_img;
    }

    public void setComment_img(String comment_img) {
        this.comment_img = comment_img;
    }
}
