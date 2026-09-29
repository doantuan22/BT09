# bai_tap_09_vd3

Project Spring Boot độc lập về source code và dùng database SQL Server riêng `bai_tap_09_vd3`. `.env` giữ các cấu hình SMTP, Cloudinary và cổng `8080` từ `bai_tap_09`, nhưng URL database trỏ riêng tới `bai_tap_09_vd3`.

Chạy bằng `mvn spring-boot:run` hoặc `mvnw.cmd spring-boot:run`. Ở lần khởi động đầu, Hibernate tạo/cập nhật schema trong database mới. `DataInitializer` thêm hai role, tài khoản demo và năm sản phẩm mẫu; các bản ghi seed không bị nhân đôi khi chạy lại.

## Tài khoản demo

| Vai trò | Username | Email | Mật khẩu |
| --- | --- | --- | --- |
| Admin | `admin` | `admin@bai_tap_09_bai_tap.test` | `Admin123!` |
| User | `user01` | `user01@bai_tap_09_bai_tap.test` | `User123!` |

Đây là thông tin demo cho môi trường local; đổi các biến `APP_ADMIN_*` và `APP_SAMPLE_USER_*` trong `.env` nếu cần. User do admin tạo từ màn hình quản lý vẫn dùng mật khẩu mặc định `123456` theo yêu cầu bài.

OTP có hiệu lực 5 phút và giới hạn 5 lần nhập. SMTP cần thiết cho luồng gửi/nhận OTP; Cloudinary cần thiết khi upload/xóa ảnh. `mvn test` dùng H2 và không kết nối SQL Server, SMTP hoặc Cloudinary thật.
