package com.llg.blog.dao;

import com.llg.blog.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//Comment, Long (primary key)
public interface CommentRepository extends JpaRepository<Comment, Long> {


    List<Comment> findByBlogId(Long blogId, Sort sort);


}
