package cn.afterturn.gen.core.util;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import cn.afterturn.gen.core.exception.GunsException;
import cn.afterturn.gen.core.exception.GunsExceptionEnum;

/**
 * 渲染工具类
 *
 * @author fengshuonan
 * @date 2017-08-25 14:13
 */
public class RenderUtil {

    /**
     * 渲染json对象
     */
    public static void renderJson(HttpServletResponse response, Object jsonObject) {
        try {
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(jsonObject));
        } catch (IOException e) {
            throw new GunsException(GunsExceptionEnum.WRITE_ERROR);
        }
    }
}
