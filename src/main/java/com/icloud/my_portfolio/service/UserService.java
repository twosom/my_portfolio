package com.icloud.my_portfolio.service;

import com.icloud.my_portfolio.domain.Role;
import com.icloud.my_portfolio.domain.User;
import com.icloud.my_portfolio.exception.UserEmailDuplicateException;
import com.icloud.my_portfolio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailSender mailSender;

    @Transactional
    public User join(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        //==중복 검사==//
        List<User> findByEmail = userRepository.findByEmail(user.getEmail());
        if (findByEmail.size() > 0) {
            throw new UserEmailDuplicateException(user.getEmail() + "은 이미 있는 이메일입니다.");
        }
        user.setPassword(encodedPassword);
        /*User활성화*/
        user.setEnabled(true);
        user.setRole(Role.USER);
        userRepository.save(user);
        sendMailMessage(user);
        return user;
    }


    public List<User> findOneByName(String username) {
        return userRepository.findByName(username);
    }


    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByEmail(String email) {
        List<User> byEmail = userRepository.findByEmail(email);
        return byEmail.get(0);
    }



    private void sendMailMessage(User user) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("if0rever@naver.com");
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject(user.getUsername() + "님의 가입을 축하드립니다.");
        mailMessage.setText(user.getUsername() + "님의 가입을 진심으로 축하드립니다.\n 현재 " + user.getUsername() + "님의 등급은 " +
                user.getRole() + "입니다.");
        mailSender.send(mailMessage);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .get(0);
        UserDetails userDetails = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(user.getRole());
                return authorities;
            }

            @Override
            public String getPassword() {
                return user.getPassword();
            }

            @Override
            public String getUsername() {
                return user.getUsername();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return user.getEnabled();
            }
        };

        return userDetails;
    }
}
