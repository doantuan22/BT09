package com.example.bai_tap_09_bai_tap.service;
import com.example.bai_tap_09_bai_tap.dto.ProductDTO;
import com.example.bai_tap_09_bai_tap.entity.*;
import com.example.bai_tap_09_bai_tap.mapper.ProductMapper;
import com.example.bai_tap_09_bai_tap.repository.*;
import jakarta.persistence.EntityNotFoundException;
import java.util.Objects;
import org.springframework.data.domain.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
@Service @Transactional
public class ProductServiceImpl implements ProductService {
 private final ProductRepository products;private final UserRepository users;private final ProductMapper mapper;private final CloudinaryService cloudinary;
 public ProductServiceImpl(ProductRepository products,UserRepository users,ProductMapper mapper,CloudinaryService cloudinary){this.products=products;this.users=users;this.mapper=mapper;this.cloudinary=cloudinary;}
 @Transactional(readOnly=true) public Page<ProductDTO> search(String q,int page,int size,Long ownerId){Pageable pageable=PageRequest.of(Math.max(0,page),Math.min(50,Math.max(1,size)),Sort.by("createdAt").descending());String s=q==null?"":q.trim();Page<Product> found=ownerId==null?products.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(s,s,pageable):products.searchByOwner(ownerId,s,pageable);return found.map(mapper::toDto);}
 @Transactional(readOnly=true) public ProductDTO find(Long id,Long actorId,boolean admin){Product p=products.findById(id).orElseThrow(()->new EntityNotFoundException("Không tìm thấy sản phẩm."));checkOwner(p,actorId,admin);return mapper.toDto(p);}
 public ProductDTO save(ProductDTO dto,Long actorId,boolean admin,MultipartFile image){Product p=dto.getId()==null?new Product():products.findById(dto.getId()).orElseThrow(()->new EntityNotFoundException("Không tìm thấy sản phẩm."));User owner;if(p.getId()!=null){checkOwner(p,actorId,admin);owner=p.getUser();}else{owner=users.findById(actorId).orElseThrow(()->new EntityNotFoundException("Không tìm thấy người dùng."));}p.setName(dto.getName().trim());p.setDescription(dto.getDescription());p.setPrice(dto.getPrice());p.setUser(owner);CloudinaryUploadResult upload=cloudinary.upload(image);if(upload!=null){String oldPublicId=publicId(p.getImageUrl());p.setImageUrl(upload.url()+"|"+upload.publicId());Product saved=products.save(p);cloudinary.delete(oldPublicId);return mapper.toDto(saved);}return mapper.toDto(products.save(p));}
 public void delete(Long id,Long actorId,boolean admin){Product p=products.findById(id).orElseThrow(()->new EntityNotFoundException("Không tìm thấy sản phẩm."));checkOwner(p,actorId,admin);String publicId=publicId(p.getImageUrl());products.delete(p);cloudinary.delete(publicId);}
 @Transactional(readOnly=true) public long countProducts(){return products.count();}
 @Transactional(readOnly=true) public long countByUser(Long userId){return products.countByUserId(userId);}
 private void checkOwner(Product p,Long actorId,boolean admin){if(!admin&&!Objects.equals(p.getUser().getId(),actorId))throw new AccessDeniedException("Bạn không có quyền với sản phẩm này.");}
 private String publicId(String imageUrl){if(imageUrl==null)return null;int separator=imageUrl.indexOf('|');return separator<0?null:imageUrl.substring(separator+1);}
}
