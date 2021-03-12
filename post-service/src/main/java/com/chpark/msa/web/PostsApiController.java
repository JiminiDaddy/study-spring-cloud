package com.chpark.msa.web;

import com.chpark.msa.domain.Posts;
import com.chpark.msa.service.PostsService;
import com.chpark.msa.web.dto.PostsResponseDto;
import com.chpark.msa.web.dto.PostsSaveRequestDto;
import com.chpark.msa.web.dto.PostsUpdateRequestDto;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;
import java.util.List;

/**
 * Created by Choen-hee Park
 * User : chpark
 * Date : 2021/03/11
 * Time : 2:47 PM
 */

@RequiredArgsConstructor
@RestController
public class PostsApiController {
    private final PostsService postsService;

    @PostMapping
    public PostsResponseDto save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    @GetMapping("/{id}")
    public PostsResponseDto find(@PathVariable(name = "id") Long id) {
        return postsService.find(id);
    }

    @GetMapping
    public List<PostsResponseDto> findAll() {
        return postsService.findAll();
    }

    @PutMapping("/{id}")
    public PostsResponseDto update(@PathVariable(name = "id") Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public Long delete(@PathVariable(name = "id") Long id) {
        return postsService.delete(id);
    }
}

