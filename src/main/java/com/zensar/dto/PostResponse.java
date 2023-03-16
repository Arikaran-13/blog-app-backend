package com.zensar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {

    private List<PostDto> posts;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private long totalPage;
    private boolean isLast;
}
