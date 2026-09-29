package com.example.bai_tap_09_bai_tap.service;
import com.example.bai_tap_09_bai_tap.dto.UserDTO;
import com.example.bai_tap_09_bai_tap.entity.*;
import com.example.bai_tap_09_bai_tap.mapper.UserMapper;
import com.example.bai_tap_09_bai_tap.repository.*;
import com.example.bai_tap_09_bai_tap.service.CloudinaryService;
import jakarta.persistence.EntityNotFoundException;
import java.util.Locale;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service @Transactional
public class UserServiceImpl implements UserService {
 private final UserRepository users;private final RoleRepository roles;private final ProductRepository products;private final UserMapper mapper;private final PasswordEncoder encoder;private final CloudinaryService cloudinary;
 public UserServiceImpl(UserRepository users,RoleRepository roles,ProductRepository products,UserMapper mapper,PasswordEncoder encoder,CloudinaryService cloudinary){this.users=users;this.roles=roles;this.products=products;this.mapper=mapper;this.encoder=encoder;this.cloudinary=cloudinary;}
 @Transactional(readOnly=true) public Page<UserDTO> search(String q,int page,int size){String s=q==null?"":q.trim();Page<User> result=users.findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrFullNameContainingIgnoreCase(s,s,s,PageRequest.of(Math.max(0,page),Math.min(50,Math.max(1,size)),Sort.by("createdAt").descending()));return result.map(u->{UserDTO dto=mapper.toDto(u);dto.setProductCount(products.countByUserId(u.getId()));return dto;});}
 @Transactional(readOnly=true) public UserDTO findById(Long id){User u=users.findById(id).orElseThrow(()->new EntityNotFoundException("Không tìm thấy người dùng."));UserDTO dto=mapper.toDto(u);dto.setProductCount(products.countByUserId(id));return dto;}
 public UserDTO save(UserDTO dto,String password){User u=dto.getId()==null?new User():users.findById(dto.getId()).orElseThrow(()->new EntityNotFoundException("Không tìm thấy người dùng."));String username=dto.getUsername().trim();if(users.existsByUsernameIgnoreCaseAndIdNot(username,u.getId()==null?-1L:u.getId()))throw new IllegalArgumentException("Username đã tồn tại.");String email=dto.getEmail().trim().toLowerCase(Locale.ROOT);if(users.existsByEmailIgnoreCaseAndIdNot(email,u.getId()==null?-1L:u.getId()))throw new IllegalArgumentException("Email đã tồn tại.");u.setUsername(username);u.setEmail(email);u.setFullName(dto.getFullName().trim());u.setEnabled(dto.isEnabled());u.setRole(roles.findByNameIgnoreCase(dto.getRoleName()).orElseThrow(()->new IllegalArgumentException("Role không hợp lệ.")));if(u.getId()==null)u.setPassword(encoder.encode("123456"));else if(password!=null&&!password.isBlank())u.setPassword(encoder.encode(password));return mapper.toDto(users.save(u));}
 public void delete(Long id){if(!users.existsById(id))throw new EntityNotFoundException("Không tìm thấy người dùng.");products.findAllByUserId(id).forEach(p->{String image=p.getImageUrl();if(image!=null&&image.contains("|"))cloudinary.delete(image.substring(image.indexOf('|')+1));});users.deleteById(id);}
 @Transactional(readOnly=true) public long count(){return users.count();}
 @Transactional(readOnly=true) public long countProducts(Long userId){return products.countByUserId(userId);}
}
