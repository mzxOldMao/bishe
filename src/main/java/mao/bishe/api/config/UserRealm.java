package mao.bishe.api.config;


import mao.bishe.api.utils.SpringUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.data.redis.core.StringRedisTemplate;

public class UserRealm extends AuthorizingRealm {
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        return null;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了认证方法doGetAuthenticationInfo");
       // System.out.println(token.toString());
        StringRedisTemplate stringRedisTemplate = (StringRedisTemplate) SpringUtil.getBean("stringRedisTemplate");
        String principal = (String) token.getPrincipal();
        if (principal!=null){
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(principal, stringRedisTemplate.opsForValue().get(principal), this.getName());
            stringRedisTemplate.delete(principal);
            return simpleAuthenticationInfo;
        }
        return null;
    }
}
