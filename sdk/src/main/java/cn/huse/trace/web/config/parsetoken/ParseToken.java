package cn.huse.trace.web.config.parsetoken;

import java.lang.annotation.*;

/**
 * @author: huanxi
 * @date: 2019-04-15 23:47
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParseToken {
}
