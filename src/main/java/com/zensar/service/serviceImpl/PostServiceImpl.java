package com.zensar.service.serviceImpl;

import com.zensar.dto.PostDto;
import com.zensar.dto.PostResponse;
import com.zensar.entity.PostEntity;
import com.zensar.exception.ResourceNotFoundException;
import com.zensar.repository.PostRepository;
import com.zensar.service.PostService;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    PostRepository postRepository;


    ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepository,ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper=mapper;
    }

    @Override
    public ResponseEntity<PostDto> createPost(PostDto post) {
       PostEntity postEntity = postRepository.save(mapper.map(post, PostEntity.class));
        return new ResponseEntity<>(
                mapper.map(postEntity,PostDto.class),
                HttpStatus.CREATED
        );
    }

    @Override
    public ResponseEntity<PostResponse> getAllPost(int pageNumber,int pageSize,String sortBy,String sortDir) {

      Sort sort =   sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize, sort);

     Page<PostEntity> pageAblePost =    postRepository.findAll(pageable);
     List<PostEntity>posts =   pageAblePost.getContent();

       if(posts.isEmpty()){
           throw new ResourceNotFoundException("No posts are available");
       }
     List<PostDto>listOfPosts = posts.stream()
                .map(i->mapper.map(i,PostDto.class))
                .collect(Collectors.toList());

     PostResponse postResponse =  PostResponse.builder()
               .posts(listOfPosts)
               .pageNumber(pageNumber)
               .pageSize(pageSize)
               .totalElements(pageAblePost.getTotalElements())
               .isLast(pageAblePost.isLast())
               .totalPage(pageAblePost.getTotalPages())
               .build();

        return new ResponseEntity<>(
                postResponse , HttpStatus.ACCEPTED
        );
    }

    @Override
    @SneakyThrows(ResourceNotFoundException.class)
    public ResponseEntity<PostDto> getPostById(long id) {

       PostEntity  post = postRepository
               .findById(id)
               .orElseThrow(()-> new ResourceNotFoundException("Resource not found"));

       return new ResponseEntity<>(mapper.map(post,PostDto.class),HttpStatus.OK);
    }

    @Override
    @SneakyThrows(ResourceNotFoundException.class)
    public ResponseEntity<PostDto> updatePostById(long id, PostDto updatePost) {
       PostEntity post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Resource not found"));

       post.setContent(updatePost.getContent());
       post.setDescription(updatePost.getDescription());
       post.setTitle(updatePost.getTitle());

       PostEntity updatedPost = postRepository.save(post);
       return ResponseEntity.ok(mapper.map(updatedPost,PostDto.class));
    }

    @Override
    @SneakyThrows(ResourceNotFoundException.class)
    public ResponseEntity<PostDto> deletePostById(long id) {
      PostEntity postEntity =  postRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException("ResourceNotFound"));
       postRepository.deleteById(id);

       return new ResponseEntity<>(mapper.map(postEntity,PostDto.class),HttpStatus.OK);
    }
}
