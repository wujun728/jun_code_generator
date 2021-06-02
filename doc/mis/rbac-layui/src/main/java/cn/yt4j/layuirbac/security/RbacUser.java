package cn.yt4j.layuirbac.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
/**
 * @author gyv12345@163.com
 */
public class RbacUser extends User {
    public RbacUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
