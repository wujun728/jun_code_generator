package com.jeasy.common.template;

import com.jeasy.common.charset.CharsetKit;
import com.jeasy.common.io.IoKit;
import com.jeasy.exception.KitException;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.*;
import java.util.Map;

/**
 * @author taomk
 * @version 1.0
 * @since 2014/10/19 22:38
 */
@Slf4j
public final class TemplateKit {

    private TemplateKit() {
    }

    /**
     * @param templatePath 模板路径
     * @param templateName 模板名称
     * @param encoding     编码方式
     * @param context      模型对象
     * @param targetPath   目标路径
     * @param targetName   目标文件
     * @return
     */
    public static boolean executeFreemarker(String templatePath, String templateName, String encoding, Map context, String targetPath, String targetName) {
        OutputStream os = null;
        Writer out = null;
        try {
            /**
             * 创建Configuration对象
             */
            Configuration config = new Configuration();
            /**
             * 指定模板路径
             */
            File file = new File(templatePath);
            /**
             * 设置要解析的模板所在的目录，并加载模板文件
             */
            config.setDirectoryForTemplateLoading(file);
            /**
             * 设置包装器，并将对象包装为数据模型
             */
            config.setObjectWrapper(new DefaultObjectWrapper());
            /**
             * 获取模板,并设置编码方式，这个编码必须要与页面中的编码格式一致
             */
            Template template = config.getTemplate(templateName, encoding);
            /**
             * 获取输出目标文件夹
             */
            File targetDir = new File(targetPath);
            if (!targetDir.exists() && targetDir.mkdirs()) {
                log.debug(targetDir.getName() + " mkdirs success ");
            }
            /**
             * 获取输出目标文件
             */
            File targetFile = new File(targetPath + File.separator + targetName);
            if (!targetFile.exists() && targetFile.createNewFile()) {
                log.debug(targetFile.getName() + " createNewFile success ");
            }
            os = new FileOutputStream(targetFile);
            /**
             * 合并数据模型与模板
             */
            out = new OutputStreamWriter(os, CharsetKit.DEFAULT_CHARSET);
            template.process(context, out);
            out.flush();
            return true;
        } catch (Exception e) {
            throw new KitException(e);
        } finally {
            IoKit.close(out, os);
        }
    }

    /**
     * @param templatePath 模板路径
     * @param templateName 模板名称
     * @param encoding     编码方式
     * @param context      模型对象
     * @param targetPath   目标路径
     * @param targetName   目标文件
     * @return
     */
    public static boolean executeVelocity(String templatePath, String templateName, String encoding, Map context, String targetPath, String targetName) {
        OutputStream os = null;
        Writer out = null;
        try {
            Velocity.init();
            VelocityContext velocityContext = new VelocityContext(context);
            org.apache.velocity.Template template = Velocity.getTemplate(templatePath + File.separator + templateName);

            /**
             * 获取输出目标文件夹
             */
            File targetDir = new File(targetPath);
            if (!targetDir.exists() && targetDir.mkdirs()) {
                log.debug(targetDir.getName() + " mkdirs success " );
            }
            /**
             * 获取输出目标文件
             */
            File targetFile = new File(targetPath + File.separator + targetName);
            if (!targetFile.exists() && targetFile.createNewFile()) {
                log.debug(targetFile.getName() + " createNewFile success ");
            }
            os = new FileOutputStream(targetFile);
            out = new OutputStreamWriter(os, CharsetKit.DEFAULT_CHARSET);

            template.merge(velocityContext, out);
            out.flush();
            return true;
        } catch (IOException e) {
            throw new KitException(e);
        } finally {
            IoKit.close(out, os);
        }
    }
}
