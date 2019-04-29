package cn.huse.trace.web.common.auth.verify;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * @author: huanxi
 * @date: 2019-04-16 00:29
 */
@Service
public class AuthenticatorImp implements Authenticator{
    ArrayList<String> notLogin=new ArrayList<>();

    public AuthenticatorImp(){
        notLogin.add("/user/token");
    }

    public boolean isLogin(String url) {
        //正则匹配
        return false;
    }
    @Override
    public boolean doVerify(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(request.getRequestURI());
        return true;
    }
}
