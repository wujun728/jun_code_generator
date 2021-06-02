package cn.afterturn.gen.modular.system.warpper;

import java.util.Map;

import cn.afterturn.gen.common.constant.factory.ConstantFactory;
import cn.afterturn.gen.core.base.warpper.BaseControllerWarpper;
import cn.afterturn.gen.core.util.ToolUtil;

/**
 * 部门列表的包装
 *
 * @author fengshuonan
 * @date 2017年4月25日 18:10:31
 */
public class DeptWarpper extends BaseControllerWarpper {

    public DeptWarpper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {

        Integer pid = (Integer) map.get("pid");

        if (ToolUtil.isEmpty(pid) || pid.equals(0)) {
            map.put("pName", "--");
        } else {
            map.put("pName", ConstantFactory.me().getDeptName(pid));
        }
    }

}
