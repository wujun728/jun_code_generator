package com.jeasy.common.template;

import com.jeasy.common.str.StrKit;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.util.Map;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class VelocityKit {

    static {
        Velocity.init();
    }

    /**
     * 渲染模板内容.
     *
     * @param templateContent 模板内容.
     * @param context         变量Map.
     * @return the string
     */
    public static String renderTemplateContent(String templateContent, Map<String, ?> context) {
        VelocityContext velocityContext = new VelocityContext(context);
        StringWriter result = new StringWriter();
        Velocity.evaluate(velocityContext, result, StrKit.S_EMPTY, templateContent);
        return result.toString();
    }

    /**
     * 渲染模板文件.
     *
     * @param templateFilePName the template file p name
     * @param velocityEngine    velocityEngine, 需经过VelocityEngineFactory处理,
     *                          绑定Spring的ResourceLoader.
     * @param encoding          the encoding
     * @param context           变量Map.
     * @return the string
     */
    public static String renderFile(String templateFilePName, VelocityEngine velocityEngine, String encoding, Map<String, ?> context) {
        VelocityContext velocityContext = new VelocityContext(context);

        StringWriter result = new StringWriter();
        velocityEngine.mergeTemplate(templateFilePName, encoding, velocityContext, result);
        return result.toString();
    }
}
