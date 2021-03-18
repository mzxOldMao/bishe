package mao.bishe.api.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureGenerationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mao.bishe.api.utils.JWTUtil;
import mao.bishe.api.utils.SpringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        StringRedisTemplate stringRedisTemplate = (StringRedisTemplate) SpringUtil.getBean("stringRedisTemplate");
        Map<String, Object> map = new HashMap<>();
        /*Subject subject = SecurityUtils.getSubject();
        String principal = (String) subject.getPrincipal();
        System.out.println(subject.getPrincipal());
        System.out.println("LoginInterceptorPrincipal："+principal);*/
        String userName = request.getHeader("userName");
        String redisToken = (String) stringRedisTemplate.opsForValue().get("TOKEN_"+userName);
        response.setContentType("application/json;charset=UTF-8");
        if (redisToken!=null){
            String token = request.getHeader("ApiAuthorization");
            //System.out.println("LoginInterceptorToken："+token);
            try{
                if (redisToken.equals(token)){
                    JWTUtil.parseToken(token);
                    map.put("msg","有效token");
                    return true;
                }
            }catch (SignatureGenerationException e){
                e.printStackTrace();
                map.put("msg","无效签名");
            }
            catch (TokenExpiredException e){
                e.printStackTrace();
                map.put("msg","token过期");
            }catch (AlgorithmMismatchException e){
                e.printStackTrace();
                map.put("msg","token算法错误");
            }catch (Exception e){
                e.printStackTrace();
                map.put("msg","token无效");
            }
            map.put("message","请检查是否携带token或重新登录");
            String s = new ObjectMapper().writeValueAsString(map);
            response.getWriter().println(s);
            return false;
        }else{
            map.put("msg","token已过期，请重新登录");
            String s = new ObjectMapper().writeValueAsString(map);
            //response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(s);
            return false;
        }
    }
}
