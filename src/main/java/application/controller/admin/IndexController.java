package application.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class IndexController
{
    @GetMapping
    public String index(ModelMap model)
    {
        model.addAttribute("key", "value");
        return "index";
    }
}
