package com.icloud.my_portfolio.controller;


import com.icloud.my_portfolio.domain.Board;
import com.icloud.my_portfolio.domain.User;
import com.icloud.my_portfolio.repository.BoardRepository;
import com.icloud.my_portfolio.service.BoardService;
import com.icloud.my_portfolio.service.UserService;
import com.icloud.my_portfolio.validator.BoardValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final BoardValidator validator;
    private final UserService userService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Board> boards = boardService.findAll();

        model.addAttribute("boards", boards);
        return "board/list";
    }


    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id) {
        if (id == null) {
            model.addAttribute("board", new Board());
        } else {
            model.addAttribute("board", boardService.findOne(id));
        }
        return "board/form";
    }

    @PostMapping("/form")                                               //파라미터에 Authentication 을 담아주면 사용자 정보가 날라온다.
    public String postForm(@Valid Board board, BindingResult bindingResult, Authentication authentication) {
        validator.validate(board, bindingResult);
        if (bindingResult.hasErrors()) {
            return "board/form";
        }
        //==스프링 시큐리티 활용하여 사용자 이름 가져오기==//
        String username = authentication.getName();

        boardService.save(username, board);

        return "redirect:/board/list";
    }
}
