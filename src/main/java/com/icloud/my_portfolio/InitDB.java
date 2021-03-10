package com.icloud.my_portfolio;


import com.icloud.my_portfolio.domain.Board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDB {

//    private final InitService initService;
//
//    @PostConstruct
//    public void init() {
//        initService.dbInit();
//    }
//
//
//    @Component
//    @RequiredArgsConstructor
//    @Transactional
//    static class InitService {
//        private final EntityManager em;
//
//
//        public void dbInit() {
//            for (int i = 1; i < 5000; i++) {
//                Board board = new Board("title" + i, "content" + i);
//                em.persist(board);
//            }
//
//        }
//    }
}

