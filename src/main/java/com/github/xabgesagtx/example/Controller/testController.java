package com.github.xabgesagtx.example.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 页面controller
 */
@Controller
public class testController {
    @RequestMapping("scan")
    public  String hello (Model model, @RequestParam(value ="name",required = false,defaultValue = "小哥") String name){
        model.addAttribute("name",name);
        return "index";
    }

    @RequestMapping("download")
    public  String success (Model model, @RequestParam(value ="name",required = false,defaultValue = "小哥") String name){
        model.addAttribute("name",name);
        return "download";
    }
}
