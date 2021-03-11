package com.chpark.msa.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * Created by Choen-hee Park
 * User : chpark
 * Date : 2021/03/11
 * Time : 3:01 PM
 */

@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
abstract class BaseTimeEntity {
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;
}
