package com.jeasy;

/**
 * @author TaoBangren
 * @version 1.0
 * @since 2017/5/17 上午9:26
 */
public class DemoJavaConfigConsumer {
    public static void main(String[] args) {
        // add `javaconfig` to args
        String[] customArgs = new String[]{"javaconfig"};
        com.alibaba.dubbo.container.Main.main(customArgs);
    }
}
