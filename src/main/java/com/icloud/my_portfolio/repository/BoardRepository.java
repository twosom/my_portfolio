package com.icloud.my_portfolio.repository;

import com.icloud.my_portfolio.domain.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private final EntityManager em;

    public List<Board> findAll() {
        return em.createQuery(
                "select b " +
                        "from Board b ", Board.class)
                .getResultList();
    }

    public Board save(Board board) {
        if (board.getId() == null) {
            em.persist(board);
        } else {
            em.merge(board);
        }
        return board;
    }

    public Board findById(Long id) {
        return em.find(Board.class, id);
    }


    public List<Board> findByTitle(String title) {
        return em.createQuery(
                "select b " +
                        "from Board b " +
                        "where b.title = :title", Board.class)
                .setParameter("title", title)
                .getResultList();
    }

    public List<Board> findByTitleOrContent(String title, String content) {
        return em.createQuery(
                "select b " +
                        "from Board b " +
                        "where b.title like :title or b.content like :content", Board.class)
                .setParameter("title", "%" + title + "%")
                .setParameter("content", "%" + content + "%")
                .getResultList();
    }

}
