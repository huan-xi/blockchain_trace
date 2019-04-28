package cn.huse.trace.web.config.parsetoken;

import cn.huse.trace.web.common.auth.jwt.JwtUserTokenUtil;
import cn.huse.trace.web.entity.User;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Tokn 解析
 * 讲请求头中的token解析成User绑定到controller上
 *
 * @author: huanxi
 * @date: 2019-04-15 23:45
 */
public class ParseTokenResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        //如果有ParseToken则使用该解析器
        return methodParameter.hasParameterAnnotation(ParseToken.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        String[] tokens = nativeWebRequest.getHeaderValues("token");
        if (tokens != null && tokens.length > 0) {
            String token = tokens[0];
            return JwtUserTokenUtil.getUserFormToken(token, User.class).getJwtUserId();
        }
        throw new Exception("token error");
    }
}
