package com.ecnu.demo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author Yiqing Tao
 * @Date 2019-10-25
 * the repository class inheits jpaRepository to fetch data from db.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer>{
    /**get the comment later than the time specified **/
    @Query(value = "select id,comment_name, comment_time,comment_content,comment_img from comment where comment_time > :time order by comment_time", nativeQuery = true)
    List<Comment> retrieveCommentLaterThanTime(@Param("time") String time);

    /** get the comment earlier than the time specified **/
    @Query(value = "select id,comment_name, comment_time,comment_content,comment_img from comment where comment_time < :time order by comment_time DESC", nativeQuery = true)
    List<Comment> retrieveCommentEarlyThanTime(@Param("time") String time);

    /** get the first comment in database **/
    @Query(value = "select id,comment_name,comment_time,comment_content,comment_img from comment limit 1", nativeQuery = true)
    List<Comment> findFirstComment();
}
