package com.example.gametalk.utils;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class SessionUtils {
    private final HttpSession session;


    public String getLoginUserEmail() {
        String email = (String)session.getAttribute("sessionKey");

        if (email == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }
        return email;
    }

    public String checkAuthorize(String email) {
        String loginEmail = getLoginUserEmail();
        if (!email.equals(loginEmail)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "권한이 없습니다.");
        }
        return email;
    }

    public void reloadSession(String email) {
        session.setAttribute("sessionKey", email);
    }
}
