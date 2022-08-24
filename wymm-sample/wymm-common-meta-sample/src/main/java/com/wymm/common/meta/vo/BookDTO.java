package com.wymm.common.meta.vo;

import com.wymm.common.meta.annotation.MetaField;
import com.wymm.common.meta.core.MetaType;
import lombok.Data;

@Data
public class BookDTO {
    
    @MetaField(type = MetaType.BOOK_TYPE_META, target = "bookTypeText")
    private String bookType;
    
    private String bookTypeText;
    
    private BookVO vo;
    
    private Integer price;
    
}
