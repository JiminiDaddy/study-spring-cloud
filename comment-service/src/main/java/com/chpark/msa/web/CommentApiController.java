package com.chpark.msa.web;

import com.chpark.msa.service.CommentService;
import com.chpark.msa.web.dto.CommentResponseDto;
import com.chpark.msa.web.dto.CommentSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Created by Choen-hee Park
 * User : chpark
 * Date : 2021/03/11
 * Time : 11:05 PM
 */

@RequiredArgsConstructor
@RestController
public class CommentApiController {
    private final CommentService commentService;

    @PostMapping("/{postId}")
    public CommentResponseDto save(@PathVariable(name = "postId") Long postId, @RequestBody CommentSaveRequestDto requestDto) {
        return commentService.save(postId, requestDto);
    }

    @GetMapping("/{postId}")
    public List<CommentResponseDto> findByPostId(@PathVariable(name = "postId") Long postId) {
        return commentService.findByPostId(postId);
    }
}
