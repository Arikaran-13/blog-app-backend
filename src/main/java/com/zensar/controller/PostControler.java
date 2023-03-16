package com.zensar.controller;

import com.zensar.constants.AppConstants;
import com.zensar.dto.CommentDto;
import com.zensar.dto.PostDto;
import com.zensar.dto.PostResponse;
import com.zensar.service.CommentService;
import com.zensar.service.PostService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.print.attribute.standard.Media;
import java.util.*;
@RestController
@RequestMapping("/api/posts")
public class PostControler {

   private PostService postService;
   private CommentService commentService;

    public PostControler(PostService postService,CommentService commentService) {

        this.postService = postService;
        this.commentService=commentService;
    }

    @PostMapping(value ="/",
                 consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PostDto>createNewPost(@RequestBody PostDto post){
       return postService.createPost(post);
    }

 @GetMapping(
         produces = MediaType.APPLICATION_JSON_VALUE
 )
    public ResponseEntity<PostResponse>getAllposts(
            @RequestParam(value = "pageNumber",defaultValue=AppConstants.DEFAULT_PAGE_NUMBER,required=false)int pageNumber,
            @RequestParam(value="pageSize", defaultValue=AppConstants.DEFAULT_PAGE_SIZE , required=false)int pageSize,
            @RequestParam(value="sortBy",defaultValue = AppConstants.SORT_BY_ID ,required = false)String sortBy,
            @RequestParam(value="sortDir",defaultValue=AppConstants.SORT_DIR,required = false)String sortDir
         ){

        return postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
 }
@GetMapping(value = "/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
 public ResponseEntity<PostDto>getPostById(@PathVariable(name ="id")long id){
        return postService.getPostById(id);
 }

 @PutMapping(value = "/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostDto>updatePost(@PathVariable(name ="id")long id, PostDto post){
        return postService.updatePostById(id,post);
 }

 @DeleteMapping(value = "/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
 public ResponseEntity<PostDto>deletePost(@PathVariable(name="id")long id){
        return postService.deletePostById(id);
 }
@PostMapping("/{postId}/comments")
 public ResponseEntity<CommentDto>createCommment(@PathVariable(name="postId") long postId,@RequestBody CommentDto comment){

       return commentService.createComment(postId,comment);

 }

 @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentDto>>getAllCommentsForPost(@PathVariable(name="postId")long postId){
     return  commentService.getAllComment(postId);
 }
 @GetMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto>getCommentById(@PathVariable(name="postId")long postId,@PathVariable(name="commentId")long id){

        return commentService.getCommentById(postId,id);
 }
@PutMapping("/{postId}/comments/{commentId}")
 public ResponseEntity<CommentDto>updateCommentById(@PathVariable(name="commentId")long commentId,@PathVariable(name="postId")long postId,@RequestBody CommentDto commentDto){
        return commentService.updateCommentById(postId,commentId,commentDto);
 }
}
