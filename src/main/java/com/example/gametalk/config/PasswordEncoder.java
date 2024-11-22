package com.example.gametalk.config;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {

    /**
     * 비밀번호 암호화
     * @param rawPassword (String 타입의 패스워드)
     * @return 암호화 하여 반환
     */
    public String encode(String rawPassword) {
        return BCrypt.withDefaults().hashToString(BCrypt.MIN_COST, rawPassword.toCharArray());
    }

    /**
     * 비밀번호 매치 여부
     * @param rawPassword (String 타입의 패스워드)
     * @param encodedPassword (암호화된 비밀번호)
     * @return rawPassword 와 encodedPassword 가 일치하면 true 틀리면 false
     */
    public boolean matches(String rawPassword, String encodedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword);
        return result.verified;
    }
}
