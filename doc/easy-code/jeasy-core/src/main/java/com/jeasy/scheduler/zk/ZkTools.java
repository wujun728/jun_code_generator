package com.jeasy.scheduler.zk;

import com.jeasy.common.Func;
import com.jeasy.common.charset.CharsetKit;
import com.jeasy.common.str.StrKit;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * zk工具类
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
@Data
public class ZkTools {

    public static void createPath(final ZooKeeper zk, final String path, final CreateMode createMode, final List<ACL> acl) throws Exception {
        String[] list = path.split(StrKit.S_SLASH);
        StringBuilder zkPath = new StringBuilder();
        for (String str : list) {
            if (StrKit.isNotEmpty(str)) {
                zkPath.append(StrKit.S_SLASH).append(str);
                if (Func.isEmpty(zk.exists(zkPath.toString(), false))) {
                    zk.create(zkPath.toString(), null, acl, createMode);
                }
            }
        }
    }

    public static void printTree(final ZooKeeper zk, final String path, final Writer writer, final String lineSplitChar) throws Exception {
        String[] list = getTree(zk, path);
        Stat stat = new Stat();
        for (String name : list) {
            byte[] value = zk.getData(name, false, stat);
            if (Func.isEmpty(value)) {
                writer.write(name + lineSplitChar);
            } else {
                writer.write(name + "[v." + stat.getVersion() + "]" + "[" + new String(value, CharsetKit.DEFAULT_CHARSET) + "]" + lineSplitChar);
            }
        }
    }

    public static void deleteTree(final ZooKeeper zk, final String path) throws Exception {
        String[] list = getTree(zk, path);
        for (int i = list.length - 1; i >= 0; i--) {
            zk.delete(list[i], -1);
        }
    }

    private static String[] getTree(final ZooKeeper zk, final String path) throws Exception {
        if (Func.isEmpty(zk.exists(path, false))) {
            return new String[0];
        }
        List<String> dealList = new ArrayList<>();
        dealList.add(path);
        int index = 0;
        while (index < dealList.size()) {
            String tempPath = dealList.get(index);
            List<String> children = zk.getChildren(tempPath, false);
            if (!tempPath.equalsIgnoreCase(StrKit.S_SLASH)) {
                tempPath = tempPath + StrKit.S_SLASH;
            }
            Collections.sort(children);
            for (int i = children.size() - 1; i >= 0; i--) {
                dealList.add(index + 1, tempPath + children.get(i));
            }
            index++;
        }
        return dealList.toArray(new String[dealList.size()]);
    }
}
