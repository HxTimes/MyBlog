package com.time.web.admin;

import com.time.pojo.Type;
import com.time.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("types")
    public String types(Pageable pageable,
                        Model model){
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }

    @GetMapping("types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }

    @GetMapping("types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";
    }

    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        Type type1 = typeService.getByName(type.getName());
        if(type1!=null){
            bindingResult.rejectValue("name","nameError","分类不可重复添加");
        }
        if(bindingResult.hasErrors()){
            return "admin/types-input";
        }
        Type t = typeService.saveType(type);
        if(t==null){
            redirectAttributes.addFlashAttribute("message","添加失败");
        }
        else{
            redirectAttributes.addFlashAttribute("message","添加成功");
        }
        return "redirect:/admin/types";
    }

    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type,
                           BindingResult bindingResult,
                           @PathVariable Long id,
                           RedirectAttributes redirectAttributes){
        Type type1 = typeService.getByName(type.getName());
        if(type1!=null){
            bindingResult.rejectValue("name","nameError","分类不可重复添加");
        }
        if(bindingResult.hasErrors()){
            return "admin/types-input";
        }
        Type t = typeService.updateType(id, type);
        if(t==null){
            redirectAttributes.addFlashAttribute("message","更新失败");
        }
        else{
            redirectAttributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes redirectAttributes){
        typeService.deleteType(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }
}
