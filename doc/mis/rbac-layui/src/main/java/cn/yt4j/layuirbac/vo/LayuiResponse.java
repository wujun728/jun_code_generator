package cn.yt4j.layuirbac.vo;

import com.github.pagehelper.PageInfo;
import lombok.*;

/**
 *  其实这里本应该使用封装类
 *  但是考虑之后如果使用分布式ID，直接生成64位长度的Long类型，在返回到前端的时候转换成string类型
 *  可以使用jackson全局配置进行转换，这里的小long不会被转换
 * @author gyv12345@163.com
 */
@Getter
@Setter
@ToString(callSuper = true)
public class LayuiResponse<T> {

    private int code;

    private String msg;

    private long count;

    private T data;

    public static <T> LayuiResponse<T> ok(T data) {
        return restResult(0, "执行成功", 0, data);
    }

    public static  LayuiResponse ok(PageInfo data){
        return restResult(0,"查询成功",data.getTotal(),data.getList());
    }

    public static <T> LayuiResponse<T> failed(T data){
        return restResult(-1, "执行失败", 0, data);
    }

    public static <T> LayuiResponse<T> failed(String msg){
        return restResult(-1, msg, 0, null);
    }


    public static <T> LayuiResponse<T> restResult(int code, String msg, long count, T data) {
        LayuiResponse response = new LayuiResponse();
        response.setCode(code);
        response.setMsg(msg);
        response.setCount(count);
        response.setData(data);
        return response;
    }
}
