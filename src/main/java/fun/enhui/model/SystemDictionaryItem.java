package fun.enhui.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据字典明细
 */
@Setter
@Getter
public class SystemDictionaryItem {
    private Long id;
    private Long parentId;  //数据字典明细对应的分类id
    private String title;   //数据字典明细名称
    private int sequence;    //数据字典明细在该分类中的排序

    /**
     * 返回当前的json字符串
     */
    public String getJsonString(){
        Map<String,Object> json = new HashMap<>();
        json.put("id",id);
        json.put("parentId",parentId);
        json.put("sequence",sequence);
        json.put("title",title);
        return JSONObject.toJSONString(json);

    }
}
