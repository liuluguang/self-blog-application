package com.llg.blog.service;

import com.llg.blog.po.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> listCommentsByBlogId(Long blogId);
    Comment saveComment(Comment comment);
}
