package com.llg.blog.web.admin;

import com.llg.blog.po.Blog;
import com.llg.blog.po.User;
import com.llg.blog.service.BlogService;
import com.llg.blog.service.TagService;
import com.llg.blog.service.TypeService;
import com.llg.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BlogController {
    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    //PageableDefault  define size of page
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size =3, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                        Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("types",typeService.listType());
//        model.addAttribute("types",null);

        //        model.addAttribute("page",null);
        model.addAttribute("page",blogService.listBlog(pageable,blog));

        return LIST;
    }
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size =3, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/blogs ::blogList";// return a segment in blogs   th:fragment="blogList"
    }

    @GetMapping("/blogs/input")
    public String input(Model model) {
        setTypeAndTag(model);
        model.addAttribute("blog", new Blog());
        return INPUT;
    }
    // model will transfer to front-end
    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        setTypeAndTag(model);
        Blog b = blogService.getBlog(id);
        b.init();
        model.addAttribute("blog", b);
        return INPUT;
    }


    private void setTypeAndTag(Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
    }

    @PostMapping("/blogs")
    public String post(Blog blog, HttpSession session, RedirectAttributes attributes){
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog blog1;
        if(blog.getId() == null){
            blog1 = blogService.saveBlog(blog);

        }
        else{
            blog1 = blogService.updateBlog(blog.getId(),blog);
        }

        if (blog1 == null ) {
            attributes.addFlashAttribute("message", "Publish Failed.");
        } else {
            attributes.addFlashAttribute("message", "Publish Success");
        }


        return REDIRECT_LIST;
    }
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message","Delete success.");
        return REDIRECT_LIST;



    }

}
