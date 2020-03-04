package fun.enhui.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据字典分类
 */
@Getter
@Setter
public class SystemDictionary {
    private Long id;
    private String sn;   //数据字典分类编码
    private String title;   //数据字典分类名称

    /**
     * 返回当前的json字符串
     */
    public String getJsonString(){
        Map<String,Object> json = new HashMap<>();
        json.put("id",id);
        json.put("sn",sn);
        json.put("title",title);
        return JSONObject.toJSONString(json);

    }
}
