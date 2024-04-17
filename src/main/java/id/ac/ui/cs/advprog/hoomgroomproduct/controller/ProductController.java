package id.ac.ui.cs.advprog.hoomgroomproduct.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("product/")
public class ProductController {
    @GetMapping("")
    public String homePage() {
        return "Hello, world!";
    }
}