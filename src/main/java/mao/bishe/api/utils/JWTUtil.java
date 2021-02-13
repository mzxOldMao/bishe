package mao.bishe.api.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import mao.bishe.api.model.form.LoginForm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;

import java.io.UnsupportedEncodingException;
import java.util.Date;


/**
 * JWT 加密解密
 */
public class JWTUtil {

  //密钥
  private static final String SECRET = "bishe_secret";

  //多久过期（毫秒）
  private static final long EXPIRE_TIME = 86400000;

  // JWT
  private JWTUtil() {
  }

  public static String generateToken(LoginForm loginForm) {
    try {
      Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
      
      Algorithm algorithm = Algorithm.HMAC256(SECRET);
      
      
      // 附带user name信息
      return String.format("Bearer %s", JWT.create()
              .withClaim("name", loginForm.getUsername())
              .withExpiresAt(date)
              .sign(algorithm));
    } catch (UnsupportedEncodingException e) {
      return null;
    }
  }

  public static void parseToken(String token) {
    try {
      token = token.substring(7);
      Algorithm algorithm = Algorithm.HMAC256(SECRET);
      JWTVerifier verifier = JWT.require(algorithm).build();
      DecodedJWT jwt = verifier.verify(token);
    } catch (TokenExpiredException e) {
      throw e;
    } catch (Exception e) {
      throw new AuthenticationException(e);
    }
  }
}
