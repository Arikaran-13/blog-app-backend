package com.zensar.service;

import com.zensar.dto.PostDto;
import com.zensar.dto.PostResponse;
import org.springframework.http.ResponseEntity;
import java.util.*;
public interface PostService {

     ResponseEntity<PostDto>createPost(PostDto post);
     ResponseEntity<PostResponse>getAllPost(int pageNumber,int pageSize,String sortBy,String sortDir);

     ResponseEntity<PostDto>getPostById(long id);

    ResponseEntity<PostDto> updatePostById(long id , PostDto updatePost);

    ResponseEntity<PostDto> deletePostById(long id);
}
