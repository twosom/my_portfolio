package com.icloud.my_portfolio.controller;

import com.icloud.my_portfolio.domain.Board;
import com.icloud.my_portfolio.exception.BoardNotFoundException;
import com.icloud.my_portfolio.repository.BoardRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardApiControllerTest {

    @Autowired
    BoardRepository boardRepository;
    @Autowired
    EntityManager em;

    Board board;

    @BeforeEach
    public void setUp() {
        board = new Board();
        board.setTitle("제목");
        board.setContent("내용");
    }
    @Test
    public void repaceBoardTest() {

        //given
        boardRepository.save(board);

        //when
        em.flush();
        em.clear();

        Board findBoard = boardRepository.findById(board.getId());
        findBoard.setTitle("새로운 제목");
        findBoard.setContent("새로운 내용");
        Board savedBoard = boardRepository.save(findBoard);
        //then

        Assertions.assertThat(savedBoard.getId()).isEqualTo(board.getId());
    }


    @Test
    public void boardReplaceException() {
        long id = 124215L;
        Board byId = boardRepository.findById(id);
        Assertions.assertThat(byId).isNull();

        assertThrows(BoardNotFoundException.class, () -> {
            if (byId == null) {
                throw new BoardNotFoundException(id + " 번 째 게시글을 찾을 수 없습니다.");
            }
        });
    }

}