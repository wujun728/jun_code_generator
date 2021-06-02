package cn.afterturn.gen.core.beetl;

import org.beetl.ext.spring.BeetlGroupUtilConfiguration;

import cn.afterturn.gen.core.util.KaptchaUtil;
import cn.afterturn.gen.core.util.ToolUtil;

public class BeetlConfiguration extends BeetlGroupUtilConfiguration {

    @Override
    public void initOther() {

        groupTemplate.registerFunctionPackage("shiro", new ShiroExt());
        groupTemplate.registerFunctionPackage("tool", new ToolUtil());
        groupTemplate.registerFunctionPackage("kaptcha", new KaptchaUtil());

    }

}
