package com.chpark.msa.repository;

import com.chpark.msa.domain.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Choen-hee Park
 * User : chpark
 * Date : 2021/03/11
 * Time : 3:04 PM
 */

public interface PostsRepository extends JpaRepository<Posts, Long> {
}
