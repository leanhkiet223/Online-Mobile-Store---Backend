thêm dữ liệu zooooooooo:
use  mobile_store_online;
-- Thêm dữ liệu cho bảng color
INSERT INTO color (color) VALUES 
('Đen'), ('Trắng'), ('Vàng'), ('Bạc'), ('Xám Đen'), ('Hồng Vàng'), ('Xanh Đen Nửa Đêm'), ('Xanh Dương');

-- Thêm dữ liệu cho bảng storage
INSERT INTO storage (random_access_memory_value, random_access_memory_unit, read_only_memory_value, read_only_memory_unit) VALUES 
(4, 'GB', 64, 'GB'), (4, 'GB', 128, 'GB'), (4, 'GB', 256, 'GB'), (6, 'GB', 64, 'GB'), (6, 'GB', 128, 'GB'), (6, 'GB', 256, 'GB');
-- Thêm dữ liệu cho bảng category
-- Thêm dữ liệu cho bảng category (danh mục iPhone)
INSERT INTO category (name, description) VALUES
('iPhone 7', 'Danh mục chứa các sản phẩm thuộc dòng iPhone 7.'),
('iPhone 8', 'Danh mục chứa các sản phẩm thuộc dòng iPhone 8.'),
('iPhone XS', 'Danh mục chứa các sản phẩm thuộc dòng iPhone XS.'),
('iPhone 11', 'Danh mục chứa các sản phẩm thuộc dòng iPhone 11.'),
('iPhone 12', 'Danh mục chứa các sản phẩm thuộc dòng iPhone 12.'),
('iPhone 13', 'Danh mục chứa các sản phẩm thuộc dòng iPhone 13.'),
('iPhone 14', 'Danh mục chứa các sản phẩm thuộc dòng iPhone 14.'),
('iPhone 15', 'Danh mục chứa các sản phẩm thuộc dòng iPhone 15.');


-- Thêm dữ liệu cho bảng trademark
INSERT INTO trademark (name, description) VALUES 
('Apple', 'Công ty công nghệ nổi tiếng với dòng sản phẩm iPhone');
-- Thêm dữ liệu cho bảng product (iPhone) - Từ iPhone 7 trở lên
INSERT INTO product (name, price, quantity, description, state, percent_discount, color_id, storage_id, category_id, trademark_id) VALUES
('iPhone 7', 8990000, 60, 'iPhone 7 - Một trong những mô hình phổ biến nhất với thiết kế tinh tế, camera nâng cấp và hiệu suất ổn định. Trải nghiệm iOS đáng tin cậy.', 1, 0,8, 17, 6, 10),
('iPhone 7 Plus', 12990000, 50, 'iPhone 7 Plus - Phiên bản lớn với màn hình lớn, camera kép độ phân giải cao và pin dung lượng cao. Cho trải nghiệm giải trí tốt nhất.', 1, 0, 8, 17, 6, 10),
('iPhone 8', 11990000, 45, 'iPhone 8 - Mô hình tiếp theo với tính năng sạc không dây, camera chất lượng và hiệu suất tốt. Thiết kế sang trọng.', 1, 0, 8, 17, 7, 10),
('iPhone 8 Plus', 15990000, 40, 'iPhone 8 Plus - Phiên bản lớn của iPhone 8 với màn hình lớn và camera chất lượng cao. Cho trải nghiệm đa phương tiện mạnh mẽ.', 1, 0, 8, 17, 7, 10),
('iPhone X', 17990000, 35, 'iPhone X - Bước đột phá với màn hình OLED, thiết kế không viền và Face ID. Cho trải nghiệm người dùng hiện đại.', 1, 0, 8, 17, 8, 10),
('iPhone XS', 21990000 , 30, 'iPhone XS - Mô hình tiếp theo với hiệu suất mạnh mẽ, camera kép nâng cấp và màn hình OLED. Sự lựa chọn cao cấp.', 1, 0, 8, 17, 8, 10),
('iPhone XS Max', 24990000 , 25, 'iPhone XS Max - Phiên bản lớn nhất với màn hình OLED lớn, pin dung lượng cao và camera chất lượng. Điện thoại đa phương tiện hàng đầu.', 1, 0, 8, 17, 8, 10),
('iPhone XR', 16990000 , 50, 'iPhone XR - Sự kết hợp giữa hiệu suất ổn định và giá trị tốt. Màn hình Liquid Retina đẹp và camera chất lượng.', 1, 0, 8, 17, 8, 10),
('iPhone 11', 19990000 , 40, 'iPhone 11 - Mô hình nổi bật với hệ thống camera kép, pin dung lượng cao và hiệu suất mạnh mẽ. Sự lựa chọn tốt cho cả giải trí và công việc.', 1, 0, 8, 17, 9, 10),
('iPhone 11 Pro', 24990000, 35, 'iPhone 11 Pro - Điện thoại chuyên nghiệp với hệ thống camera ba cảm biến, màn hình OLED và hiệu suất ấn tượng. Cho người dùng đòi hỏi cao.', 1, 0, 8, 17, 9, 10),
('iPhone 11 Pro Max', 27990000 , 30, 'iPhone 11 Pro Max - Phiên bản lớn nhất với màn hình lớn, pin dung lượng cao và camera chất lượng. Sự lựa chọn cao cấp nhất của Apple.', 1, 0, 8, 17, 9, 10),
('iPhone 12', 23990000 , 25, 'iPhone 12 - Mô hình với thiết kế mới, hỗ trợ 5G và hiệu suất mạnh mẽ. Màn hình Super Retina XDR cho trải nghiệm tuyệt vời.', 1, 0, 8, 17, 10, 10),
('iPhone 12 Mini', 21990000 , 20, 'iPhone 12 Mini - Phiên bản nhỏ gọn với màn hình nhỏ, nhẹ và hiệu suất tốt. Dễ dàng mang theo và sử dụng một tay.', 1, 0, 8, 17, 10, 10),
('iPhone 12 Pro', 30990000 , 30, 'iPhone 12 Pro - Sự chuyên nghiệp với hệ thống camera nâng cấp, màn hình ProMotion và hiệu suất ấn tượng. Cho người dùng sáng tạo.', 1, 0, 8, 17, 10, 10),
('iPhone 12 Pro Max', 33990000 , 25, 'iPhone 12 Pro Max - Phiên bản lớn nhất với camera chất lượng cao, màn hình lớn và pin dung lượng cao. Điện thoại chụp ảnh xuất sắc.', 1, 0, 8, 17, 10, 10),
('iPhone 13 Mini', 22990000 , 15, 'iPhone 13 Mini - Mô hình nhỏ gọn nhưng mạnh mẽ với hiệu suất ổn định, camera cải tiến và màn hình Super Retina XDR.', 1, 0, 8, 17, 11, 10),
('iPhone 13', 27990000 , 20, 'iPhone 13 - Mô hình mới với thiết kế hiện đại, camera nâng cấp và hiệu suất mạnh mẽ. Trải nghiệm iOS tốt nhất.', 1, 0, 8, 17, 11, 10),
('iPhone 13 Pro', 30990000 , 15, 'iPhone 13 Pro - Sự chuyên nghiệp với hệ thống camera đa cảm biến, màn hình ProMotion và hiệu suất ấn tượng. Cho người dùng sáng tạo.', 1, 0, 8, 17, 11, 10),
('iPhone 13 Pro Max', 33990000 , 10, 'iPhone 13 Pro Max - Phiên bản lớn nhất với màn hình lớn, pin dung lượng cao và camera chất lượng. Điện thoại chụp ảnh xuất sắc.', 1, 0, 8, 17, 11, 10),
('iPhone 14', 35990000 , 25, 'iPhone 14 - Dòng sản phẩm tiếp theo với thiết kế đột phá và công nghệ tiên tiến. Màn hình đẹp và hiệu suất ấn tượng.', 1, 0, 8, 17, 12, 10),
('iPhone 14 Pro', 38990000 , 20, 'iPhone 14 Pro - Mô hình chuyên nghiệp với hệ thống camera tiên tiến, màn hình OLED và khả năng xử lý mạnh mẽ. Cho trải nghiệm sáng tạo.', 1, 0, 8, 17, 12, 10),
('iPhone 14 Pro Max', 41990000 , 15, 'iPhone 14 Pro Max - Phiên bản lớn nhất với màn hình lớn, pin dung lượng cao và camera chất lượng. Điện thoại chụp ảnh và quay video đỉnh cao.', 1, 0, 8, 17, 12, 10),
('iPhone 15', 44990000 , 10, 'iPhone 15 - Dòng sản phẩm đỉnh cao với công nghệ mới, camera vô cùng nâng cao và hiệu suất không giới hạn. Trải nghiệm di động hoàn hảo.', 1, 0, 8, 17, 13, 10);

