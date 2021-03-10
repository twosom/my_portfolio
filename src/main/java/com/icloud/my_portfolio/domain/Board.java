package com.icloud.my_portfolio.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    public Board(@Size(min = 2, max = 30, message = "제목은 2~30 글자까지만 허용됩니다.") String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Size(min = 2, max = 30, message = "제목은 2~30 글자까지만 허용됩니다.")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void addUser(User user) {
        user.getBoards().add(this);
        setUser(user);
    }


}
