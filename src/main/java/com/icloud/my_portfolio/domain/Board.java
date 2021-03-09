package com.icloud.my_portfolio.domain;



import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter @Setter
public class Board {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;


    @Size(min = 2, max = 30, message = "제목은 2~30 글자까지만 허용됩니다.")
    private String title;

    private String content;


}
