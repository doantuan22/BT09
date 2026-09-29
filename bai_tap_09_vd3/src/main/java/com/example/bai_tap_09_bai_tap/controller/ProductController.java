package com.example.bai_tap_09_bai_tap.controller;
import com.example.bai_tap_09_bai_tap.dto.ProductDTO;
import com.example.bai_tap_09_bai_tap.repository.UserRepository;
import com.example.bai_tap_09_bai_tap.security.CustomUserDetails;
import com.example.bai_tap_09_bai_tap.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller @RequestMapping("/products")
public class ProductController {
 private final ProductService products;private final UserRepository users;
 public ProductController(ProductService products,UserRepository users){this.products=products;this.users=users;}
 @GetMapping public String list(@RequestParam(defaultValue="") String keyword,@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="10") int size,@AuthenticationPrincipal CustomUserDetails actor,Model model){boolean admin=isAdmin(actor);Long owner=admin?null:actor.getId();Page<ProductDTO> result=products.search(keyword,page,size,owner);model.addAttribute("products",result);model.addAttribute("keyword",keyword);model.addAttribute("size",size);model.addAttribute("admin",admin);return "products/list";}
 @GetMapping("/create") public String create(@AuthenticationPrincipal CustomUserDetails actor,Model model){ProductDTO dto=new ProductDTO();dto.setUserId(actor.getId());model.addAttribute("productDTO",dto);model.addAttribute("mode","create");return "products/form";}
 @GetMapping("/edit/{id}") public String edit(@PathVariable Long id,@AuthenticationPrincipal CustomUserDetails actor,Model model){model.addAttribute("productDTO",products.find(id,actor.getId(),isAdmin(actor)));model.addAttribute("mode","edit");return "products/form";}
 @PostMapping("/create") public String create(@Valid @ModelAttribute("productDTO") ProductDTO dto,BindingResult binding,@RequestParam(required=false) MultipartFile image,@AuthenticationPrincipal CustomUserDetails actor,Model model,RedirectAttributes flash){if(binding.hasErrors()){model.addAttribute("mode","create");return "products/form";}try{products.save(dto,actor.getId(),isAdmin(actor),image);flash.addFlashAttribute("success","Tạo sản phẩm thành công.");return "redirect:/products";}catch(RuntimeException ex){binding.reject("product.error",ex.getMessage());model.addAttribute("mode","create");return "products/form";}}
 @PostMapping("/edit/{id}") public String edit(@PathVariable Long id,@Valid @ModelAttribute("productDTO") ProductDTO dto,BindingResult binding,@RequestParam(required=false) MultipartFile image,@AuthenticationPrincipal CustomUserDetails actor,Model model,RedirectAttributes flash){if(binding.hasErrors()){model.addAttribute("mode","edit");return "products/form";}dto.setId(id);try{products.save(dto,actor.getId(),isAdmin(actor),image);flash.addFlashAttribute("success","Cập nhật sản phẩm thành công.");return "redirect:/products";}catch(RuntimeException ex){binding.reject("product.error",ex.getMessage());model.addAttribute("mode","edit");return "products/form";}}
 @PostMapping("/delete/{id}") public String delete(@PathVariable Long id,@AuthenticationPrincipal CustomUserDetails actor,RedirectAttributes flash){products.delete(id,actor.getId(),isAdmin(actor));flash.addFlashAttribute("success","Xóa sản phẩm thành công.");return "redirect:/products";}
 private boolean isAdmin(CustomUserDetails actor){return actor.getAuthorities().stream().anyMatch(a->a.getAuthority().equals("ROLE_ADMIN"));}
}
