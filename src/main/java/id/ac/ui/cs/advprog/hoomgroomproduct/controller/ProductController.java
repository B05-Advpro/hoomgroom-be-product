package id.ac.ui.cs.advprog.hoomgroomproduct.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("product/")
public class ProductController {
    @GetMapping("")
    public ModelAndView homePage() {
        // Fill with code of retrieving data from SupaBase
        String url = "https://b5-hoomgroom-fe.vercel.app/product";
        return new ModelAndView(new RedirectView(url));
    }

}