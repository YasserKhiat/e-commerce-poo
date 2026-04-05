package com.yasser.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.yasser.ecommerce.entity.Product;
import com.yasser.ecommerce.service.FileStorageService;
import com.yasser.ecommerce.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final FileStorageService fileStorageService;

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.findAll());
        return "admin-products";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("formTitle", "Add Product");
        model.addAttribute("formAction", "/admin/products/save");
        return "admin-product-form";
    }

    @PostMapping("/save")
    public String saveProduct(@Valid @ModelAttribute("product") Product product,
                              BindingResult bindingResult,
                              MultipartFile imageFile,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("formTitle", product.getId() == null ? "Add Product" : "Edit Product");
            model.addAttribute("formAction", product.getId() == null ? "/admin/products/save" : "/admin/products/update/" + product.getId());
            return "admin-product-form";
        }

        String imageName = fileStorageService.storeProductImage(imageFile);
        if (imageName != null) {
            product.setImageName(imageName);
        }

        productService.save(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + id));
        model.addAttribute("product", product);
        model.addAttribute("formTitle", "Edit Product");
        model.addAttribute("formAction", "/admin/products/update/" + product.getId());
        return "admin-product-form";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id,
                                @Valid @ModelAttribute("product") Product product,
                                BindingResult bindingResult,
                                MultipartFile imageFile,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("formTitle", "Edit Product");
            model.addAttribute("formAction", "/admin/products/update/" + id);
            return "admin-product-form";
        }

        Product existingProduct = productService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + id));

        String imageName = fileStorageService.storeProductImage(imageFile);
        if (imageName != null) {
            product.setImageName(imageName);
        } else {
            product.setImageName(existingProduct.getImageName());
        }

        product.setId(id);
        productService.save(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return "redirect:/admin/products";
    }
}
