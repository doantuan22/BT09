package com.example.bai_tap_09_bai_tap.controller;
import com.example.bai_tap_09_bai_tap.dto.*;
import com.example.bai_tap_09_bai_tap.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller
public class AuthController {
 private final AuthService auth;
 public AuthController(AuthService auth){this.auth=auth;}
 @GetMapping("/login") public String login(){return "auth/login";}
 @GetMapping("/register") public String register(Model model){model.addAttribute("registerDTO",new RegisterDTO());return "auth/register";}
 @PostMapping("/register") public String register(@Valid @ModelAttribute("registerDTO") RegisterDTO form,BindingResult binding,RedirectAttributes flash){if(binding.hasErrors())return "auth/register";try{auth.register(form);flash.addFlashAttribute("success","OTP đã được gửi đến email.");return "redirect:/verify-otp?email="+enc(form.getEmail());}catch(IllegalArgumentException ex){binding.reject("register.error",ex.getMessage());return "auth/register";}}
 @GetMapping("/verify-otp") public String verifyPage(@RequestParam(required=false) String email,Model model){VerifyOtpDTO form=new VerifyOtpDTO();form.setEmail(email);model.addAttribute("verifyOtpDTO",form);return "auth/verify-otp";}
 @PostMapping("/verify-otp") public String verify(@Valid @ModelAttribute("verifyOtpDTO") VerifyOtpDTO form,BindingResult binding,RedirectAttributes flash){if(binding.hasErrors())return "auth/verify-otp";try{auth.verifyRegistration(form.getEmail(),form.getOtp());flash.addFlashAttribute("success","Xác nhận thành công. Hãy đăng nhập.");return "redirect:/login";}catch(IllegalArgumentException ex){binding.reject("otp.error",ex.getMessage());return "auth/verify-otp";}}
 @PostMapping({"/resend-register-otp","/resend-otp"}) public String resend(@RequestParam String email,RedirectAttributes flash){try{auth.resendRegistrationOtp(email);flash.addFlashAttribute("success","Đã gửi lại OTP.");}catch(IllegalArgumentException ex){flash.addFlashAttribute("error",ex.getMessage());}return "redirect:/verify-otp?email="+enc(email);}
 @GetMapping("/forgot-password") public String forgot(Model model){model.addAttribute("forgotPasswordDTO",new ForgotPasswordDTO());return "auth/forgot-password";}
 @PostMapping("/forgot-password") public String forgot(@Valid @ModelAttribute("forgotPasswordDTO") ForgotPasswordDTO form,BindingResult binding,RedirectAttributes flash){if(binding.hasErrors())return "auth/forgot-password";try{auth.requestPasswordReset(form.getEmail());flash.addFlashAttribute("email",form.getEmail());flash.addFlashAttribute("success","OTP đã được gửi.");return "redirect:/reset-password";}catch(IllegalArgumentException ex){binding.reject("forgot.error",ex.getMessage());return "auth/forgot-password";}}
 @GetMapping("/reset-password") public String resetPage(Model model){ResetPasswordDTO form=new ResetPasswordDTO();Object email=model.asMap().get("email");if(email!=null)form.setEmail(email.toString());model.addAttribute("resetPasswordDTO",form);return "auth/reset-password";}
 @PostMapping("/reset-password") public String reset(@Valid @ModelAttribute("resetPasswordDTO") ResetPasswordDTO form,BindingResult binding,@RequestParam String otp,RedirectAttributes flash){if(!form.getPassword().equals(form.getConfirmPassword()))binding.reject("password.error","Mật khẩu xác nhận không đúng.");if(binding.hasErrors())return "auth/reset-password";form.setOtp(otp);try{auth.resetPassword(form);flash.addFlashAttribute("success","Đổi mật khẩu thành công.");return "redirect:/login";}catch(IllegalArgumentException ex){binding.reject("otp.error",ex.getMessage());return "auth/reset-password";}}
 private String enc(String value){return java.net.URLEncoder.encode(value,java.nio.charset.StandardCharsets.UTF_8);}
}
