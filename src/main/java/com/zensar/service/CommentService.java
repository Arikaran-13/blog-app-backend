package com.zensar.service;

import com.zensar.dto.CommentDto;
import org.springframework.http.ResponseEntity;
import java.util.*;
public interface CommentService {


    ResponseEntity<CommentDto> createComment(long postId,CommentDto comment);
    ResponseEntity<List<CommentDto>>getAllComment(long postId);

    ResponseEntity<CommentDto>getCommentById(long postId,long commentId);
    ResponseEntity<CommentDto>updateCommentById(long postId,long commentId,CommentDto commentDto);
}
