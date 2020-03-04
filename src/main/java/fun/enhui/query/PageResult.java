package fun.enhui.query;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页查询结果对象
 */
@SuppressWarnings("all")
@Getter
public class PageResult {

    private List listData;   //当前页的结果集数据：查询
    private Integer totalCount;  //总数据条数

    private Integer currentPage = 1;   //当前页码
    private Integer pageSize = 10;     //每页显示条数

    private Integer prevPage;   //上一页previous
    private Integer nextPage;   //下一页next
    private Integer totalPage;    //总页数total

    public PageResult(List  listData, Integer totalCount, Integer currentPage, Integer pageSize) {
        this.currentPage = currentPage;
        this.listData = listData;
        this.totalCount = totalCount;
        this.pageSize = pageSize;

        /* ---------------------------------- */
        //总条数%每页条数     计算出总页数为   总条数/每页数  或  总条数/每页数+1
        this.totalPage = this.totalCount % this.pageSize == 0 ?
                this.totalCount/this.pageSize : this.totalCount/this.pageSize+1;
        totalPage = totalPage==0?1:totalPage;
        this.prevPage = this.currentPage -1 >= 1 ? this.currentPage - 1 : 1;
        this.nextPage = this.currentPage +1 <= this.totalCount ? this.currentPage+1:this.totalPage;
    }

    //如果总数据条数为0  返回一个空
    public static PageResult empty(Integer pageSize){
        return new PageResult(new ArrayList<>(),0,1,pageSize);
    }














}
