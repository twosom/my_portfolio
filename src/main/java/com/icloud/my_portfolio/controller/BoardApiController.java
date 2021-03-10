package com.icloud.my_portfolio.controller;


import com.icloud.my_portfolio.domain.Board;
import com.icloud.my_portfolio.exception.BoardNotFoundException;
import com.icloud.my_portfolio.repository.BoardRepository;
import com.icloud.my_portfolio.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardApiController {

    private final BoardService boardService;

    @GetMapping("/boards")
    public Result<Board> all(@RequestParam(name = "title", required = false, defaultValue = "") String title,
                             @RequestParam(name="content", required=false, defaultValue = "") String content) {

        if (StringUtils.isEmpty(title) && StringUtils.isEmpty(content)) {
            List<BoardDto> collect = boardService.findAll()
                    .stream()
                    .map(b -> new BoardDto(b.getId(), b.getTitle(), b.getContent()))
                    .collect(Collectors.toList());

            return new Result(collect.size(), collect);
        }
        List<BoardDto> collect = boardService.findOneByTitleOrContent(title, content)
                .stream()
                .map(b -> new BoardDto(b.getId(), b.getTitle(), b.getContent()))
                .collect(Collectors.toList());
        return new Result(collect.size(), collect);
    }


    @PostMapping("/boards")
    public Result<Board> newBoard(@RequestBody Board board) {
        Board save = boardService.save(board);
        BoardDto boardDto = new BoardDto(save);
        return new Result(boardDto);
    }


    @GetMapping("/boards/{id}")
    public Result<Board> one(@PathVariable Long id) {
        Board findBoard = boardService.findOne(id);
        boardValidate(id, findBoard);
        BoardDto boardDto = new BoardDto(findBoard);
        return new Result(boardDto);
    }

    @PutMapping("/boards/{id}")
    public Result<Board> replaceBoard(@RequestBody Board newBoard, @PathVariable Long id) {
        Board findBoard = boardService.findOne(id);
        boardValidate(id, findBoard);

        findBoard.setTitle(newBoard.getTitle());
        findBoard.setContent(newBoard.getContent());

        Board savedBoard = boardService.save(findBoard);
        BoardDto boardDto = new BoardDto(savedBoard);
        return new Result(boardDto);
    }


    private void boardValidate(@PathVariable Long id, Board findBoard) {
        if (findBoard == null) {
            throw new BoardNotFoundException(id + "번 게시글을 찾을 수 없습니다.");
        }
    }


    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;

        public Result(T data) {
            this.count = 1;
            this.data = data;
        }
    }

    @Data
    @AllArgsConstructor
    static class BoardDto {
        private Long id;
        private String title;
        private String content;

        public BoardDto(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
        }
    }
}


