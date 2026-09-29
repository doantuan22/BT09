package com.example.bai_tap_09_bai_tap.controller;
import com.example.bai_tap_09_bai_tap.dto.UserDTO;
import com.example.bai_tap_09_bai_tap.repository.RoleRepository;
import com.example.bai_tap_09_bai_tap.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller @RequestMapping("/users")
public class UserController {
 private final UserService users;private final RoleRepository roles;
 public UserController(UserService users,RoleRepository roles){this.users=users;this.roles=roles;}
 @GetMapping public String list(@RequestParam(defaultValue="") String keyword,@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="10") int size,Model model){Page<UserDTO> result=users.search(keyword,page,size);model.addAttribute("users",result);model.addAttribute("keyword",keyword);model.addAttribute("size",size);return "users/list";}
 @GetMapping("/create") public String create(Model model){UserDTO dto=new UserDTO();dto.setEnabled(true);dto.setRoleName("ROLE_USER");model.addAttribute("userDTO",dto);formOptions(model,"create");return "users/form";}
 @PostMapping("/create") public String create(@Valid @ModelAttribute("userDTO") UserDTO dto,BindingResult result,Model model,RedirectAttributes redirect){if(result.hasErrors()){formOptions(model,"create");return "users/form";}try{users.save(dto,null);redirect.addFlashAttribute("success","Tạo user thành công. Mật khẩu mặc định: 123456");return "redirect:/users";}catch(IllegalArgumentException ex){result.reject("user.error",ex.getMessage());formOptions(model,"create");return "users/form";}}
 @GetMapping("/edit/{id}") public String edit(@PathVariable Long id,Model model){model.addAttribute("userDTO",users.findById(id));model.addAttribute("mode","edit");formOptions(model,"edit");return "users/form";}
 @PostMapping("/edit/{id}") public String edit(@PathVariable Long id,@Valid @ModelAttribute("userDTO") UserDTO dto,BindingResult result,@RequestParam(required=false) String password,Model model,RedirectAttributes redirect){if(result.hasErrors()){formOptions(model,"edit");return "users/form";}dto.setId(id);try{users.save(dto,password);redirect.addFlashAttribute("success","Cập nhật user thành công.");return "redirect:/users";}catch(IllegalArgumentException ex){result.reject("user.error",ex.getMessage());formOptions(model,"edit");return "users/form";}}
 @GetMapping("/detail/{id}") public String detail(@PathVariable Long id,Model model){model.addAttribute("user",users.findById(id));return "users/detail";}
 @PostMapping("/delete/{id}") public String delete(@PathVariable Long id,RedirectAttributes redirect){users.delete(id);redirect.addFlashAttribute("success","Xóa user thành công.");return "redirect:/users";}
 private void formOptions(Model model,String mode){model.addAttribute("roles",roles.findAll());model.addAttribute("mode",mode);}
}
