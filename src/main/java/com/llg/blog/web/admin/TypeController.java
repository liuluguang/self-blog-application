package com.llg.blog.web.admin;

import com.llg.blog.po.Type;
import com.llg.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    private TypeService typeService;
    @GetMapping("/types")
    public String list(@PageableDefault(size=5, sort={"id"},direction = Sort.Direction.DESC)
                                   Pageable pageable, Model model){
        // spring boot packaged for paging
         model.addAttribute("page",typeService.listType(pageable));

        return "admin/types";

    }
    @GetMapping("/types/input")
    public String input(Model model){
        //add a type to model, to transfer to front-end
        model.addAttribute("type",new Type());
        return "admin/types-input";

    }

    //model will return to front-end   @PathVariable to get id from /{id}
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";



    }


    @GetMapping("/types/{id}/delete")
    public String delelte(@PathVariable Long id, RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","Delet success.");
        return "redirect:/admin/types";



    }


    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes){

        //check duplicate
        Type type2 = typeService.getTypeByName(type.getName());
        if(type2!=null){
            result.rejectValue("name","nameError","Name duplicate in this category. ");
        }

        if(result.hasErrors()){

            return "admin/types-input";
        }
        Type type1 = typeService.saveType(type);

        if(type1 == null){
            attributes.addFlashAttribute("message","Add Failed.");



        }else{
            attributes.addFlashAttribute("message","Add Success.");


        }

        return "redirect:/admin/types";



    }
    // type must follow by bindingresult
    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result, @PathVariable Long id, RedirectAttributes attributes){

        //check duplicate
        Type type2 = typeService.getTypeByName(type.getName());
        if(type2!=null){
            result.rejectValue("name","nameError","Name duplicate in this category. ");
        }

        if(result.hasErrors()){

            return "admin/types-input";
        }
        Type type1 = typeService.updateType(id, type);

        if(type1 == null){
            attributes.addFlashAttribute("message","Update Failed.");



        }else{
            attributes.addFlashAttribute("message","Update Success.");


        }

        return "redirect:/admin/types";



    }



}
