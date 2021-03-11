package com.chpark.msa.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by Choen-hee Park
 * User : chpark
 * Date : 2021/03/11
 * Time : 2:54 PM
 */

@NoArgsConstructor
@Getter
@Table(name = "posts")
@Entity
public class Posts extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 128, nullable = false)
    private String title;

    @Column(name = "contents", length = 1024 * 10, nullable = false)
    private String contents;

    @Column(name = "author", nullable = false)
    private String author;

    @Builder
    public Posts(String title, String contents, String author) {
        this.title = title;
        this.contents = contents;
        this.author = author;
    }

    //protected Posts() { }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
