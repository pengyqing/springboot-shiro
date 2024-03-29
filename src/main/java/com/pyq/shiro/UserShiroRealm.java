package com.pyq.shiro;

import com.pyq.entity.Resources;
import com.pyq.entity.User;
import com.pyq.service.ResourcesService;
import com.pyq.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.ByteSource;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 *
 * @author: peng
 * @date: 2018-12-7 007 14:11:51
 */

@Slf4j
public class UserShiroRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    @Resource
    private ResourcesService resourcesService;

    @Autowired
    private RedisSessionDAO redisSessionDAO;

    {
        super.setName("userShiroRealm");
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        User user = (User) SecurityUtils.getSubject().getPrincipal();

        Map<String,Object> map = new HashMap<String,Object>(1);
        map.put("userid",user.getId());
        List<Resources> resourcesList = resourcesService.loadUserResources(map);

        // 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        for(Resources resources: resourcesList){
            info.addStringPermission(resources.getResUrl());
        }

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户的输入的账号.
        String username = (String)token.getPrincipal();

        User user = userService.selectByUsername(username);

        if(user==null) throw new UnknownAccountException();

        if (0==user.getEnable()) {
            throw new LockedAccountException(); // 帐号禁用
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user, //用户
                user.getPassword(), //密码
                ByteSource.Util.bytes(username),
                getName()  //realm name
        );

        // 当验证都通过后，把用户信息放在session里
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("userSession", user);
        session.setAttribute("userSessionId", user.getId());
        return authenticationInfo;
    }

    /**
     * 根据userId 清除当前session存在的用户的权限缓存
     * @param userIds 已经修改了权限的userId
     */
    public void clearUserAuthByUserId(List<Integer> userIds){

        if(null == userIds || userIds.size() == 0)	return ;

        //获取所有session
        Collection<Session> sessions = redisSessionDAO.getActiveSessions();

        //定义返回
        List<SimplePrincipalCollection> list = new ArrayList<SimplePrincipalCollection>();

        for (Session session:sessions){
            //获取session登录信息。
            Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);

            if(null != obj && obj instanceof SimplePrincipalCollection){
                //强转
                SimplePrincipalCollection spc = (SimplePrincipalCollection) obj;
                //判断用户，匹配用户ID。
                obj = spc.getPrimaryPrincipal();

                if(null != obj && obj instanceof User){

                    User user = (User) obj;

                    log.info("user:"+user);

                    //比较用户ID，符合即加入集合
                    if(null != user && userIds.contains(user.getId())){
                        list.add(spc);
                    }

                }

            }

        }

        RealmSecurityManager securityManager = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        UserShiroRealm realm = (UserShiroRealm) securityManager.getRealms().iterator().next();

        for (SimplePrincipalCollection simplePrincipalCollection : list) {
            realm.clearCachedAuthorizationInfo(simplePrincipalCollection);
        }

    }

}
