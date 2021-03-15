package com.chpark.msa.repository;

import com.chpark.msa.domain.Comment;
import com.chpark.msa.domain.CommentId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Choen-hee Park
 * User : chpark
 * Date : 2021/03/11
 * Time : 11:41 PM
 */

public interface CommentsRepository extends JpaRepository<Comment, CommentId> {
    List<Comment> findByCommentId_PostId(Long postId);
}
