package com.time.web;

import com.time.NotFoundException;
import com.time.service.BlogService;
import com.time.service.TagService;
import com.time.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Type;


@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 5) Pageable pageable, Model model){
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listTypeTop(3));
        model.addAttribute("tags",tagService.listTagTop(3));
        return "index";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model){
        model.addAttribute("blog",blogService.getAndConvert(id));
        return "blog";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 5) Pageable pageable, @RequestParam String query, Model model){
        model.addAttribute("page",blogService.listBlog(query,pageable));
        model.addAttribute("query",query);
        return "search";
    }

}
