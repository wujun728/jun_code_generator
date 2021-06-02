package cn.afterturn.gen.modular.system.warpper;

import java.util.List;
import java.util.Map;

import cn.afterturn.gen.common.constant.factory.ConstantFactory;
import cn.afterturn.gen.core.base.warpper.BaseControllerWarpper;

/**
 * 角色列表的包装类
 *
 * @author fengshuonan
 * @date 2017年2月19日10:59:02
 */
public class RoleWarpper extends BaseControllerWarpper {

    public RoleWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("pName", ConstantFactory.me().getSingleRoleName((Integer) map.get("pid")));
        map.put("deptName", ConstantFactory.me().getDeptName((Integer) map.get("deptid")));
    }

}
