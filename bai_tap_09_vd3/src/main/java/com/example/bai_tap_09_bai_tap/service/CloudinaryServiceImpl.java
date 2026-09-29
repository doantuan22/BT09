package com.example.bai_tap_09_bai_tap.service;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
 @Service
public class CloudinaryServiceImpl implements CloudinaryService {
 private final Cloudinary cloudinary;
 public CloudinaryServiceImpl(Cloudinary cloudinary){this.cloudinary=cloudinary;}
 public CloudinaryUploadResult upload(MultipartFile file){if(file==null||file.isEmpty())return null;if(file.getSize()>10L*1024*1024)throw new IllegalArgumentException("Ảnh tối đa 10 MB.");String type=file.getContentType();if(type==null||!type.startsWith("image/"))throw new IllegalArgumentException("Chỉ chấp nhận tệp ảnh.");try{Map<?,?> result=cloudinary.uploader().upload(file.getBytes(),ObjectUtils.asMap("folder","shop/products","resource_type","image"));return new CloudinaryUploadResult((String)result.get("secure_url"),(String)result.get("public_id"));}catch(Exception ex){throw new IllegalStateException("Không thể tải ảnh lên Cloudinary. Kiểm tra cấu hình CLOUDINARY_*.",ex);}}
 public void delete(String publicId){if(publicId==null||publicId.isBlank())return;try{cloudinary.uploader().destroy(publicId,ObjectUtils.emptyMap());}catch(Exception ex){throw new IllegalStateException("Không thể xóa ảnh Cloudinary.",ex);}}
}
