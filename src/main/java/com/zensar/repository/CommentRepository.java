package com.zensar.repository;

import com.zensar.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface CommentRepository extends JpaRepository<CommentEntity,Long> {


    List<CommentEntity>findByPostId(long postId);

}
