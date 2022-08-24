package com.wymm.common.dao.util;

import com.github.pagehelper.IPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * pagehelper 分页实现类
 *
 * @version 2021/01/26
 */
@ApiModel(value = "分页对象", description = "")
@Data
@Builder
public class SimplePage implements IPage {
    
    @ApiModelProperty(value = "页码", required = true)
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小为1")
    private Integer pageNum;
    
    @ApiModelProperty(value = "分页大小", required = true)
    @NotNull(message = "分页大小不能为空")
    @Min(value = 1, message = "分页大小最小为1")
    private Integer pageSize;
    
    @ApiModelProperty(value = "排序", hidden = true)
    private String orderBy;
    
    public SimplePage() {
    }
    
    public SimplePage(Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }
    
    public SimplePage(Integer pageNum, Integer pageSize, String orderBy) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.orderBy = orderBy;
    }
}
