package com.icloud.my_portfolio.service;

import com.icloud.my_portfolio.domain.Board;
import com.icloud.my_portfolio.domain.User;
import com.icloud.my_portfolio.repository.BoardRepository;
import com.icloud.my_portfolio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public Board save(Board board) {
        boardRepository.save(board);
        return board;
    }

    public Board findOne(Long id) {
        return boardRepository.findById(id);
    }

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    public List<Board> findOneByTitle(String title) {
        return boardRepository.findByTitle(title);
    }

    public List<Board> findOneByTitleOrContent(String title, String content) {
        return boardRepository.findByTitleOrContent(title, content);
    }


    @Transactional
    public Board save(String username, Board board) {
        List<User> findUser = userRepository.findByName(username);
        User user = findUser.get(0);
        board.addUser(user);
        return boardRepository.save(board);
    }
}
