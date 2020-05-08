package com.time.web;

import com.time.pojo.Blog;
import com.time.pojo.Type;
import com.time.service.BlogService;
import com.time.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String type(@PageableDefault(size = 5) Pageable pageable, @PathVariable Long id, Model model){
        List<Type> types = typeService.listTypeTop(100);
        if(id==-1){
            id = types.get(0).getId();
        }
        Blog blog = new Blog();
        Type t = new Type();
        t.setId(id);
        blog.setType(t);
        model.addAttribute("types",types);
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        model.addAttribute("activeTypeId",id);
        return "types";
    }
}
