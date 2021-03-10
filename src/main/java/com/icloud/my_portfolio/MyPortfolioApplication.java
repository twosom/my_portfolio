package com.icloud.my_portfolio;

import com.icloud.my_portfolio.domain.Board;
import com.icloud.my_portfolio.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@SpringBootApplication
@Component
public class MyPortfolioApplication {
	public static void main(String[] args) {
		SpringApplication.run(MyPortfolioApplication.class, args);

	}

	@Bean
	public MailSender mailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.naver.com");
		mailSender.setPort(587);
		mailSender.setUsername("if0rever");
		mailSender.setPassword("9ZPMLSPE3S26");
		return mailSender;
	}
}
