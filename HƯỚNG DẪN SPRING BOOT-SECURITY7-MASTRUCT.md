# **HƯỚNG DẪN SPRING BOOT + SECURITY** 

### Bài tập: 

Cho các bảng Users, Roles (user, admin), OtpToken, Products. Mối quan hệ 1 user - n product. Upload images vào cloud cloudanry. Xây dựng code chi tiết cho chức năng Register xác nhận OTP qua mail, Login lưu session, forgotpassword gửi OTP qua mail, CRUD bảng user, product, tìm kiếm phân trang cho bảng user, cho bảng product, đếm số user, product của user bằng spring boot 4.1.1 + security mới nhất + mapper DTO to Entity và ngược lại bằng mapstruct, các view bằng thymeleaf + sql server. 



<!-- Start of picture text -->
THYMELEAF<br>(Views/ Templates)<br>AuthController UserController ProductController<br>(Register, Login, OTP, Forgot...) (CRUD, Search, Pagination) (CRUD, Search, Pagination)<br>AuthService UserService ProductService<br>(Register, Login, OTP, Forgot...) (CRUD, Search, Pagination) (CRUD, Search, Pagination)<br>zi ‘ff Mapper ‘J Mapper<br>Q s ee WB ies<br>OTP Mail Security Session<br>(Generate & verity) | | (Gmait swrP) (Spring Security) (sttpsession) DTO To<br>(User0TO, ProductOTO, .) (User0TO, ProductOTO,..)<br>SsSS (springRepository<br>bata jPa)<br>SS<br>‘> SQLSERVER<br>EZ (Database)<br><!-- End of picture text -->

Chức năng: 

### **Authentication** 

- Register 

- Gửi OTP email 

- Xác nhận OTP 

- Resend OTP 

- Login 

- Spring Security Session 

- Logout 

- Forgot password 

- Gửi OTP reset password 

- Verify OTP 

- Đổi password 

### **User** 

1. CRUD User 

2. Search User 

3. Pagination User 

4. Role USER/ADMIN 

5. Đếm tổng User 

6. Đếm Product của từng User 

### **Product** 

- CRUD Product 

- Search Product 

- Pagination Product 

- Upload ảnh Cloudinary 

- Product thuộc User 

- Hiển thị số Product của User 

- Xóa Product 

- **Công nghệ** 

|**Thành phần**|**Công nghệ**|
|---|---|
|Backend|Spring Boot 4.1.1|
|Security|Spring Security 7.1.x|
|Java|JDK 26|
|Database|SQL Server|
|ORM|Spring Data JPA / Hibernate|
|View|Thymeleaf|
|Mapper|MapStruct 1.6.3|
|Email|Spring Mail|
|Image|Cloudinary|
|Validation|Jakarta Validation|
|Build|Maven|
|Authentication|Session|
|Password|BCrypt|
|Architecture|MVC + Service + Repository|
|Cấu trúc project:||



- Y © shop-springboot-4-1-1 [boot] Y ® src/main/java 

   - v 8 vniiotstar 

Y {8 config 

   - LD) CloudinaryConfigjava 

   - > {3) EncodingConfig.java 

   - [i SecurityConfigjava 

- | controller| > 2) AuthControllerjava > B) ErrorControllerjava 

   - [) HomeControllerjava 

   - > ProductController.java > LB) UserControllerjava 

v i dto 

- 2) ForgotPasswordDTO,java 

- J) LoginDTO java 

> DD) ProductDTO java > [B) RegisterDTOjava >»p ResetPasswordDTO,java > JB) UserDTOjava > B) VerifyOtpDTOjava v # entity > |) OtpTokenjava > DD) Productjava > DF Rolejava >p User.java 

- v #8 mapper 

   - FR ProductMapper.java 

> [R UserMapperjava 

© §& repository 

> FR otpTokenRepositoryjava 

> FR ProductRepository,java > FF RoleRepositoryjava 

> GR UserRepository,java 

Y security > D) CustomUserDetailsjava > B) CustomUserDetailsServicejava v & service 

v #imol 

## v # impl 

         - J) AuthServicelmpljava 

         - > B) CloudinaryServicelmpljava 

         - 2) EmailServicelmpljava 

         - > [2) OtpServicelmpl.java > B) ProductServicelmpljava 

         - [B) UserServiceimpljava 

      - [F authServicejava 

      - [F CloudinaryServicejava 

      - FR cloudinaryUploadResultjava 

      - FB Emailservicejava 

      - F otpServicejava 

      - DR ProductServicejava 

   - TB UserServicejava 

   - > 1) ShopApplicationjava 

- v §® src/main/resources v B static Vv oss 

E app.css 

v & templates 

- v Bauth 

2) forgot-password.html E) login.html 2) register.html 2) reset-password.html B verify-otp.html v & fragments 

2) footer.html 2) header-html v B layouts ) layout-html v & products 2) form.html 2) listhtml 

v Busers BB form.html 2) listhtml &) error.html 2) homehtml 



<!-- Start of picture text -->
& application.properties<br>[g} data.sql<br>> BA JRE System Library [JavaSE-26]<br>> BA Maven Dependencies<br>> @ target/generated-sources/annotations<br>@ target/generated-test-sources/test-annotatior<br>> B settings<br>> src<br>> B target<br>® uploads<br>\) .classpath<br>L} env<br>|=) factorypath<br>® aitignore<br>[X) .project<br>{u pom.xml<br>README.md<br><!-- End of picture text -->

### 1. File pom.xml 

```
<?xml version="1.0" encoding="UTF-8"?>
<projectxmlns="http://maven.apache.org/POM/4.0.0"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
https://maven.apache.org/xsd/maven-4.0.0.xsd">
<modelVersion>4.0.0</modelVersion>
```

```
<parent>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-parent</artifactId>
<version>4.1.1</version>
<relativePath />
</parent>
```

```
<groupId>vn.iotstar</groupId>
<artifactId>shop-springboot-4-1-1</artifactId>
<version>1.0.0</version>
<name>shop-springboot-4-1-1</name>
```

```
<properties>
<java.version>26</java.version>
<mapstruct.version>1.6.3</mapstruct.version>
 <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
<project.reporting.outputEncoding>UTF-
8</project.reporting.outputEncoding>
</properties>
<dependencies>
<dependency>
<groupId>org.springframework.boot</groupId>
```

```
<artifactId>spring-boot-starter-webmvc</artifactId>
</dependency>
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-validation</artifactId>
</dependency>
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-mail</artifactId>
</dependency>
<dependency>
<groupId>com.microsoft.sqlserver</groupId>
<artifactId>mssql-jdbc</artifactId>
<scope>runtime</scope>
</dependency>
<dependency>
<groupId>org.mapstruct</groupId>
<artifactId>mapstruct</artifactId>
<version>${mapstruct.version}</version>
</dependency>
<dependency>
<groupId>org.mapstruct</groupId>
<artifactId>mapstruct-processor</artifactId>
<version>${mapstruct.version}</version>
<scope>provided</scope>
</dependency>
<dependency>
<groupId>com.cloudinary</groupId>
<artifactId>cloudinary-http5</artifactId>
<version>2.0.0</version>
</dependency>
<dependency>
<groupId>org.projectlombok</groupId>
<artifactId>lombok</artifactId>
<optional>true</optional>
</dependency>
<dependency>
<groupId>org.thymeleaf.extras</groupId>
<artifactId>thymeleaf-extras-springsecurity6</artifactId>
</dependency>
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-test</artifactId>
```

```
<scope>test</scope>
</dependency>
<dependency>
<groupId>org.springframework.security</groupId>
<artifactId>spring-security-test</artifactId>
<scope>test</scope>
</dependency>
<dependency>
<groupId>nz.net.ultraq.thymeleaf</groupId>
<artifactId>thymeleaf-layout-dialect</artifactId>
<scope>compile</scope>
</dependency>
</dependencies>
<build>
<plugins>
<plugin>
<groupId>org.apache.maven.plugins</groupId>
<artifactId>maven-compiler-plugin</artifactId>
<configuration>
                <encoding>UTF-8</encoding>
<annotationProcessorPaths>
<path>
<groupId>org.mapstruct</groupId>
<artifactId>mapstruct-
processor</artifactId>
<version>${mapstruct.version}</version>
</path>
<path>
<groupId>org.projectlombok</groupId>
<artifactId>lombok</artifactId>
</path>
</annotationProcessorPaths>
</configuration>
</plugin>
<plugin>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-maven-plugin</artifactId>
</plugin>
</plugins>
</build>
</project>
========================================================
```

2. File application.properties 

```
spring.config.import=optional:file:.env[.properties]
spring.application.name=shop
```

```
server.port=${SERVER_PORT:8080}
```

```
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

```
spring.jpa.hibernate.ddl-auto=${DDL_AUTO:update}
spring.jpa.show-sql=${SHOW_SQL:false}
```

```
spring.mail.host=${MAIL_HOST}
spring.mail.port=${MAIL_PORT}
spring.mail.username=${MAIL_USERNAME}
spring.mail.password=${MAIL_PASSWORD}
```

```
#jwt.secret=${JWT_SECRET}
#jwt.access-expiration=${JWT_ACCESS_EXPIRATION:900000}
#jwt.refresh-expiration=${JWT_REFRESH_EXPIRATION:604800000}
```

```
spring.datasource.driverClassName=com.microsoft.sqlserver.jdbc.SQLServerDriver
```

```
spring.jpa.properties.hibernate.format_sql=true
spring.thymeleaf.encoding=UTF-8
spring.thymeleaf.cache=false
```

```
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

```
cloudinary.cloud-name=${CLOUDINARY_CLOUD_NAME}
cloudinary.api-key=${CLOUDINARY_API_KEY}
cloudinary.api-secret=${CLOUDINARY_API_SECRET}
```

```
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=20MB
```

```
spring.servlet.encoding.enabled=true
spring.servlet.encoding.charset=UTF-8
spring.servlet.encoding.force=true
spring.servlet.encoding.force-request=true
spring.servlet.encoding.force-response=true
```

```
spring.main.allow-bean-definition-overriding=true
```

### 3. Tạo biến môi trường .env trong project 

```
# ===============================
# DATABASE
# ===============================
DB_URL=jdbc:sqlserver://localhost:1433;databaseName=webst3;encrypt=false;trustSer
verCertificate=true;sslProtocol=TLSv1.2;characterEncoding=UTF-8
DB_USERNAME=sa
DB_PASSWORD=fdfdfd
```

```
# ===============================
# JPA
```

```
# ===============================
```

```
DDL_AUTO=update
SHOW_SQL=true
```

```
# ===============================
# SERVER
# ===============================
SERVER_PORT=8080
```

```
# ===============================
# JWT
# ===============================
#JWT_SECRET=your-super-secret-key-change-this-in-production
#JWT_ACCESS_EXPIRATION=900000
#JWT_REFRESH_EXPIRATION=604800000
```

```
# ===============================
# SMTP
# ===============================
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=abc@gmail.com
MAIL_PASSWORD=zkhxpxxfcalaknld
```

```
# ===============================
# CLOUDINARY
# ===============================
CLOUDINARY_CLOUD_NAME=dfdfdf
CLOUDINARY_API_KEY=576632571682623
CLOUDINARY_API_SECRET=ikPEbngxnKwAw-XkvR1WVEaQZcI
```

```
================================================================
```

4. Tạo các Entity 

```
package vn.iotstar.entity;
```

```
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "users",
       indexes = {
@Index(name = "idx_users_username", columnList = "username"),
@Index(name = "idx_users_email", columnList = "email")
       })
@Getter@Setter@NoArgsConstructor@AllArgsConstructor@Builder
publicclass User {
@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
@Column(nullable = false, unique = true, length = 50)
private String username;
@Column(nullable = false, unique = true, length = 150)
```

```
private String email;
@Column(nullable = false)
private String password;
@Column(columnDefinition = "nvarchar(500)")
private String fullName;
@Builder.Default
@Column(nullable = false)
privatebooleanenabled = false;
@ManyToOne(fetch = FetchType.EAGER, optional = false)
@JoinColumn(name = "role_id", nullable = false)
private Role role;
@Builder.Default
@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
private List<Product> products = new ArrayList<>();
}
```

=========================================================== 

```
package vn.iotstar.entity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity
@Table(name = "products",
       indexes = @Index(name = "idx_products_name", columnList = "name"))
@Getter@Setter@NoArgsConstructor@AllArgsConstructor@Builder
publicclass Product {
@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
@Column(nullable = false,length = 2000,columnDefinition = "nvarchar(500)")
private String name;
@Column(length = 5000, columnDefinition = "nvarchar(500)")
private String description;
@Column(nullable = false, precision = 18, scale = 2)
private BigDecimal price;
@Column(length = 1000)
private String imageUrl;
@ManyToOne(fetch = FetchType.LAZY, optional = false)
@JoinColumn(name = "user_id", nullable = false)
private User user;
@Builder.Default
```

```
@Column(nullable = false)
private LocalDateTime createdAt = LocalDateTime.now();
}
```

======================================== 

**<mark>`package`</mark>** <mark>`vn.iotstar.entity;`</mark> **<mark>`import`</mark>** <mark>`jakarta.persistence.*;`</mark> **<mark>`import`</mark>** <mark>`lombok.*; @Entity @Table(name = "roles") @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder`</mark> **<mark>`public class`</mark>** <mark>`Role { @Id @GeneratedValue(strategy = GenerationType.`</mark> **_<mark>`IDENTITY`</mark>_** <mark>`)`</mark> **<mark>`private`</mark>** <mark>`Long id; @Column(nullable =`</mark> **<mark>`false`</mark>** <mark>`, unique =`</mark> **<mark>`false`</mark>** <mark>`, length = 30)`</mark> **<mark>`private`</mark>** <mark>`String name; }`</mark> ====================================================== **<mark>`package`</mark>** <mark>`vn.iotstar.entity;`</mark> **<mark>`import`</mark>** <mark>`jakarta.persistence.*;`</mark> **<mark>`import`</mark>** <mark>`lombok.*;`</mark> **<mark>`import`</mark>** <mark>`java.time.LocalDateTime; @Entity @Table(name = "otp_tokens", indexes = @Index(name = "idx_otp_email_type", columnList = "email,type")) @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder`</mark> **<mark>`public class`</mark>** <mark>`OtpToken { @Id @GeneratedValue(strategy = GenerationType.`</mark> **_<mark>`IDENTITY`</mark>_** <mark>`)`</mark> **<mark>`private`</mark>** <mark>`Long id; @Column(nullable =`</mark> **<mark>`false`</mark>** <mark>`, length = 150)`</mark> **<mark>`private`</mark>** <mark>`String email; @Column(nullable =`</mark> **<mark>`false`</mark>** <mark>`, length = 100)`</mark> **<mark>`private`</mark>** <mark>`String otpHash; @Column(nullable =`</mark> **<mark>`false`</mark>** <mark>`, length = 30)`</mark> **<mark>`private`</mark>** <mark>`String type; @Column(nullable =`</mark> **<mark>`false`</mark>** <mark>`)`</mark> **<mark>`private`</mark>** <mark>`LocalDateTime expiresAt; @Column(nullable =`</mark> **<mark>`false`</mark>** <mark>`)`</mark> **<mark>`private int`</mark>** <mark>`attempts; @Builder.Default @Column(nullable =`</mark> **<mark>`false`</mark>** <mark>`)`</mark> **<mark>`private boolean`</mark>** <mark>`used =`</mark> **<mark>`false`</mark>** <mark>`;`</mark> 

```
@Column(nullable = false)
private LocalDateTime createdAt;
}
```

5. Tạo các DTO 

```
package vn.iotstar.dto;
import jakarta.validation.constraints.*;
import lombok.Data;
@Data
publicclass UserDTO {
private Long id;
@NotBlank(message = "Username không được để trống")
private String username;
@NotBlank(message = "Email không được để trống")
@Email(message = "Email không hợp lệ")
private String email;
@NotBlank(message = "Họ tên không được để trống")
private String fullName;
privatebooleanenabled;
private String roleName;
privatelongproductCount;
}
```

==================================================================== 

```
package vn.iotstar.dto;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
```

```
import java.math.BigDecimal;
```

```
@Data
publicclass ProductDTO {
private Long id;
@NotBlank(message = "Tên sản phẩm không được để trống")
private String name;
private String description;
```

```
@NotNull(message = "Giá không được để trống")
@DecimalMin(value = "0.0", message = "Giá phải >= 0")
private BigDecimal price;
private String imageUrl;
private Long userId;
private String username;
```

```
private MultipartFile image;
```

```
}
```

====================================================================== 

```
package vn.iotstar.dto;
import jakarta.validation.constraints.*;
import lombok.Data;
@Data
publicclass RegisterDTO {
@NotBlank(message = "Username không được để trống")
private String username;
@NotBlank(message = "Email không được để trống")
@Email(message = "Email không hợp lệ")
private String email;
@NotBlank(message = "Mật khẩu không được để trống")
@Size(min = 6, message = "Mật khẩu tối thiểu 6 ký tự")
private String password;
@NotBlank(message = "Xác nhận mật khẩu")
private String confirmPassword;
@NotBlank(message = "Họ tên không được để trống")
private String fullName;
}
```

======================================================== 

```
package vn.iotstar.dto;
```

**<mark>`import`</mark>** <mark>`jakarta.validation.constraints.NotBlank;`</mark> **<mark>`import`</mark>** <mark>`lombok.Data; @Data`</mark> **<mark>`public class`</mark>** <mark>`LoginDTO { @NotBlank`</mark> **<mark>`private`</mark>** <mark>`String username; @NotBlank`</mark> **<mark>`private`</mark>** <mark>`String password; }`</mark> =============================================== 

```
package vn.iotstar.dto;
```

```
import jakarta.validation.constraints.*;
import lombok.Data;
@Data
publicclass ResetPasswordDTO {
@NotBlank@Email
private String email;
```

```
@NotBlank
@Size(min = 6)
private String password;
@NotBlank
private String confirmPassword;
}
```

======================================= 

```
package vn.iotstar.dto;
import jakarta.validation.constraints.*;
import lombok.Data;
```

```
@Data
publicclass VerifyOtpDTO {
@NotBlank@Email
private String email;
@NotBlank
@Size(min = 6, max = 6)
private String otp;
}
```

====================================== 

```
package vn.iotstar.dto;
```

```
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
```

```
@Data
publicclass ForgotPasswordDTO {
@NotBlank
@Email
private String email;
}
```

============================================ 

6. Tạo các Mapper 

```
package vn.iotstar.mapper;
```

```
import org.mapstruct.*;
import vn.iotstar.dto.UserDTO;
import vn.iotstar.entity.User;
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
publicinterface UserMapper {
@Mapping(target = "roleName", source = "role.name")
    UserDTO toDTO(User entity);
```

```
@Mapping(target = "role", ignore = true)
@Mapping(target = "products", ignore = true)
@Mapping(target = "password", ignore = true)
    User toEntity(UserDTO dto);
}
```

```
package vn.iotstar.mapper;
```

```
import org.mapstruct.*;
import vn.iotstar.dto.ProductDTO;
import vn.iotstar.entity.Product;
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
publicinterface ProductMapper {
@Mapping(target = "userId", source = "user.id")
@Mapping(target = "username", source = "user.username")
@Mapping(target = "image", ignore = true)
    ProductDTO toDTO(Product entity);
@Mapping(target = "user", ignore = true)
@Mapping(target = "createdAt", ignore = true)
    Product toEntity(ProductDTO dto);
}
```

======================================================== 

7. Tạo database 

Khởi động SQL server lên và tạo 01 database (webst3) 

8. Tạo các Repository 

```
package vn.iotstar.repository;
```

```
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import vn.iotstar.entity.User;
import java.util.Optional;
```

```
publicinterface UserRepository extends JpaRepository<User, Long> {
```

```
    Optional<User> findByUsername(String username);
```

```
    Optional<User> findByEmail(String email);
boolean existsByUsername(String username);
boolean existsByEmail(String email);
```

```
@Query("""
selectufrom User u
wherelower(u.username)likelower(concat('%',:keyword, '%'))
orlower(u.email)likelower(concat('%',:keyword, '%'))
orlower(u.fullName)likelower(concat('%',:keyword, '%'))
```

```
    """)
```

```
    Page<User> search(@Param("keyword") String keyword, Pageable pageable);
```

```
@Query("selectcount(p)from Product pwherep.user.id=:userId")
long countProductsByUserId(@Param("userId") Long userId);
```

```
@Query("""
selectu.idasid,count(p.id)asproductCount
from User uleftjoinu.productsp
groupbyu.id
    """)
    java.util.List<Object[]> countProductsForUsers();
}
```

====================================================== 

```
package vn.iotstar.repository;
```

```
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import vn.iotstar.entity.Product;
```

```
publicinterface ProductRepository extends JpaRepository<Product, Long> {
```

```
@Query("""
selectpfrom Product pjoinfetchp.useru
        where lower(p.name) like lower(concat('%', :keyword, '%'))
           or lower(coalesce(p.description, '')) like lower(concat('%', :keyword,
'%'))
    """)
    Page<Product> search(@Param("keyword") String keyword, Pageable pageable);
```

```
    Page<Product> findByUserId(Long userId, Pageable pageable);
```

- `long countByUserId(Long userId);` <mark>`}`</mark> 

======================================================= 

```
package vn.iotstar.repository;
```

```
import org.springframework.data.jpa.repository.JpaRepository;
import vn.iotstar.entity.Role;
import java.util.Optional;
```

```
publicinterface RoleRepository extends JpaRepository<Role, Long> {
```

```
    Optional<Role> findByName(String name);
```

```
}
```

===================================================== 

```
package vn.iotstar.repository;
```

```
import org.springframework.data.jpa.repository.JpaRepository;
import vn.iotstar.entity.OtpToken;
import java.util.Optional;
```

```
publicinterface OtpTokenRepository extends JpaRepository<OtpToken, Long> {
```

```
    Optional<OtpToken> findTopByEmailAndTypeAndUsedFalseOrderByCreatedAtDesc(
        String email, String type);
```

```
void deleteByEmailAndType(String email, String type);
```

```
    Optional<OtpToken> findTopByEmailAndTypeOrderByCreatedAtDesc(
        String email, String type);
}
```

### 9. Tạo các interface services 

```
package vn.iotstar.service;
```

```
import org.springframework.data.domain.Page;
import vn.iotstar.dto.UserDTO;
```

```
publicinterface UserService {
    Page<UserDTO> findAll(String keyword, intpage, intsize);
    UserDTO findById(Long id);
    UserDTO create(UserDTO dto);
    UserDTO update(Long id, UserDTO dto);
void delete(Long id);
long countUsers();
long countProducts(Long userId);
}
```

==================================================== 

```
package vn.iotstar.service;
```

```
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import vn.iotstar.dto.ProductDTO;
```

```
publicinterface ProductService {
    Page<ProductDTO> findAll(String keyword, intpage, intsize);
    ProductDTO findById(Long id);
    ProductDTO create(ProductDTO dto, MultipartFile image);
    ProductDTO update(Long id, ProductDTOdto, MultipartFile image);
void delete(Long id);
long countProducts();
long countByUser(Long userId);
}
```

=============================================================== 

```
package vn.iotstar.service;
```

```
publicinterface OtpService {
void sendRegisterOtp(String email);
boolean verifyRegisterOtp(String email, String otp);
void sendResetPasswordOtp(String email);
boolean verifyResetPasswordOtp(String email, String otp);
}
```

=================================================================== 

```
package vn.iotstar.service;
```

```
publicinterface EmailService {
void sendOtp(String email, String otp, String subject);
}
```

=================================================================== 

```
package vn.iotstar.service;
```

```
import org.springframework.web.multipart.MultipartFile;
```

```
publicinterface CloudinaryService {
    CloudinaryUploadResult upload(MultipartFile file);
void delete(String publicId);
}
```

========================================================= 

```
package vn.iotstar.service;
```

```
publicrecord CloudinaryUploadResult(String url, String publicId) {}
```

========================================================= 

```
package vn.iotstar.service;
```

```
import vn.iotstar.dto.RegisterDTO;
```

```
publicinterface AuthService {
void register(RegisterDTO dto);
boolean verifyRegister(String email, String otp);
void forgotPassword(String email);
boolean verifyResetOtp(String email, String otp);
void resetPassword(String email, String password);
}
```

### 10. Tạo các Implement Services 

```
package vn.iotstar.service.impl;
```

```
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.iotstar.dto.UserDTO;
```

```
import vn.iotstar.entity.Role;
import vn.iotstar.entity.User;
import vn.iotstar.mapper.UserMapper;
import vn.iotstar.repository.RoleRepository;
import vn.iotstar.repository.UserRepository;
import vn.iotstar.service.UserService;
```

```
@Service
@RequiredArgsConstructor
publicclass UserServiceImpl implements UserService {
privatefinal UserRepository userRepository;
privatefinal RoleRepository roleRepository;
privatefinal UserMapper mapper;
privatefinal PasswordEncoder passwordEncoder;
@Override@Transactional(readOnly = true)
public Page<UserDTO> findAll(String keyword, intpage, intsize) {
        Pageable pageable = PageRequest.of(
            Math.max(page, 0), Math.max(size, 1),
            Sort.by(Sort.Direction.DESC,"id")
        );
returnuserRepository.search(keyword == null ? "" : keyword, pageable)
            .map(user -> {
                UserDTO dto = mapper.toDTO(user);
dto.setProductCount(
userRepository.countProductsByUserId(user.getId())
                );
returndto;
            });
    }
@Override@Transactional(readOnly = true)
public UserDTO findById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("User không tồn
tại"));
        UserDTO dto = mapper.toDTO(user);
dto.setProductCount(userRepository.countProductsByUserId(id));
returndto;
    }
@Override@Transactional
public UserDTO create(UserDTO dto) {
if (userRepository.existsByUsername(dto.getUsername()))
thrownew IllegalArgumentException("Username đã tồn tại");
if (userRepository.existsByEmail(dto.getEmail()))
thrownew IllegalArgumentException("Email đã tồn tại");
        User user = mapper.toEntity(dto);
        Role role = roleRepository.findByName(
dto.getRoleName() == null || dto.getRoleName().isBlank()
                ? "ROLE_USER" : dto.getRoleName()
        ).orElseThrow(() -> new IllegalArgumentException("Role không tồn tại"));
user.setRole(role);
```

```
user.setPassword(passwordEncoder.encode("123456"));
user.setEnabled(dto.isEnabled());
returnmapper.toDTO(userRepository.save(user));
    }
@Override@Transactional
public UserDTO update(Long id, UserDTO dto) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("User không tồn
tại"));
if (!user.getEmail().equals(dto.getEmail())
                && userRepository.existsByEmail(dto.getEmail()))
thrownew IllegalArgumentException("Email đã tồn tại");
user.setUsername(dto.getUsername());
user.setEmail(dto.getEmail());
user.setFullName(dto.getFullName());
user.setEnabled(dto.isEnabled());
if (dto.getRoleName() != null && !dto.getRoleName().isBlank()) {
            Role role = roleRepository.findByName(dto.getRoleName())
                .orElseThrow(() -> new IllegalArgumentException("Role không tồn
tại"));
user.setRole(role);
        }
returnmapper.toDTO(userRepository.save(user));
    }
@Override@Transactional
publicvoid delete(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("User không tồn
tại"));
userRepository.delete(user);
    }
@Override@Transactional(readOnly = true)
publiclong countUsers() { returnuserRepository.count(); }
@Override@Transactional(readOnly = true)
publiclong countProducts(Long userId) {
returnuserRepository.countProductsByUserId(userId);
    }
}
```

======================================== 

```
package vn.iotstar.service.impl;
```

```
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vn.iotstar.dto.ProductDTO;
```

```
import vn.iotstar.entity.Product;
import vn.iotstar.entity.User;
import vn.iotstar.mapper.ProductMapper;
import vn.iotstar.repository.ProductRepository;
import vn.iotstar.repository.UserRepository;
import vn.iotstar.service.*;
```

```
@Service
@RequiredArgsConstructor
publicclass ProductServiceImpl implements ProductService {
privatefinal ProductRepository productRepository;
privatefinal UserRepository userRepository;
privatefinal ProductMapper mapper;
privatefinal CloudinaryService cloudinaryService;
@Override@Transactional(readOnly = true)
public Page<ProductDTO> findAll(String keyword, intpage, intsize) {
        Pageable pageable = PageRequest.of(
            Math.max(page, 0), Math.max(size, 1),
            Sort.by(Sort.Direction.DESC,"id")
        );
returnproductRepository.search(keyword == null ? "" : keyword, pageable)
            .map(mapper::toDTO);
    }
@Override@Transactional(readOnly = true)
public ProductDTO findById(Long id) {
returnmapper.toDTO(productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Product không tồn
tại")));
    }
@Override@Transactional
public ProductDTO create(ProductDTO dto, MultipartFile image) {
        User user = userRepository.findById(dto.getUserId())
            .orElseThrow(() -> new IllegalArgumentException("User không tồn tại"));
        Product product = mapper.toEntity(dto);
product.setUser(user);
if (image != null && !image.isEmpty()) {
            CloudinaryUploadResult r = cloudinaryService.upload(image);
product.setImageUrl(r.url() + "|" + r.publicId());
        }
returnmapper.toDTO(productRepository.save(product));
    }
@Override@Transactional
public ProductDTO update(Long id, ProductDTO dto, MultipartFile image) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Product không tồn
tại"));
product.setName(dto.getName());
product.setDescription(dto.getDescription());
product.setPrice(dto.getPrice());
if (image != null && !image.isEmpty()) {
```

```
            String old = product.getImageUrl();
if (old != null && old.contains("|")) {
cloudinaryService.delete(old.substring(old.indexOf('|') + 1));
            }
            CloudinaryUploadResult r = cloudinaryService.upload(image);
product.setImageUrl(r.url() + "|" + r.publicId());
        }
returnmapper.toDTO(productRepository.save(product));
    }
@Override@Transactional
publicvoid delete(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Product không tồn
tại"));
        String image = product.getImageUrl();
if (image != null && image.contains("|"))
cloudinaryService.delete(image.substring(image.indexOf('|') + 1));
productRepository.delete(product);
    }
@Override@Transactional(readOnly = true)
publiclong countProducts() { returnproductRepository.count(); }
@Override@Transactional(readOnly = true)
publiclong countByUser(Long userId) {
returnproductRepository.countByUserId(userId);
    }
}
```

======================================= 

```
package vn.iotstar.service.impl;
```

```
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.iotstar.entity.OtpToken;
import vn.iotstar.repository.OtpTokenRepository;
import vn.iotstar.service.EmailService;
import vn.iotstar.service.OtpService;
```

```
import java.security.SecureRandom;
import java.time.LocalDateTime;
```

```
@Service
@RequiredArgsConstructor
publicclass OtpServiceImpl implements OtpService {
privatestaticfinalintMAX_ATTEMPTS = 5;
privatestaticfinalintOTP_MINUTES = 5;
privatefinal OtpTokenRepository repository;
privatefinal PasswordEncoder passwordEncoder;
privatefinal EmailService emailService;
privatefinal SecureRandom random = new SecureRandom();
```

```
private String generateOtp() {
return"%06d".formatted(random.nextInt(1_000_000));
    }
privatevoid send(String email, String type, String subject) {
repository.deleteByEmailAndType(email, type);
        String otp = generateOtp();
        OtpToken token = OtpToken.builder()
            .email(email)
            .otpHash(passwordEncoder.encode(otp))
            .type(type)
            .expiresAt(LocalDateTime.now().plusMinutes(OTP_MINUTES))
            .attempts(0)
            .used(false)
            .createdAt(LocalDateTime.now())
            .build();
repository.save(token);
emailService.sendOtp(email, otp, subject);
    }
@Override@Transactional
publicvoid sendRegisterOtp(String email) {
        send(email, "REGISTER", "Shop - Xác nhận đăng ký tài khoản");
    }
@Override@Transactional
publicvoid sendResetPasswordOtp(String email) {
        send(email, "RESET_PASSWORD", "Shop - OTP đặt lại mật khẩu");
    }
privateboolean verify(String email, String otp, String type) {
        OtpToken token = repository
            .findTopByEmailAndTypeAndUsedFalseOrderByCreatedAtDesc(email, type)
            .orElse(null);
if (token == null || token.getExpiresAt().isBefore(LocalDateTime.now())
                || token.getAttempts() >= MAX_ATTEMPTS) returnfalse;
token.setAttempts(token.getAttempts() + 1);
if (!passwordEncoder.matches(otp, token.getOtpHash())) {
repository.save(token);
returnfalse;
        }
token.setUsed(true);
repository.save(token);
returntrue;
    }
@Override@Transactional
publicboolean verifyRegisterOtp(String email, String otp) {
return verify(email, otp, "REGISTER");
    }
@Override@Transactional
publicboolean verifyResetPasswordOtp(String email, String otp) {
return verify(email, otp, "RESET_PASSWORD");
```

```
    }
}
```

==================================== 

```
package vn.iotstar.service.impl;
```

```
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import vn.iotstar.service.EmailService;
```

```
@Service
@RequiredArgsConstructor
publicclass EmailServiceImpl implements EmailService {
privatefinal JavaMailSender mailSender;
@Override
publicvoid sendOtp(String email, String otp, String subject) {
        SimpleMailMessage message = new SimpleMailMessage();
message.setTo(email);
message.setSubject(subject);
message.setText("""
            Xin chào,
            Mã OTP của bạn là: %s
            OTP có hiệu lực trong 5 phút và chỉ sử dụng một lần.
            Không chia sẻ mã này cho người khác.
            """.formatted(otp));
mailSender.send(message);
    }
}
```

===================================================== 

```
package vn.iotstar.service.impl;
```

```
import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.iotstar.service.CloudinaryService;
import vn.iotstar.service.CloudinaryUploadResult;
```

```
import java.util.Map;
```

```
@Service
@RequiredArgsConstructor
publicclass CloudinaryServiceImpl implements CloudinaryService {
privatefinal Cloudinary cloudinary;
@Override
public CloudinaryUploadResult upload(MultipartFile file) {
if (file == null || file.isEmpty())
thrownew IllegalArgumentException("Chưa chọn ảnh");
```

<mark>`String type = file.getContentType();`</mark> **<mark>`if`</mark>** <mark>`(type ==`</mark> **<mark>`null`</mark>** <mark>`|| !type.startsWith("image/"))`</mark> **<mark>`throw new`</mark>** <mark>`IllegalArgumentException("Chỉ cho phép file hình ảnh");`</mark> **<mark>`try`</mark>** <mark>`{ Map<?, ?> result = cloudinary.uploader().upload( file.getBytes(), Map.`</mark> _<mark>`of`</mark>_ <mark>`("folder", "shop/products") );`</mark> **<mark>`return new`</mark>** <mark>`CloudinaryUploadResult( String.`</mark> _<mark>`valueOf`</mark>_ <mark>`(result.get("secure_url")), String.`</mark> _<mark>`valueOf`</mark>_ <mark>`(result.get("public_id")) ); }`</mark> **<mark>`catch`</mark>** <mark>`(Exception e) {`</mark> **<mark>`throw new`</mark>** <mark>`IllegalStateException("Upload Cloudinary thất bại", e); } } @Override`</mark> **<mark>`public void`</mark>** <mark>`delete(String publicId) {`</mark> **<mark>`if`</mark>** <mark>`(publicId ==`</mark> **<mark>`null`</mark>** <mark>`|| publicId.isBlank())`</mark> **<mark>`return`</mark>** <mark>`;`</mark> **<mark>`try`</mark>** <mark>`{ cloudinary.uploader().destroy( publicId, Map.`</mark> _<mark>`of`</mark>_ <mark>`("resource_type", "image") ); }`</mark> **<mark>`catch`</mark>** <mark>`(Exception e) {`</mark> **<mark>`throw new`</mark>** <mark>`IllegalStateException("Xóa ảnh Cloudinary thất bại", e); } } }`</mark> ================================================================ 

```
package vn.iotstar.service.impl;
```

```
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.iotstar.dto.RegisterDTO;
import vn.iotstar.entity.Role;
import vn.iotstar.entity.User;
import vn.iotstar.repository.RoleRepository;
import vn.iotstar.repository.UserRepository;
import vn.iotstar.service.AuthService;
import vn.iotstar.service.OtpService;
```

```
@Service
@RequiredArgsConstructor
publicclass AuthServiceImpl implements AuthService {
privatefinal UserRepository userRepository;
privatefinal RoleRepository roleRepository;
privatefinal PasswordEncoder passwordEncoder;
privatefinal OtpService otpService;
```

<mark>`@Override @Transactional`</mark> **<mark>`public void`</mark>** <mark>`register(RegisterDTO dto) {`</mark> **<mark>`if`</mark>** <mark>`(userRepository.existsByUsername(dto.getUsername()))`</mark> **<mark>`throw new`</mark>** <mark>`IllegalArgumentException("Username đã tồn tại");`</mark> **<mark>`if`</mark>** <mark>`(userRepository.existsByEmail(dto.getEmail()))`</mark> **<mark>`throw new`</mark>** <mark>`IllegalArgumentException("Email đã tồn tại");`</mark> **<mark>`if`</mark>** <mark>`(!dto.getPassword().equals(dto.getConfirmPassword()))`</mark> **<mark>`throw new`</mark>** <mark>`IllegalArgumentException("Mật khẩu xác nhận không đúng"); Role role = roleRepository.findByName("ROLE_USER") .orElseThrow(() ->`</mark> **<mark>`new`</mark>** <mark>`IllegalStateException("Chưa có ROLE_USER")); User user = User.`</mark> _<mark>`builder`</mark>_ <mark>`() .username(dto.getUsername()) .email(dto.getEmail()) .password(passwordEncoder.encode(dto.getPassword())) .fullName(dto.getFullName()) .enabled(`</mark> **<mark>`false`</mark>** <mark>`) .role(role) .build(); userRepository.save(user); otpService.sendRegisterOtp(dto.getEmail()); } @Override @Transactional`</mark> **<mark>`public boolean`</mark>** <mark>`verifyRegister(String email, String otp) {`</mark> **<mark>`boolean`</mark>** <mark>`ok = otpService.verifyRegisterOtp(email, otp);`</mark> **<mark>`if`</mark>** <mark>`(!ok)`</mark> **<mark>`return false`</mark>** <mark>`; User user = userRepository.findByEmail(email) .orElseThrow(() ->`</mark> **<mark>`new`</mark>** <mark>`IllegalArgumentException("User không tồn tại")); user.setEnabled(`</mark> **<mark>`true`</mark>** <mark>`);`</mark> **<mark>`return true`</mark>** <mark>`; } @Override @Transactional`</mark> **<mark>`public void`</mark>** <mark>`forgotPassword(String email) {`</mark> **<mark>`if`</mark>** <mark>`(!userRepository.existsByEmail(email))`</mark> **<mark>`throw new`</mark>** <mark>`IllegalArgumentException("Email không tồn tại"); otpService.sendResetPasswordOtp(email); } @Override`</mark> **<mark>`public boolean`</mark>** <mark>`verifyResetOtp(String email, String otp) {`</mark> **<mark>`return`</mark>** <mark>`otpService.verifyResetPasswordOtp(email, otp); } @Override @Transactional`</mark> **<mark>`public void`</mark>** <mark>`resetPassword(String email, String password) { User user = userRepository.findByEmail(email) .orElseThrow(() ->`</mark> **<mark>`new`</mark>** <mark>`IllegalArgumentException("Email không tồn tại")); user.setPassword(passwordEncoder.encode(password)); } }`</mark> 11. Tạo các Configs 

```
package vn.iotstar.config;
import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import java.util.Map;
@Configuration
publicclass CloudinaryConfig {
@Bean
    Cloudinary cloudinary(
@Value("${cloudinary.cloud-name}") String cloudName,
@Value("${cloudinary.api-key}") String apiKey,
@Value("${cloudinary.api-secret}") String apiSecret) {
returnnew Cloudinary(Map.of(
"cloud_name", cloudName,
"api_key", apiKey,
"api_secret", apiSecret
        ));
    }
}
=====================================================
package vn.iotstar.config;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import vn.iotstar.security.CustomUserDetailsService;
```

```
@Configuration
@RequiredArgsConstructor
publicclass SecurityConfig {
privatefinal CustomUserDetailsService userDetailsService;
@Bean
    PasswordEncoder passwordEncoder() {
returnnew BCryptPasswordEncoder();
    }
@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
http
            .userDetailsService(userDetailsService)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
"/", "/login", "/register", "/verify-otp",
"/forgot-password", "/reset-password",
"/resend-register-otp", "/css/**", "/js/**"
                ).permitAll()
                .requestMatchers("/users/**").hasRole("ADMIN")
                .requestMatchers("/products/**").authenticated()
```

```
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            )
            .sessionManagement(session -> session
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
            );
returnhttp.build();
    }
}
```

```
package vn.iotstar.config;
```

```
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
```

```
@Configuration
publicclass EncodingConfig {
@Bean
FilterRegistrationBean<CharacterEncodingFilter> characterEncodingFilter() {
CharacterEncodingFilter filter = new CharacterEncodingFilter();
filter.setEncoding("UTF-8");
filter.setForceEncoding(true);
FilterRegistrationBean<CharacterEncodingFilter> registration = new
FilterRegistrationBean<>(filter);
registration.setOrder(Integer.MIN_VALUE);
returnregistration;
}
}
```

12. Tạo các Controllers 

```
package vn.iotstar.controller;
```

```
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.iotstar.dto.UserDTO;
```

```
import vn.iotstar.service.UserService;
```

```
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
publicclass UserController {
privatefinal UserService userService;
@GetMapping
public String list(@RequestParam(defaultValue = "") String keyword,
@RequestParam(defaultValue = "0") intpage,
@RequestParam(defaultValue = "10") intsize,
                       Model model) {
model.addAttribute("users", userService.findAll(keyword, page, size));
model.addAttribute("keyword", keyword);
model.addAttribute("size", size);
return"users/list";
    }
@GetMapping("/create")
public String create(Model model) {
        UserDTO dto = new UserDTO();
dto.setEnabled(true);
dto.setRoleName("ROLE_USER");
model.addAttribute("userDTO", dto);
model.addAttribute("mode", "create");
return"users/form";
    }
@PostMapping("/create")
public String create(@Valid@ModelAttribute UserDTO dto,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirect) {
if (result.hasErrors()) {
model.addAttribute("mode", "create");
return"users/form";
        }
try {
userService.create(dto);
redirect.addFlashAttribute("success", "Tạo user thành công. Mật khẩu
mặc định: 123456");
return"redirect:/users";
        } catch (IllegalArgumentException e) {
result.reject("user.error", e.getMessage());
model.addAttribute("mode", "create");
return"users/form";
        }
    }
@GetMapping("/edit/{id}")
public String edit(@PathVariable Long id, Model model) {
model.addAttribute("userDTO", userService.findById(id));
model.addAttribute("mode", "edit");
return"users/form";
```

<mark>`} @PostMapping("/edit/{id}")`</mark> **<mark>`public`</mark>** <mark>`String edit(@PathVariable Long id, @Valid @ModelAttribute UserDTO dto, BindingResult result, Model model, RedirectAttributes redirect) {`</mark> **<mark>`if`</mark>** <mark>`(result.hasErrors()) { model.addAttribute("mode", "edit");`</mark> **<mark>`return`</mark>** <mark>`"users/form"; }`</mark> **<mark>`try`</mark>** <mark>`{ userService.update(id, dto); redirect.addFlashAttribute("success", "Cập nhật user thành công.");`</mark> **<mark>`return`</mark>** <mark>`"redirect:/users"; }`</mark> **<mark>`catch`</mark>** <mark>`(IllegalArgumentException e) { result.reject("user.error", e.getMessage()); model.addAttribute("mode", "edit");`</mark> **<mark>`return`</mark>** <mark>`"users/form"; } } @PostMapping("/delete/{id}")`</mark> **<mark>`public`</mark>** <mark>`String delete(@PathVariable Long id, RedirectAttributes redirect) { userService.delete(id); redirect.addFlashAttribute("success", "Xóa user thành công.");`</mark> **<mark>`return`</mark>** <mark>`"redirect:/users"; } }`</mark> ============================== 

```
package vn.iotstar.controller;
```

```
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.iotstar.dto.ProductDTO;
import vn.iotstar.security.CustomUserDetails;
import vn.iotstar.service.ProductService;
```

```
@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
publicclass ProductController {
privatefinal ProductService productService;
@GetMapping
public String list(@RequestParam(defaultValue = "") String keyword,
```

```
@RequestParam(defaultValue = "0") intpage,
@RequestParam(defaultValue = "10") intsize,
                       Model model) {
model.addAttribute("products", productService.findAll(keyword, page, size));
model.addAttribute("keyword", keyword);
model.addAttribute("size", size);
return"products/list";
    }
@GetMapping("/create")
public String create(Model model) {
model.addAttribute("productDTO", new ProductDTO());
model.addAttribute("mode", "create");
return"products/form";
    }
@PostMapping("/create")
public String create(@Valid@ModelAttribute ProductDTO dto,
                         BindingResult result,
@RequestParam(required = false) MultipartFile image,
                         Authentication authentication,
                         Model model,
                         RedirectAttributes redirect) {
if (result.hasErrors()) {
model.addAttribute("mode", "create");
return"products/form";
        }
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
dto.setUserId(user.getId());
try {
productService.create(dto, image);
redirect.addFlashAttribute("success", "Tạo sản phẩm thành công.");
return"redirect:/products";
        } catch (IllegalArgumentException e) {
result.reject("product.error", e.getMessage());
model.addAttribute("mode", "create");
return"products/form";
        }
    }
@GetMapping("/edit/{id}")
public String edit(@PathVariable Long id, Model model) {
model.addAttribute("productDTO", productService.findById(id));
model.addAttribute("mode", "edit");
return"products/form";
    }
@PostMapping("/edit/{id}")
public String edit(@PathVariable Long id,
@Valid@ModelAttribute ProductDTO dto,
                       BindingResult result,
@RequestParam(required = false) MultipartFile image,
                       Model model,
                       RedirectAttributes redirect) {
if (result.hasErrors()) {
```

```
model.addAttribute("mode", "edit");
return"products/form";
        }
//System.out.println("NAME = " + dto.getName());
// System.out.println("DESCRIPTION = " + dto.getDescription());
productService.update(id, dto, image);
redirect.addFlashAttribute("success", "Cập nhật sản phẩm thành công.");
return"redirect:/products";
    }
@PostMapping("/delete/{id}")
public String delete(@PathVariable Long id, RedirectAttributes redirect) {
productService.delete(id);
redirect.addFlashAttribute("success", "Xóa sản phẩm thành công.");
return"redirect:/products";
    }
}
```

============================================ 

```
package vn.iotstar.controller;
```

```
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.iotstar.service.ProductService;
import vn.iotstar.service.UserService;
```

```
@Controller
@RequiredArgsConstructor
publicclass HomeController {
privatefinal UserService userService;
privatefinal ProductService productService;
```

```
@GetMapping("/")
public String home(Model model) {
model.addAttribute("userCount", userService.countUsers());
model.addAttribute("productCount", productService.countProducts());
return"home";
    }
}
```

======================================= 

```
package vn.iotstar.controller;
```

```
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
```

```
@Controller
@RequestMapping("/error")
publicclass ErrorController{
public String error(Model model) {
model.addAttribute("message", "Đã xảy ra lỗi.");
```

**<mark>`return`</mark>** <mark>`"error"; } }`</mark> ============================== 

```
package vn.iotstar.controller;
```

```
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.iotstar.dto.*;
import vn.iotstar.service.AuthService;
import vn.iotstar.service.OtpService;
import vn.iotstar.dto.RegisterDTO;
@Controller
@RequiredArgsConstructor
publicclass AuthController {
privatefinal AuthService authService;
privatefinal OtpService otpService;
@GetMapping("/login")
public String login() { return"auth/login"; }
@GetMapping("/register")
public String register(Model model) {
model.addAttribute("registerDTO", new RegisterDTO());
return"auth/register";
    }
@PostMapping("/register")
public String register(@Valid@ModelAttribute RegisterDTO dto,
                           BindingResult result,
                           RedirectAttributes redirect) {
if (result.hasErrors()) return"auth/register";
try {
authService.register(dto);
redirect.addFlashAttribute("success", "OTP đã được gửi đến email.");
return"redirect:/verify-otp?email=" + dto.getEmail();
        } catch (IllegalArgumentException e) {
result.reject("register.error", e.getMessage());
return"auth/register";
        }
    }
@GetMapping("/verify-otp")
public String verifyPage(@RequestParam(required = false) String email, Model
model) {
        VerifyOtpDTO dto = new VerifyOtpDTO();
dto.setEmail(email);
model.addAttribute("verifyOtpDTO", dto);
```

```
return"auth/verify-otp";
    }
@PostMapping("/verify-otp")
public String verify(@Valid@ModelAttribute VerifyOtpDTO dto,
                         BindingResult result,
                         RedirectAttributes redirect) {
if (result.hasErrors()) return"auth/verify-otp";
if (!authService.verifyRegister(dto.getEmail(), dto.getOtp())) {
result.reject("otp.error", "OTP không hợp lệ, hết hạn hoặc đã quá số lần
thử.");
return"auth/verify-otp";
        }
redirect.addFlashAttribute("success", "Xác nhận thành công. Hãy đăng nhập.");
return"redirect:/login";
    }
@PostMapping("/resend-register-otp")
public String resend(@RequestParam String email, RedirectAttributes redirect) {
otpService.sendRegisterOtp(email);
redirect.addFlashAttribute("success", "Đã gửi lại OTP.");
return"redirect:/verify-otp?email=" + email;
    }
@GetMapping("/forgot-password")
public String forgot(Model model) {
model.addAttribute("forgotPasswordDTO", new ForgotPasswordDTO());
return"auth/forgot-password";
    }
@PostMapping("/forgot-password")
public String forgot(@Valid@ModelAttribute ForgotPasswordDTO dto,
                         BindingResult result,
                         RedirectAttributes redirect) {
if (result.hasErrors()) return"auth/forgot-password";
try {
authService.forgotPassword(dto.getEmail());
redirect.addFlashAttribute("email", dto.getEmail());
redirect.addFlashAttribute("success", "OTP đã được gửi.");
return"redirect:/reset-password";
        } catch (IllegalArgumentException e) {
result.reject("forgot.error", e.getMessage());
return"auth/forgot-password";
        }
    }
@GetMapping("/reset-password")
public String reset(Model model) {
        ResetPasswordDTO dto = new ResetPasswordDTO();
        Object email = model.asMap().get("email");
if (email != null) dto.setEmail(email.toString());
model.addAttribute("resetPasswordDTO", dto);
return"auth/reset-password";
    }
```

```
@PostMapping("/reset-password")
public String reset(@Valid@ModelAttribute ResetPasswordDTO dto,
                        BindingResult result,
@RequestParam String otp,
                        RedirectAttributes redirect) {
if (!dto.getPassword().equals(dto.getConfirmPassword())) {
result.reject("password.error", "Mật khẩu xác nhận không đúng.");
        }
if (result.hasErrors()) return"auth/reset-password";
if (!authService.verifyResetOtp(dto.getEmail(), otp)) {
result.reject("otp.error", "OTP không hợp lệ hoặc đã hết hạn.");
return"auth/reset-password";
        }
authService.resetPassword(dto.getEmail(), dto.getPassword());
redirect.addFlashAttribute("success", "Đổi mật khẩu thành công.");
return"redirect:/login";
    }
}
```

13. Tạo các security 

```
package vn.iotstar.security;
```

```
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vn.iotstar.entity.User;
import java.util.Collection;
import java.util.List;
@Getter
publicclass CustomUserDetails implements UserDetails {
privatestaticfinallongserialVersionUID = 1L;
privatefinal Long id;
privatefinal String username;
privatefinal String password;
privatefinalbooleanenabled;
privatefinal Collection<? extends GrantedAuthority> authorities;
public CustomUserDetails(User user) {
this.id = user.getId();
this.username = user.getUsername();
this.password = user.getPassword();
this.enabled = user.isEnabled();
this.authorities = List.of(
            (GrantedAuthority) () -> user.getRole().getName()
        );
    }
@Overridepublic Collection<? extends GrantedAuthority> getAuthorities() {
returnauthorities; }
@Overridepublic String getPassword() { returnpassword; }
```

```
@Overridepublic String getUsername() { returnusername; }
@Overridepublicboolean isEnabled() { returnenabled; }
}
```

```
=======================================
package vn.iotstar.security;
```

```
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import vn.iotstar.repository.UserRepository;
```

```
@Service
@RequiredArgsConstructor
publicclass CustomUserDetailsService implements UserDetailsService {
privatefinal UserRepository userRepository;
```

```
@Override
public UserDetails loadUserByUsername(String username) {
returnuserRepository.findByUsername(username)
            .map(CustomUserDetails::new)
            .orElseThrow(() ->
new UsernameNotFoundException("Username không tồn tại"));
    }
}
```

### 14. Tạo CSS trong thư mục static 

```
* { box-sizing: border-box; }
body { margin:0; font-family:Arial,sans-serif; background:#f5f7fb; color:#1f2937;
}
a { color:#2563eb; text-decoration:none; }
.header { min-height:64px; display:flex; align-items:center; gap:28px; padding:0
5%; background:#111827; color:white; }
.headera { color:white; }
.brand { font-weight:800; font-size:20px; }
.headernav { display:flex; gap:18px; flex:1; }
.account { display:flex; gap:12px; align-items:center; }
.container { max-width:1200px; margin:30pxauto; padding:020px; min-
height:calc(100vh - 150px); }
.footer { text-align:center; padding:25px; background:#111827; color:white; }
.cards { display:grid; grid-template-columns:repeat(2,minmax(200px,1fr));
gap:20px; }
.card,.auth-card,.form-card { background:white; border-radius:12px; padding:25px;
box-shadow:05px25pxrgba(0,0,0,.07); }
.cardstrong { font-size:36px; }
.toolbar { display:flex; justify-content:space-between; align-items:center;
margin:20px0; gap:15px; }
.toolbarform { display:flex; gap:8px; flex:1; }
input,textarea,select { width:100%; padding:11px; margin:5px012px; border:1px
solid#d1d5db; border-radius:7px; }
.toolbarinput { margin:0; max-width:600px; }
.button { display:inline-block; border:0; border-radius:7px; padding:10px16px;
background:#2563eb; color:white; cursor:pointer; }
.button.secondary { background:#6b7280; }
.button.full { width:100%; }
```

_<mark>`.link-button`</mark>_ <mark>`{ background:`</mark> _<mark>`none`</mark>_ <mark>`; border:`</mark> _<mark>`0`</mark>_ <mark>`; color:`</mark> _<mark>`white`</mark>_ <mark>`; cursor:`</mark> _<mark>`pointer`</mark>_ <mark>`; }`</mark> **<mark>`table`</mark>** <mark>`{ width:`</mark> _<mark>`100%`</mark>_ <mark>`; background:`</mark> _<mark>`white`</mark>_ <mark>`; border-collapse:`</mark> _<mark>`collapse`</mark>_ <mark>`; box-shadow:`</mark> _<mark>`0 3px 18px rgba(0,0,0,.05)`</mark>_ <mark>`; }`</mark> **<mark>`th,td`</mark>** <mark>`{ padding:`</mark> _<mark>`12px`</mark>_ <mark>`; border-bottom:`</mark> _<mark>`1px solid #e5e7eb`</mark>_ <mark>`; text-align:`</mark> _<mark>`left`</mark>_ <mark>`; verticalalign:`</mark> _<mark>`middle`</mark>_ <mark>`; }`</mark> **<mark>`th`</mark>** <mark>`{ background:`</mark> _<mark>`#f3f4f6`</mark>_ <mark>`; }`</mark> _<mark>`.actions`</mark>_ <mark>`{ display:`</mark> _<mark>`flex`</mark>_ <mark>`; gap:`</mark> _<mark>`10px`</mark>_ <mark>`; align-items:`</mark> _<mark>`center`</mark>_ <mark>`; }`</mark> _<mark>`.actions`</mark>_ **<mark>`form`</mark>** <mark>`{ margin:`</mark> _<mark>`0`</mark>_ <mark>`; }`</mark> _<mark>`.danger-link`</mark>_ <mark>`{ border:`</mark> _<mark>`0`</mark>_ <mark>`; background:`</mark> _<mark>`none`</mark>_ <mark>`; color:`</mark> _<mark>`#dc2626`</mark>_ <mark>`; cursor:`</mark> _<mark>`pointer`</mark>_ <mark>`; }`</mark> _<mark>`.pagination`</mark>_ <mark>`{ display:`</mark> _<mark>`flex`</mark>_ <mark>`; gap:`</mark> _<mark>`5px`</mark>_ <mark>`; justify-content:`</mark> _<mark>`center`</mark>_ <mark>`; margin:`</mark> _<mark>`25px`</mark>_ <mark>`; }`</mark> _<mark>`.pagination`</mark>_ **<mark>`a`</mark>** <mark>`{ padding:`</mark> _<mark>`8px 12px`</mark>_ <mark>`; border:`</mark> _<mark>`1px solid #ddd`</mark>_ <mark>`; border-radius:`</mark> _<mark>`5px`</mark>_ <mark>`; background:`</mark> _<mark>`white`</mark>_ <mark>`; }`</mark> _<mark>`.pagination`</mark>_ **<mark>`a`</mark>** _<mark>`.active`</mark>_ <mark>`{ background:`</mark> _<mark>`#2563eb`</mark>_ <mark>`; color:`</mark> _<mark>`white`</mark>_ <mark>`; }`</mark> _<mark>`.alert`</mark>_ <mark>`{ padding:`</mark> _<mark>`12px`</mark>_ <mark>`; border-radius:`</mark> _<mark>`7px`</mark>_ <mark>`; margin:`</mark> _<mark>`12px 0`</mark>_ <mark>`; }`</mark> _<mark>`.alert.success`</mark>_ <mark>`{ background:`</mark> _<mark>`#dcfce7`</mark>_ <mark>`; color:`</mark> _<mark>`#166534`</mark>_ <mark>`; }`</mark> _<mark>`.alert.error`</mark>_ <mark>`{ background:`</mark> _<mark>`#fee2e2`</mark>_ <mark>`; color:`</mark> _<mark>`#991b1b`</mark>_ <mark>`; }`</mark> _<mark>`.error-text`</mark>_ <mark>`{ color:`</mark> _<mark>`#dc2626`</mark>_ <mark>`; display:`</mark> _<mark>`block`</mark>_ <mark>`; margin-top:`</mark> _<mark>`-8px`</mark>_ <mark>`; margin-bottom:`</mark> _<mark>`8px`</mark>_ <mark>`; }`</mark> _<mark>`.auth-page`</mark>_ <mark>`{ min-height:`</mark> _<mark>`100vh`</mark>_ <mark>`; display:`</mark> _<mark>`grid`</mark>_ <mark>`; place-items:`</mark> _<mark>`center`</mark>_ <mark>`; padding:`</mark> _<mark>`30px`</mark>_ <mark>`; }`</mark> _<mark>`.auth-card`</mark>_ <mark>`{ width:`</mark> _<mark>`min(450px,100%)`</mark>_ <mark>`; }`</mark> _<mark>`.auth-card`</mark>_ **<mark>`h1`</mark>** <mark>`{ margin-top:`</mark> _<mark>`0`</mark>_ <mark>`; }`</mark> _<mark>`.form-card`</mark>_ <mark>`{ max-width:`</mark> _<mark>`700px`</mark>_ <mark>`; }`</mark> _<mark>`.thumb`</mark>_ <mark>`{ width:`</mark> _<mark>`70px`</mark>_ <mark>`; height:`</mark> _<mark>`70px`</mark>_ <mark>`; object-fit:`</mark> _<mark>`cover`</mark>_ <mark>`; border-radius:`</mark> _<mark>`7px`</mark>_ <mark>`; }`</mark> _<mark>`.preview`</mark>_ <mark>`{ width:`</mark> _<mark>`180px`</mark>_ <mark>`; max-height:`</mark> _<mark>`180px`</mark>_ <mark>`; object-fit:`</mark> _<mark>`cover`</mark>_ <mark>`; border-radius:`</mark> _<mark>`8px`</mark>_ <mark>`; margin-bottom:`</mark> _<mark>`15px`</mark>_ <mark>`; }`</mark> _<mark>`.quick-links`</mark>_ <mark>`{ display:`</mark> _<mark>`flex`</mark>_ <mark>`; gap:`</mark> _<mark>`12px`</mark>_ <mark>`; margin-top:`</mark> _<mark>`25px`</mark>_ <mark>`; }`</mark> _<mark>`.note`</mark>_ <mark>`{ padding:`</mark> _<mark>`12px`</mark>_ <mark>`; background:`</mark> _<mark>`#fff7ed`</mark>_ <mark>`; border-radius:`</mark> _<mark>`7px`</mark>_ <mark>`; }`</mark> _<mark>`.mt`</mark>_ <mark>`{ margin-top:`</mark> _<mark>`12px`</mark>_ <mark>`; } @media(max-width:700px) {`</mark> _<mark>`.header`</mark>_ <mark>`{ flex-wrap:`</mark> _<mark>`wrap`</mark>_ <mark>`; padding:`</mark> _<mark>`15px`</mark>_ <mark>`; }`</mark> _<mark>`.header`</mark>_ **<mark>`nav`</mark>** <mark>`{ order:`</mark> _<mark>`3`</mark>_ <mark>`; width:`</mark> _<mark>`100%`</mark>_ <mark>`; }`</mark> _<mark>`.cards`</mark>_ <mark>`{ grid-template-columns:`</mark> _<mark>`1fr`</mark>_ <mark>`; }`</mark> _<mark>`.toolbar`</mark>_ <mark>`{ flex-direction:`</mark> _<mark>`column`</mark>_ <mark>`; align-items:`</mark> _<mark>`stretch`</mark>_ <mark>`; }`</mark> **<mark>`table`</mark>** <mark>`{ display:`</mark> _<mark>`block`</mark>_ <mark>`; overflow-x:`</mark> _<mark>`auto`</mark>_ <mark>`; } }`</mark> 15. Tạo các views layouts 

```
<!DOCTYPEhtml>
<html
xmlns:th="http://www.thymeleaf.org">
<headth:fragment="head(title)">
<metacharset="UTF-8">
<metaname="viewport"
content="width=device-width, initial-scale=1">
<titleth:text="${title ?: 'IOTSTAR SHOP'}">
        IOTSTAR SHOP
</title>
<linkrel="stylesheet"
th:href="@{/css/app.css}">
</head>
```

<mark>`<body> <div th:fragment=`</mark> _<mark>`"page(content)"`</mark>_ <mark>`> <header th:replace=`</mark> _<mark>`"~{fragments/header :: header}"`</mark>_ <mark>`> </header> <main class=`</mark> _<mark>`"container"`</mark>_ <mark>`th:replace=`</mark> _<mark>`"${content}"`</mark>_ <mark>`> </main> <footer th:replace=`</mark> _<mark>`"~{fragments/footer :: footer}"`</mark>_ <mark>`> </footer> </div> </body> </html>`</mark> ================================================= 

<mark>`<!DOCTYPE html>`</mark> v Gusers <mark>`<html xmlns:th=`</mark> _<mark>`"http://www.thymeleaf.org"`</mark>_ <mark>`>` ) form.html</mark> <mark>`<body> <header th:fragment=`</mark> _<mark>`"header"`</mark>_ <mark>`class=`</mark> _<mark>`"header"`</mark>_ <mark>`>` G2 list.htm!</mark> <mark>`<a th:href=`</mark> _<mark>`"@{/}"`</mark>_ <mark>`class=`</mark> _<mark>`"brand"`</mark>_ <mark>`>IOTSTAR SHOP</a>` E) error.html</mark> <mark>`<nav>` E) home.html</mark> <mark>`<a th:href=`</mark> _<mark>`"@{/}"`</mark>_ <mark>`>Dashboard</a> <a th:href=`</mark> _<mark>`"@{/products}"`</mark>_ <mark>`>Products</a> <a th:if=`</mark> _<mark>`"${#authorization.expression('hasRole(''ADMIN'')')}"`</mark>_ <mark>`th:href=`</mark> _<mark>`"@{/users}"`</mark>_ <mark>`>Users</a> </nav> <div class=`</mark> _<mark>`"account"`</mark>_ <mark>`th:if=`</mark> _<mark>`"${#authorization.expression('isAuthenticated()')}"`</mark>_ <mark>`> <span th:text=`</mark> _<mark>`"${#authentication.name}"`</mark>_ <mark>`></span> <form th:action=`</mark> _<mark>`"@{/logout}"`</mark>_ <mark>`method=`</mark> _<mark>`"post"`</mark>_ <mark>`style="display:`</mark> _<mark>`inline`</mark>_ <mark>`"> <button type=`</mark> _<mark>`"submit"`</mark>_ <mark>`class=`</mark> _<mark>`"link-button"`</mark>_ <mark>`>Logout</button> </form> </div> <div th:unless=`</mark> _<mark>`"${#authorization.expression('isAuthenticated()')}"`</mark>_ <mark>`> <a th:href=`</mark> _<mark>`"@{/login}"`</mark>_ <mark>`>Login</a> </div> </header> </body> </html>`</mark> ============================================== 

```
<!DOCTYPEhtml>
<htmlxmlns:th="http://www.thymeleaf.org">
<body>
<footerth:fragment="footer"class="footer">
<span>© 2026 IOTSTAR SHOP - Spring Boot 4.1.1</span>
</footer>
</body>
</html>
```

============================================== 

16. Tạo các views contents 

```
home.html
```

```
<!DOCTYPEhtml>
<htmlxmlns:th="http://www.thymeleaf.org">
<headth:replace="~{layouts/layout :: head('Dashboard')}"></head>
<body>
<divth:replace="~{layouts/layout :: page(~{::main})}">
<main>
<h1>Dashboard</h1>
<divth:if="${success}"class="alert success"th:text="${success}"></div>
<divclass="cards">
<divclass="card"><h3>Total Users</h3><strong
th:text="${userCount}">0</strong></div>
<divclass="card"><h3>Total Products</h3><strong
th:text="${productCount}">0</strong></div>
</div>
<divclass="quick-links">
<aclass="button"th:href="@{/products}">Quản lý Products</a>
<aclass="button"
th:if="${#authorization.expression('hasRole(''ADMIN'')')}"
th:href="@{/users}">Quảnlý Users</a>
</div>
</main>
</div>
</body>
</html>
```

### <mark>error.html</mark> 

```
<!DOCTYPEhtml>
<htmlxmlns:th="http://www.thymeleaf.org">
<headth:replace="~{layouts/layout :: head('Error')}"></head>
<body>
<divclass="auth-page">
<divclass="auth-card">
<h1>Có lỗi xảy ra</h1>
<pth:text="${message}">Error</p>
<aclass="button"th:href="@{/}">Về trang chủ</a>
</div>
</div>
</body>
</html>
```

<mark>Auth/register.html</mark> 

```
<!DOCTYPEhtml>
<htmlxmlns:th="http://www.thymeleaf.org">
<headth:replace="~{layouts/layout :: head('Đăng ký')}"></head>
<body>
<divclass="auth-page">
<divclass="auth-card">
<h1>Đăng ký</h1>
```

```
<formth:action="@{/register}"th:object="${registerDTO}"
method="post">
<divth:if="${#fields.hasGlobalErrors()}"class="alert
error">
<pth:each="e : ${#fields.globalErrors()}"
th:text="${e}"></p>
</div>
<label>Username</label><inputth:field="*{username}">
<small
class="error-text"th:errors="*{username}"></small>
<label>Email</label>
<inputtype="email"th:field="*{email}"><small
class="error-text"th:errors="*{email}"></small>
<label>Họ
tên</label><inputth:field="*{fullName}"><small
class="error-text"th:errors="*{fullName}"></small>
<label>Password</label>
<inputtype="password"th:field="*{password}"><small
class="error-text"th:errors="*{password}"></small>
<label>Confirm
Password</label><inputtype="password"
```

```
th:field="*{confirmPassword}">
```

```
<smallclass="error-text"
```

```
th:errors="*{confirmPassword}"></small>
```

```
<buttonclass="button full"type="submit">Đăngký & nhận
OTP</button>
</form>
<p>
<ath:href="@{/login}">Đã có tài khoản?</a>
</p>
</div>
</div>
</body>
</html>
```

### <mark>Auth/login.html</mark> 

```
<!DOCTYPEhtml>
<htmlxmlns:th="http://www.thymeleaf.org">
<headth:replace="~{layouts/layout :: head('Đăng nhập')}"></head>
<body>
```

```
<divclass="auth-page">
```

```
<divclass="auth-card">
```

```
<h1>Đăng nhập</h1>
<divth:if="${param.error}"class="alert error">Username/password
không đúng hoặc tài khoản chưađược xác thực.</div>
<divth:if="${param.logout}"class="alert success">Bạn đã đăng
xuất.</div>
<divth:if="${success}"class="alert success"
th:text="${success}"></div>
<formth:action="@{/login}"method="post">
<label>Username</label><inputname="username"required
autofocus>
```

<mark>`<label>Password</label> <input type=`</mark> _<mark>`"password"`</mark>_ <mark>`name=`</mark> _<mark>`"password"`</mark>_ <mark>`required> <button class=`</mark> _<mark>`"button full"`</mark>_ <mark>`type=`</mark> _<mark>`"submit"`</mark>_ <mark>`>Đăng`</mark> <u><mark>`nhập</button>`</mark></u> <mark>`</form> <p> <a th:href=`</mark> _<mark>`"@{/register}"`</mark>_ <mark>`>Tạo tài khoản</a> </p> <p> <a th:href=`</mark> _<mark>`"@{/forgot-password}"`</mark>_ <mark>`>Quên mật khẩu?</a> </p> </div> </div> </body> </html>` Auth/reset-password.html</mark> 

<mark>`<!DOCTYPE html> <html xmlns:th=`</mark> _<mark>`"http://www.thymeleaf.org"`</mark>_ <mark>`> <head th:replace=`</mark> _<mark>`"~{layouts/layout :: head('Đặt lại mật khẩu')}"`</mark>_ <mark>`></head> <body> <div class=`</mark> _<mark>`"auth-page"`</mark>_ <mark>`> <div class=`</mark> _<mark>`"auth-card"`</mark>_ <mark>`> <form th:action=`</mark> _<mark>`"@{/reset-password}"`</mark>_ <mark>`th:object=`</mark> _<mark>`"${resetPasswordDTO}"`</mark>_ <mark>`method=`</mark> _<mark>`"post"`</mark>_ <mark>`> <h1>Đặt lại mật khẩu</h1> <div th:if=`</mark> _<mark>`"${success}"`</mark>_ <mark>`class=`</mark> _<mark>`"alert success"`</mark>_ <mark>`th:text=`</mark> _<mark>`"${success}"`</mark>_ <mark>`></div> <div th:if=`</mark> _<mark>`"${#fields.hasGlobalErrors()}"`</mark>_ <mark>`class=`</mark> _<mark>`"alert error"`</mark>_ <mark>`> <p th:each=`</mark> _<mark>`"e : ${#fields.globalErrors()}"`</mark>_ <mark>`th:text=`</mark> _<mark>`"${e}"`</mark>_ <mark>`></p> </div> <label>Email</label> <input type=`</mark> _<mark>`"email"`</mark>_ <mark>`th:field=`</mark> _<mark>`"*{email}"`</mark>_ <mark>`required> <label>OTP</label> <input name=`</mark> _<mark>`"otp"`</mark>_ <mark>`maxlength=`</mark> _<mark>`"6"`</mark>_ <mark>`required> <label>Mật khẩu`</mark> <u><mark>`mới</label>`</mark></u> <mark>`<input type=`</mark> _<mark>`"password"`</mark>_ <mark>`th:field=`</mark> _<mark>`"*{password}"`</mark>_ <mark>`required> <label>Xác`</mark> <u><mark>`nhận mật khẩu</label>`</mark></u> <mark>`<input type=`</mark> _<mark>`"password"`</mark>_ <mark>`th:field=`</mark> _<mark>`"*{confirmPassword}"`</mark>_ <mark>`required> <button class=`</mark> _<mark>`"button full"`</mark>_ <mark>`>Đổi mật khẩu</button> </form> </div> </div> </body> </html>` Auth/forgot-password.html</mark> 

<mark>`<!DOCTYPE html> <html xmlns:th=`</mark> _<mark>`"http://www.thymeleaf.org"`</mark>_ <mark>`> <head th:replace=`</mark> _<mark>`"~{layouts/layout :: head('Quên mật khẩu')}"`</mark>_ <mark>`></head> <body> <div class=`</mark> _<mark>`"auth-page"`</mark>_ <mark>`> <div class=`</mark> _<mark>`"auth-card"`</mark>_ <mark>`> <form th:action=`</mark> _<mark>`"@{/forgot-password}"`</mark>_ <mark>`th:object=`</mark> _<mark>`"${forgotPasswordDTO}"`</mark>_ <mark>`method=`</mark> _<mark>`"post"`</mark>_ <mark>`> <h1>Quên mật khẩu</h1> <div th:if=`</mark> _<mark>`"${#fields.hasGlobalErrors()}"`</mark>_ <mark>`class=`</mark> _<mark>`"alert error"`</mark>_ <mark>`> <p th:each=`</mark> _<mark>`"e : ${#fields.globalErrors()}"`</mark>_ <mark>`th:text=`</mark> _<mark>`"${e}"`</mark>_ <mark>`></p> </div> <label>Email</label> <input type=`</mark> _<mark>`"email"`</mark>_ <mark>`th:field=`</mark> _<mark>`"*{email}"`</mark>_ <mark>`required> <button class=`</mark> _<mark>`"button full"`</mark>_ <mark>`>Gửi OTP</button> </form> <p> <a th:href=`</mark> _<mark>`"@{/login}"`</mark>_ <mark>`>Quay lại Login</a> </p> </div> </div> </body> </html>` Auth/verify-otp.html</mark> 

```
<!DOCTYPEhtml>
<htmlxmlns:th="http://www.thymeleaf.org">
<headth:replace="~{layouts/layout :: head('Xác nhận OTP')}"></head>
<body>
<divclass="auth-page">
<divclass="auth-card">
<formth:action="@{/verify-otp}"th:object="${verifyOtpDTO}"
method="post">
<h1>Xác nhận OTP</h1>
<divth:if="${success}"class="alert success"
th:text="${success}"></div>
<divth:if="${#fields.hasGlobalErrors()}"class="alert
error">
<pth:each="e : ${#fields.globalErrors()}"
th:text="${e}"></p>
</div>
<label>Email</label><inputtype="email"
th:field="*{email}"
required><label>OTP 6 số</label><input
th:field="*{otp}"
maxlength="6"inputmode="numeric"required>
<buttonclass="button full">Xác nhận</button>
</form>
```

<mark>`<form th:action=`</mark> _<mark>`"@{/resend-register-otp}"`</mark>_ <mark>`method=`</mark> _<mark>`"post"`</mark>_ <mark>`class=`</mark> _<mark>`"mt"`</mark>_ <mark>`> <input type=`</mark> _<mark>`"hidden"`</mark>_ <mark>`name=`</mark> _<mark>`"email"`</mark>_ <mark>`th:value=`</mark> _<mark>`"${verifyOtpDTO.email}"`</mark>_ <mark>`> <button class=`</mark> _<mark>`"button secondary full"`</mark>_ <mark>`>Gửi lại OTP</button> </form> <p> <a th:href=`</mark> _<mark>`"@{/login}"`</mark>_ <mark>`>Quay lại Login</a> </p> </div> </div> </body> </html>` User/list.html</mark> 

```
<!DOCTYPEhtml>
<htmlxmlns:th="http://www.thymeleaf.org">
<headth:replace="~{layouts/layout :: head('Users')}"></head>
<body>
<divth:replace="~{layouts/layout :: page(~{::main})}">
<main>
<h1>Quản lý Users</h1>
<divth:if="${success}"class="alert success"
th:text="${success}"></div>
<divclass="toolbar">
<formth:action="@{/users}"method="get">
<inputname="keyword"th:value="${keyword}"
placeholder="Username, email, họ tên...">
<input
type="hidden"name="size"th:value="${size}">
<buttonclass="button">Tìm kiếm</button>
</form>
<aclass="button"th:href="@{/users/create}">+ Thêm
User</a>
</div>
<table>
<thead>
<tr>
<th>ID</th>
<th>Username</th>
<th>Email</th>
<th>Họ tên</th>
<th>Role</th>
<th>Enabled</th>
<th>Products</th>
<th>Action</th>
</tr>
</thead>
<tbody>
<trth:each="u : ${users.content}">
<tdth:text="${u.id}"></td>
<tdth:text="${u.username}"></td>
<tdth:text="${u.email}"></td>
<tdth:text="${u.fullName}"></td>
```

```
<tdth:text="${u.roleName}"></td>
<tdth:text="${u.enabled ? 'ACTIVE' :
```

```
'INACTIVE'}"></td>
```

```
<tdth:text="${u.productCount}"></td>
<tdclass="actions"><a
```

```
th:href="@{/users/edit/{id}(id=${u.id})}">Edit</a>
```

```
<form
```

```
th:action="@{/users/delete/{id}(id=${u.id})}"method="post"
```

```
onsubmit="return confirm('Xóa
```

```
user này?')">
```

```
<buttonclass="danger-
```

```
link">Delete</button>
```

```
</form></td>
```

```
</tr>
```

```
<trth:if="${users.empty}">
```

```
<tdcolspan="8">Không có dữ liệu.</td>
```

```
</tr>
```

```
</tbody>
```

```
</table>
<divclass="pagination">
```

```
<ath:if="${users.hasPrevious()}"
```

```
th:href="@{/users(page=${users.number-
```

```
1},size=${size},keyword=${keyword})}">«</a>
```

```
<span
```

```
th:each="i : ${#numbers.sequence(0, users.totalPages
>
<ath:classappend="${i == users.number ? 'active' :
```

```
> 0 ? users.totalPages - 1 : 0)}">
<a
''}"
```

```
th:href="@{/users(page=${i},size=${size},keyword=${keyword})}"
```

```
th:text="${i+1}"></a>
```

```
</span><ath:if="${users.hasNext()}"
```

```
th:href="@{/users(page=${users.number+1},size=${size},keyword=${keyword})}">»<
```

```
/a>
```

```
</div>
```

```
</main>
```

```
</div>
```

```
</body>
</html>
```

==================================== 

### <mark>User/form.html</mark> 

```
<!DOCTYPEhtml>
<htmlxmlns:th="http://www.thymeleaf.org">
<headth:replace="~{layouts/layout :: head(${mode == 'create' ? 'Thêm User' : 'Sửa
User'})}"></head>
<body>
<divth:replace="~{layouts/layout :: page(~{::main})}">
<main>
```

```
<formth:object="${userDTO}"method="post"enctype="multipart/form-data"accept-
charset="UTF-8"th:action="${mode == 'create'} ? @{/users/create} :
@{/users/edit/{id}(id=${userDTO.id})}">
<h1th:text="${mode == 'create' ? 'Thêm User' : 'Sửa User'}"></h1>
<label>Username</label>
<inputth:field="*{username}">
<smallclass="error-text"th:errors="*{username}"></small>
<label>Email</label>
<inputtype="email"th:field="*{email}">
<smallclass="error-text"th:errors="*{email}"></small>
<label>Họ tên</label>
<inputth:field="*{fullName}">
<smallclass="error-text"th:errors="*{fullName}"></small>
<label>Role</label>
<selectth:field="*{roleName}">
<optionvalue="ROLE_USER">USER</option>
<optionvalue="ROLE_ADMIN">ADMIN</option>
</select>
<label><inputtype="checkbox"th:field="*{enabled}"> Enabled</label>
<buttonclass="button">Lưu</button>
<aclass="button secondary"th:href="@{/users}">Hủy</a>
</form>
<pth:if="${mode == 'create'}"class="note">Mật khẩu mặc định của user do Admin tạo:
<b>123456</b>.</p>
</main>
</div>
</body>
</html>
```

================================== 

### <mark>Products/list.html</mark> 

```
<!DOCTYPEhtml>
<htmlxmlns:th="http://www.thymeleaf.org">
<headth:replace="~{layouts/layout :: head('Products')}">
</head>
<body>
<divth:replace="~{layouts/layout :: page(~{::main})}">
<main>
<h1>Quản lý Products</h1>
<divth:if="${success}"class="alert success"
th:text="${success}"></div>
<divclass="toolbar">
<formth:action="@{/products}"method="get">
<inputname="keyword"th:value="${keyword}"
placeholder="Tên sản phẩm, mô tả..."><input
type="hidden"
name="size"th:value="${size}">
<buttonclass="button">Tìm kiếm</button>
</form>
<aclass="button"th:href="@{/products/create}">+ Thêm
Product</a>
</div>
<table>
```

```
<thead>
<tr>
<th>ID</th>
<th>Ảnh</th>
<th>Name</th>
<th>Price</th>
<th>User</th>
<th>Action</th>
</tr>
</thead>
<tbody>
<trth:each="p : ${products.content}">
<tdth:text="${p.id}"></td>
<td><imgth:if="${p.imageUrl != null}"
```

```
th:src="${#strings.substringBefore(p.imageUrl, '|')}"
```



```
th:unless="${p.imageUrl != null}">No
```



```
th:text="${#numbers.formatDecimal(p.price, 0, 'COMMA', 2, 'POINT')}"></td>
<tdth:text="${p.username}"></td>
<tdclass="actions"><a
```

```
th:href="@{/products/edit/{id}(id=${p.id})}">Edit</a>
```



```
th:action="@{/products/delete/{id}(id=${p.id})}"
```

```
confirm('Xóa product này?')">
```

```
method="post"onsubmit="return
<buttonclass="danger-
```

```
link">Delete</button>
```





```
1},size=${size},keyword=${keyword})}">«</a>
```

```
<span
th:each="i : ${#numbers.sequence(0,
```

```
products.totalPages > 0 ? products.totalPages - 1 : 0)}">
```

```
: ''}"
```

```
<ath:classappend="${i == products.number ? 'active'
th:href="@{/products(page=${i},size=${size},keyword=${keyword})}"
th:text="${i+1}"></a>
</span><ath:if="${products.hasNext()}"
```

<mark>`th:href=`</mark> _<mark>`"@{/products(page=${products.number+1},size=${size},keyword=${keyword} )}"`</mark>_ <mark>`>»</a> </div> </main> </div> </body> </html>` Products/form.html</mark> 

```
<!DOCTYPEhtml>
<htmlxmlns:th="http://www.thymeleaf.org">
<head
th:replace="~{layouts/layout :: head(
        ${mode == 'create' ? 'Thêm Product' : 'Sửa Product'}
)}">
</head>
<body>
<divth:replace="~{layouts/layout :: page(~{::main})}">
<main>
<h1
th:text="${mode == 'create'
                        ? 'Thêm Product'
                        : 'Sửa Product'}">
</h1>
<formth:object="${productDTO}"
th:with="actionUrl=${mode == 'create'
                    ? '/products/create'
                    : '/products/edit/' + productDTO.id}"
th:action="${actionUrl}"method="post"
enctype="multipart/form-data"
accept-charset="UTF-8">
<!-- ID -->
<inputtype="hidden"th:field="*{id}">
<!-- Tên sản phẩm -->
<div>
<labelfor="name"> Tên sản phẩm </label><input
id="name"
type="text"th:field="*{name}"><small
class="error-text"
th:if="${#fields.hasErrors('name')}"
th:errors="*{name}">
</small>
```

```
</div>
<!-- Mô tả -->
<div>
<labelfor="description">Mô tả </label>
<textareaid="description"th:field="*{description}"
rows="5">
</textarea>
</div>
<!-- Giá -->
<div>
<labelfor="price">Giá </label><inputid="price"
type="number"
step="0.01"th:field="*{price}"><small
class="error-text"
th:if="${#fields.hasErrors('price')}"
th:errors="*{price}">
</small>
</div>
<!-- Ảnh -->
<div>
<labelfor="image">Ảnh </label><inputid="image"
type="file"
name="image"accept="image/*">
</div>
<!-- Preview ảnh hiện tại -->
<divth:if="${productDTO.imageUrl != null}">
<img
th:src="${#strings.substringBefore(
                                  productDTO.imageUrl, '|'
                              )}"
class="preview">
</div>
<!-- Button -->
<div>
<buttontype="submit"class="button">Lưu</button>
```

```
<aclass="button secondary"th:href="@{/products}">
Hủy </a>
```

```
</div>
```

```
</form>
```

```
</main>
```

```
</div>
</body>
</html>
```

17. Chạy project 

Kích phải project và thực hiện start 

Mở SQL server lên và tạo query để thêm Roles vào như sau: 

```
INSERT INTO roles (name)
SELECT'ROLE_USER'
WHERENOTEXISTS (SELECT1FROM roles WHEREname='ROLE_USER');
INSERT INTO roles (name)
SELECT'ROLE_ADMIN'
WHERENOTEXISTS (SELECT1FROM roles WHEREname='ROLE_ADMIN');
```

18. Kết quả 

Kích đúp vào project trong cửa sổ Boot Dashboard để mở project và test kết quả như sau: 



<!-- Start of picture text -->
> © cats OA 1<br>a e ° 6 ° 0 @ a<br>dashboard<br>Total Users Total Products<br>2 2<br>Quin Products<br>2028 IOTSTAR<br>SHOP - Spring Bot 41.1<br><!-- End of picture text -->



<!-- Start of picture text -->
Dangx ky,<br>Username<br>Email<br>Ho tén<br>Password<br>Confirm Password<br>Da co tai knoan?<br><!-- End of picture text -->



<!-- Start of picture text -->
Dang nhap<br>Username<br>Password<br>Tao tai khoan<br>Quén mat khdu?<br><!-- End of picture text -->



<!-- Start of picture text -->
Quén mat khau<br>Email<br>Quay lai Login<br><!-- End of picture text -->

Sau khi đăng ký và kích hoạt OTP xong thì tiến hành đăng nhập. 

> DQ @ tocaihose: 3 é m ° e oO a 

oe OA 1 



<!-- Start of picture text -->
a<br><!-- End of picture text -->

Dashboard 

Total Users Total Products 

2 

‘Quin Products 

© cshonts07od 

a b ° 6 

2 

2026 JOTSTAR SHOP - Spring Boot 4.1.1 

° 

OA 1 

e 

a 

#### Quan ly Products 



<!-- Start of picture text -->
a<br><!-- End of picture text -->

< Cc (] = @ localhost:8080/products/create 

88 5) Building an Angular @ DANH SACH DANG RubricDo_An_Mon... @ Quan ly Bé tai- 

IOTSTAR SHOP Dashboard Products 

Théma Product 

‘én san pham 

6 ta 

3ia 

nh 

Choose File | No file chosen 

Dey 



<!-- Start of picture text -->
< Cc (J)  @ _localhost:8080/products/edit/4<br>89 3) Building an Angular @ DANH SACH DANG. Rubric_Do_An_Mon. @ Quan ly be tai—t<br>IOTSTAR SHOP Dashboard Products<br>Stra> Product<br>Tén san pham<br>Dién thoai Oppo A95<br>M6 ta<br>Dién thoai Oppo A95<br>Gia<br>656565656.00<br>Anh<br>Choose File | No file chosen<br>i 8 S eo<br>eS ° Spt<br>fw To<br><!-- End of picture text -->

Khi đăng nhập với vai trò admin thì kết quả 



<!-- Start of picture text -->
> © estos 2 OA 7<br>a 6 ° a ° a 2 a<br>Dashboard<br>Total Users Total Products<br>2 2<br>Quin Procts JF auéniy Users<br>spring Boot 4.1.1<br>ame |<br>7 © cabot oA 1<br>a o ° 6 ° a e a<br>Quan ly Users<br><!-- End of picture text -->

