package com.wymm.common.meta.vo;

import com.wymm.common.meta.core.MetaType;
import com.wymm.common.meta.annotation.MetaField;
import lombok.Data;

@Data
public class BookVO {
    
    @MetaField(type = MetaType.BOOK_TYPE_META, target = "bookTypeText")
    private String bookType;
    
    private String bookTypeText;

}
