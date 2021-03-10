//package com.icloud.my_portfolio.controller;
//
//
//import com.icloud.my_portfolio.domain.Board;
//import com.icloud.my_portfolio.domain.User;
//import com.icloud.my_portfolio.exception.BoardNotFoundException;
//import com.icloud.my_portfolio.service.BoardService;
//import com.icloud.my_portfolio.service.UserService;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//import org.thymeleaf.util.StringUtils;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api")
//public class UserApiController {
//
//    private final UserService userService;
//
//    @GetMapping("/users")
//    public Result<Board> all() {
//        List<UserDto> collect = userService.findAll()
//                .stream().map(u -> new UserDto(u))
//                .collect(Collectors.toList());
//
//        return new Result(collect);
//    }
//
//
//    @PostMapping("/users")
//    public Result<Board> newBoard(@RequestBody User user) {
//        User save = userService.join(user);
//        UserDto userDto = new UserDto(save);
//        return new Result(userDto);
//    }
//
//
//    @GetMapping("/boards/{id}")
//    public Result<Board> one(@PathVariable Long id) {
//        Board findBoard = boardService.findOne(id);
//        boardValidate(id, findBoard);
//        BoardDto boardDto = new BoardDto(findBoard);
//        return new Result(boardDto);
//    }
//
//    @PutMapping("/boards/{id}")
//    public Result<Board> replaceBoard(@RequestBody Board newBoard, @PathVariable Long id) {
//        Board findBoard = boardService.findOne(id);
//        boardValidate(id, findBoard);
//
//        findBoard.setTitle(newBoard.getTitle());
//        findBoard.setContent(newBoard.getContent());
//
//        Board savedBoard = boardService.save(findBoard);
//        BoardDto boardDto = new BoardDto(savedBoard);
//        return new Result(boardDto);
//    }
//
//
//    private void boardValidate(@PathVariable Long id, Board findBoard) {
//        if (findBoard == null) {
//            throw new BoardNotFoundException(id + "번 게시글을 찾을 수 없습니다.");
//        }
//    }
//
//
//    @Data
//    @AllArgsConstructor
//    static class Result<T> {
//        private int count;
//        private T data;
//
//
//        public Result(List<UserDto> collect) {
//            this.count = collect.size();
//            this.data = (T) collect;
//        }
//
//        public Result(UserDto userDto) {
//            this.count = 1;
//            this.data = (T) userDto;
//        }
//    }
//
//    @Data
//    @AllArgsConstructor
//    static class UserDto {
//        private Long id;
//        private String email;
//        private String username;
//
//        public UserDto(User user) {
//            this.id = user.getId();
//            this.email = user.getEmail();
//            this.username = user.getUsername();
//        }
//    }
//}
//
//
