package com.zensar.service.serviceImpl;

import com.zensar.dto.CommentDto;
import com.zensar.entity.CommentEntity;
import com.zensar.entity.PostEntity;
import com.zensar.exception.BlogApiException;
import com.zensar.exception.ResourceNotFoundException;
import com.zensar.repository.CommentRepository;
import com.zensar.repository.PostRepository;
import com.zensar.service.CommentService;
import java.util.*;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    private ModelMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository,ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    @SneakyThrows
    public ResponseEntity<CommentDto> createComment(long postId, CommentDto comment) {

      PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("resource not found"));

       CommentEntity entity =mapper.map(comment, CommentEntity.class);
       entity.setPost(post);
       return new ResponseEntity<>(
               mapper.map(commentRepository.save(entity),CommentDto.class),
               HttpStatus.CREATED);

    }

    @Override
    @SneakyThrows
    public ResponseEntity<List<CommentDto>> getAllComment(long postId) {

        PostEntity post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Resource not found"));
       if( commentRepository.findByPostId(postId).isEmpty()){
           throw new ResourceNotFoundException("No comments found for this post: "+postId);
       }
      return new ResponseEntity<>( commentRepository.findByPostId(postId).stream()
               .map(comment->mapper.map(comment,CommentDto.class)).collect(Collectors.toList()),HttpStatus.OK
      );
    }

    @Override
    public ResponseEntity<CommentDto> getCommentById(long postId,long commentId) {

        PostEntity post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Resource not found for post: "+postId)
        );

      CommentEntity commentEntity =  commentRepository.findById(commentId).orElseThrow(
              ()-> new ResourceNotFoundException("Resource not found for comment: "+commentId));


      boolean isBelongToPost = commentEntity.getPost().getId().equals(postId);

      if(!isBelongToPost){
          throw new BlogApiException("Comment does not belong to this post : "+postId);
      }


      return new ResponseEntity<>(
              mapper.map(commentEntity,CommentDto.class),
              HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CommentDto> updateCommentById(long postId, long commentId,CommentDto commentDto) {
     PostEntity postEntity=  postRepository.findById(postId).orElseThrow(
               ()->new ResourceNotFoundException("Post does not exist : "+postId));

     CommentEntity commentEntity = commentRepository.findById(commentId).orElseThrow(
             ()->new ResourceNotFoundException("Comment does not exist: "+commentId));

     boolean isBelongToPost = commentEntity.getPost().getId().equals(postId);

     if(!isBelongToPost){
         throw new BlogApiException("Comment does not belong to this post: "+postId);
     }

     commentEntity.setContent(commentDto.getContent());
     commentEntity.setName(commentDto.getName());
     commentEntity.setEmail(commentDto.getEmail());

    return new ResponseEntity<>(mapper.map(
            commentRepository.save(commentEntity),CommentDto.class),
            HttpStatus.OK);

    }
}
