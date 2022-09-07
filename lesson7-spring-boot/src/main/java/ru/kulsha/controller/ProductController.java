package ru.kulsha.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kulsha.persist.Product;
import ru.kulsha.persist.ProductRepository;

import javax.validation.Valid;
import java.util.Optional;


@Slf4j   //библиотека для логирования
@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping
    public String listPage(
            @RequestParam (required = false) String productNameFilter,
            @RequestParam (required = false) String emailFilter,
            Model model) {
        productNameFilter = productNameFilter == null || productNameFilter.isBlank() ? null : "%" + productNameFilter.trim() + "%";
        emailFilter = emailFilter == null || emailFilter.isBlank() ? null : "%" + emailFilter.trim() + "%";
        model.addAttribute("products", productRepository.productsByFilter(productNameFilter, emailFilter));
        return "product";
    }



//    @GetMapping
//    public String listPage(
//            @RequestParam Optional<String> productNameFilter, Model model) {
//        if (productNameFilter.isEmpty() || productNameFilter.get().isBlank()){
//            model.addAttribute("products", productRepository.findAll());
//        } else {
//            model.addAttribute("products", productRepository.findAllByProductNameLike("%" + productNameFilter.get() + "%"));
//        }
//        return "product";
//    }

    @GetMapping("/{id}")
    public String form(@PathVariable("id") long id, Model model) {
        model.addAttribute("product", productRepository.findById(id));
        return "product_form";
    }

    @GetMapping("/new")
    public String addNewProduct(Model model) {
        model.addAttribute("product", new Product(""));
        System.out.println("Product was added");
        return "product_form";

    }

    @DeleteMapping("{id}")
    public String deleteProductById(@PathVariable long id) {
        productRepository.deleteById(id);
        return "redirect:/product";
    }

    @PostMapping
    public String saveProduct(@Valid Product product, BindingResult bindingResult) { //valid  для работы с NotBlank
        if(bindingResult.hasErrors()){
            return "product_form";
        }
        if(!product.getPassword().equals(product.getMatchingPassword())){
            bindingResult.rejectValue("password", "Password is not matching");
            return "product_form";
        }
        productRepository.save(product);
        return "redirect:/product";
    }

    @PostMapping("/update")
    public String updateProduct(Product product) {
        productRepository.save(product);
        return "redirect:/product";
    }

}
