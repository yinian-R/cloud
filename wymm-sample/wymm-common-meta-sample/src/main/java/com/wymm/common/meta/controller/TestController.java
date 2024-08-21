package com.wymm.common.meta.controller;

import com.wymm.common.meta.annotation.MetaMapping;
import com.wymm.common.meta.vo.BookVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {
    
    @MetaMapping
    @GetMapping
    public BookVO test() {
        BookVO bookVO = new BookVO();
        bookVO.setBookType("1");
        return bookVO;
    }
    
    @MetaMapping
    @GetMapping("/int")
    public Integer integer() {
        return 1;
    }
    
    @MetaMapping
    @GetMapping("/int2")
    public int integer2() {
        return 1;
    }
    
    @MetaMapping
    @GetMapping("/list")
    public List<BookVO> list() {
        List<BookVO> list = new ArrayList<>();
        BookVO bookVO = new BookVO();
        bookVO.setBookType("1");
        list.add(bookVO);
        return list;
    }
    
    @MetaMapping
    @GetMapping("/intArray")
    public Integer[] integer1() {
        return new Integer[]{1,2};
    }
    
    @MetaMapping
    @GetMapping("/intArray2")
    public List<Integer> intArray2() {
        ArrayList<Integer> objects = new ArrayList<>();
        objects.add(1);
        return objects;
    }
    
}
