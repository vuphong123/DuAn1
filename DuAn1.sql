--Create database AoPhongDuAn
--GO
use AoPhongDuAn
--drop database AoPhongDuAn
go 
CREATE TABLE dbo.Salary
	(
	id int primary key NOT NULL IDENTITY (1, 1),
	salary_type nvarchar(100) NULL,
	salary_mount float(53) NULL,
	status nvarchar(10) NULL
	)
GO

CREATE TABLE dbo.Users
	(
	id int primary key NOT NULL IDENTITY (1, 1),
	username nvarchar(50) NULL,
	password nvarchar(MAX) NULL,
	role nvarchar(255) NULL
	)
GO

CREATE TABLE dbo.UserDetails
	(
	id int primary key NOT NULL IDENTITY (1, 1),
	user_id int NULL,
	salary_id int NULL,
	fullname nvarchar(100) NULL,
	gender nvarchar(10) NULL,
	tel nvarchar(12) NULL,
	email nvarchar(100) NULL,
	photo nvarchar(200) NULL,
	address nvarchar(200) NULL,
	birthdate datetime NULL,
	citizen_id nvarchar(20) NULL,
	job_position nvarchar(50) NULL,
	note nvarchar(200) NULL,
	status nvarchar(50) NULL,
	created_at datetime NULL
	)
GO

CREATE TABLE dbo.MAUSAC
	(
	id bigint primary key NOT NULL IDENTITY (1, 1),
	TenMauSac nvarchar(250) NULL,
	NgayTao datetime NULL,
	NgaySua datetime NULL
	)
GO

CREATE TABLE dbo.SIZE
	(
	id bigint primary key NOT NULL IDENTITY (1, 1),
	TenSize nvarchar(250) NULL,
	NgayTao date NULL,
	NgaySua date NULL
	) 
GO

CREATE TABLE dbo.VOUCHER
	(
	id bigint primary key NOT NULL IDENTITY (1, 1),
	TenPhieu nvarchar(200) NULL,
	DieuKienGiam float(53) NULL, 
	GiaTriGiam float(53) NULL,
	NgayBatDau datetime NULL,
	NgayKetThuc datetime NULL,
	NgayTao datetime NULL,
	SoLuong int NULL,
	TrangThai int NULL,
	PhamTramGiam float(53) NULL
	) 
GO

CREATE TABLE dbo.BILL
	(
	id nvarchar(250) primary key NOT NULL,
	TongTien float(53) NULL,
	id_NhanVien int null,
	id_KhachHang bigint null,
	TrangThai nvarchar(200) NULL,
	id_Voucher bigint NULL,
	TongSanPham int NULL,
	NgayTao datetime NULL,
	NgaySua datetime NULL,
	TienGiam float null,
	tienKhachTra float null,
	TongTienSauKhiGiam float null,
	TienTraLai float null,
	)
GO

CREATE TABLE dbo.KHACHHANG
	(
	id bigint primary key NOT NULL IDENTITY (1, 1),
	TenKhachHang nvarchar(250) NULL,
	sdt nvarchar(50) null,
	gioiTinh bit null,
	TrangThai bit NULL,
	NgayTao datetime NULL,
	NgaySua datetime NULL,
	Gmail nvarchar(250) null,
	)
GO

CREATE TABLE dbo.CHATLIEU
	(
	id bigint primary key NOT NULL IDENTITY (1, 1),
	TenVai nvarchar(250) NULL,
	TrangThai bit NULL,
	NgayTao datetime NULL,
	NgaySua datetime NULL
	)
GO

CREATE TABLE dbo.KIEUDANG
	(
	id bigint primary key NOT NULL IDENTITY (1, 1),
	TenKieuDang  nvarchar(250) NULL,
	TrangThai bit NULL,
	NgayTao datetime NULL,
	NgaySua datetime NULL
	)
GO

CREATE TABLE dbo.SANPHAM
	(
	id bigint primary key NOT NULL IDENTITY (1, 1),
	TenSanPham nvarchar(250) NULL,
	 MoTa nvarchar(max) null,
	TrangThai Nvarchar(50) NULL,
	NgayTao datetime NULL,
	NgaySua datetime NULL,
	SoLuong int null,
	)
go


CREATE TABLE dbo.SANPHAMCHITIET
	(
	id bigint primary key NOT NULL IDENTITY (1, 1),
	id_MauSac bigint NULL,
	id_Size bigint NULL,
	id_ChatLieu bigint null,
	id_SanPham bigint NULL,
	SoLuong int NULL,
	TrangThai bit NULL,
	NgayTao datetime NULL,
	NgaySua datetime NULL,
	GiaSanPham float(53) NULL,
    id_KieuDang bigint null,
	)
GO

CREATE TABLE dbo.BILLCHITIET
	(
	id bigint primary key NOT NULL IDENTITY (1, 1),
	id_Bill nvarchar(250) NULL,
	id_SanPhamChiTIet bigint NULL,
	SoLuongDaMua int NULL,
	ThanhTien float(53) NULL,
	GiaTienHienTai float(53) NULL,
	NgayTao datetime NULL,
	NgaySua datetime NULL
	) 
GO

CREATE TABLE dbo.DANHMUC
	(
	id bigint primary key NOT NULL IDENTITY (1, 1),
	TenDanhMuc nvarchar(250) NULL,
	TrangThai bit NULL,
	NgayTao datetime NULL,
	NgaySua datetime NULL
	)
GO

CREATE TABLE dbo.DANHMUCVASANPHAM
	(
	id_DanhMuc bigint NULL,
	id_SanPham bigint NULL
	)
GO

ALTER TABLE [UserDetails]
    ADD FOREIGN KEY ([user_id]) REFERENCES [Users] ([id])
GO
ALTER TABLE [UserDetails]
    ADD FOREIGN KEY ([salary_id]) REFERENCES [Salary] ([id])
GO
ALTER TABLE [BILL]
    ADD FOREIGN KEY ([id_Voucher]) REFERENCES [VOUCHER] ([id])
GO
ALTER TABLE [BILL]
    ADD FOREIGN KEY ([id_NhanVien]) REFERENCES [Users] ([id])
GO
ALTER TABLE [BILL]
    ADD FOREIGN KEY ([id_KhachHang]) REFERENCES [KHACHHANG] ([id])
GO
ALTER TABLE [SANPHAMCHITIET]
    ADD FOREIGN KEY ([id_MauSac]) REFERENCES [MAUSAC] ([id])
GO
ALTER TABLE [SANPHAMCHITIET]
    ADD FOREIGN KEY ([id_Size]) REFERENCES [SIZE] ([id])
GO
ALTER TABLE [SANPHAMCHITIET]
    ADD FOREIGN KEY ([id_SanPham]) REFERENCES [SANPHAM] ([id])
GO
ALTER TABLE [SANPHAMCHITIET]
    ADD FOREIGN KEY ([id_ChatLieu]) REFERENCES [CHATLIEU] ([id])
GO
ALTER TABLE [SANPHAMCHITIET]
    ADD FOREIGN KEY ([id_KieuDang]) REFERENCES [KIEUDANG] ([id])
GO
ALTER TABLE [BILLCHITIET]
    ADD FOREIGN KEY ([id_Bill]) REFERENCES [BILL] ([id])
GO
ALTER TABLE [BILLCHITIET]
    ADD FOREIGN KEY ([id_SanPhamChiTIet]) REFERENCES [SANPHAMCHITIET] ([id])
GO
ALTER TABLE [DANHMUCVASANPHAM]
    ADD FOREIGN KEY ([id_DanhMuc]) REFERENCES [DANHMUC] ([id])
GO
ALTER TABLE [DANHMUCVASANPHAM]
    ADD FOREIGN KEY ([id_SanPham]) REFERENCES [SANPHAM] ([id])
GO

INSERT INTO Salary (salary_type, salary_mount, [status])
VALUES ('Monthly salary', 8000000, 'Active'),
       ('Hourly wage', 21000, 'Active')
go

INSERT INTO [Users] ([username], [password], [role])
VALUES ('Admin', '$2a$10$roI7ElW8vMZ/aa/ndvev5ekg.szrhPLnsihszv5fyi1moKL5DNrN2', 'Admin'),
       ('vunhph33506', '$2a$10$ALO4bzEz7frQ0XHXyU3a/ehNCLg1MC2ROOWQNuoRs7tpNpwsYvVEO', 'User'),
       ('phongvvutuan', '$2a$10$roI7ElW8vMZ/aa/ndvev5ekg.szrhPLnsihszv5fyi1moKL5DNrN2', 'User'),
       ('nguyentranthanhdat', '$2a$10$roI7ElW8vMZ/aa/ndvev5ekg.szrhPLnsihszv5fyi1moKL5DNrN2', 'User'),
	   ('hoangquocbinh0411', '$2a$10$roI7ElW8vMZ/aa/ndvev5ekg.szrhPLnsihszv5fyi1moKL5DNrN2', 'User');
GO
INSERT INTO [UserDetails] ([user_id], [salary_id], [fullname], [gender], [tel], [email], [photo], [address], [birthdate], [citizen_id], [job_position], [note], [status])
VALUES
(1, 1, N'Admin', N'Male', N'1234567890', N'admin@admin.com', N'hen.png', N'Internet', '1990-01-01', N'123456789', N'Manager', N'This is a note for John Doe', N'Active'),
(2, 2, N'Nong Hoang Vu', N'Male', N'0345904585', N'vunhph33506@fpt.edu.vn', N'boy.png', N'My Dinh 2 - Ha Noi - VietNam', '2004-12-01', N'987654321', N'IT', N'This is a note for Nong Hoang Vu', N'Active'),
(3, 2, N'Vũ Tuấn Phong', N'Male', N' 0345129882', N'phongvvutuan@gmail.com', N'photo3.jpg', N'Null', '2004-08-17', N'555111222', N'Analyst', N'This is a note for Bob Johnson', N'Active'),
(4, 2, N'Nguyen Tran Thanh Dat', N'Female', N'0366097403 ', N' nguyentranthanhdat0000@gmail.com', N'photo4.jpg', N'Hoang Van Thu NamDinh', '2004-08-04', N'777888999', N'Developer', N'This is a note for Alice Brown', N'Active'),
(5, 1, N'Hoang Quoc Binh', N'Male', N'0937818716', N'hoangquocbinh0411@gmail.com', N'photo5.jpg', N'654 Birch St, State', '2003-11-04', N'999000111', N'Sales', N'This is a note for Michael White', N'Active');
GO

INSERT INTO dbo.MAUSAC (TenMauSac, NgayTao, NgaySua)
VALUES (N'màu đỏ', GETDATE(), GETDATE()),
       (N'màu xanh', GETDATE(), GETDATE()),
       (N'màu vàng', GETDATE(), GETDATE()),
       (N'màu trắng', GETDATE(), GETDATE()),
       (N'màu đen', GETDATE(), GETDATE());
GO

INSERT INTO dbo.KIEUDANG (TenKieuDang, TrangThai, NgayTao, NgaySua)
VALUES 
(N'Áo thun cổ tròn', 1, GETDATE(), GETDATE()),
(N'Áo thun cổ V', 1, GETDATE(), GETDATE()),
(N'Áo phông Oversize', 1, GETDATE(), GETDATE()),
(N'Áo phông có cổ ', 1, GETDATE(), GETDATE()),
(N'Áo phông Unisex', 1, GETDATE(), GETDATE());

INSERT INTO dbo.SIZE (TenSize, NgayTao, NgaySua)
VALUES ('Size S', GETDATE(), GETDATE()),
       ('Size M', GETDATE(), GETDATE()),
       ('Size L', GETDATE(), GETDATE()),
       ('Size XL', GETDATE(), GETDATE()),
       ('Size XXL', GETDATE(), GETDATE());
GO

--INSERT INTO NHANHIEU (TenThuongHieu, NgayTao, TrangThai) 
--VALUES
--('Nike', '1971-01-25', 'Hoạt động'),
--('Adidas', '1949-08-18', 'Hoạt động'),
--('Gucci', '1921-03-01', 'Hoạt động'),
--('Louis Vuitton', '1854-01-01', 'Hoạt động'),
--('Zara', '1975-05-24', 'Hoạt động');
--GO

INSERT INTO dbo.CHATLIEU (TenVai, TrangThai, NgayTao, NgaySua)
VALUES (N'Vải cotton', 1, GETDATE(), GETDATE()),
       (N'Vải polyester', 1, GETDATE(), GETDATE()),
       (N'Vải silk', 1, GETDATE(), GETDATE()),
       (N'Vải denim', 1, GETDATE(), GETDATE()),
       (N'Vải khaki', 1, GETDATE(), GETDATE());
GO

--1 Là đang diễn ra
--2 Là chưa bắt đầu
--3 Là đã kết thúc
INSERT INTO dbo.VOUCHER (TenPhieu, DieuKienGiam, NgayBatDau, NgayKetThuc, NgayTao, SoLuong, TrangThai, PhamTramGiam)
VALUES 
(N'Sale tưng bừng nhận quà không ngừng',10000.0, '2024-01-01', '2024-12-31', '2024-01-01', 2, 1, 10.0),
(N'Back to school',20000.0, '2024-01-02', '2024-12-31', '2024-01-02', 2, 1, 20.0),
(N'Black Friday',30000.0, '2024-01-03', '2024-12-31', '2024-01-03',2, 1, 25.0),
(N'Merry Chirsmart',400000.0, '2024-01-04', '2024-12-31', '2024-01-04',2, 1, 10.0),
(N'Happy New Year',50000.0, '2024-01-05', '2024-12-31', '2024-01-05',2, 1, 10.0);
GO

INSERT INTO dbo.KHACHHANG (TenKhachHang, sdt, gioiTinh, TrangThai, NgayTao, NgaySua, Gmail)
VALUES (N'Khách lẻ', null,0, 1, GETDATE(), GETDATE(), null),
       (N'Khách hàng 1', '02648273647', 1, 1, GETDATE(), GETDATE(), N'phongvtph33807@fpt.edu.vn'),
       (N'Khách hàng 2','048493728475', 1,  1, GETDATE(), GETDATE(), N'm48zack@gmail.com'),
       (N'Khách hàng 3','0283947462826', 1, 1, GETDATE(), GETDATE(), N'zacker048@gmail.com'),
       (N'Khách hàng 4','048593783748', 0, 1, GETDATE(), GETDATE(), N'nguyentranthanhdat2004@gmail.com'),
       (N'Khách hàng 5','094759374937', 0, 1, GETDATE(), GETDATE(), N'nhom02THShop@gmail.com');
GO

INSERT INTO dbo.BILL (id, TongTien, id_NhanVien, id_KhachHang, TrangThai, TienGiam, NgayTao, NgaySua, id_Voucher, TongSanPham, TongTienSauKhiGiam, TienTraLai)
VALUES ('Bill1', 100000, 1, 1, N'Chưa thanh toán', null,  GETDATE(), GETDATE(), 1, 5, 0, 0),
       ('Bill2', 200000, 2, 1, N'Chưa thanh toán', null, GETDATE(), GETDATE(), 2, 10, 0, 0),
       ('Bill3', 300000, 3, 2, N'Chưa thanh toán', null,GETDATE(), GETDATE(), 3, 15, 0, 0),
       ('Bill4', 400000, 4, 3, N'Chưa thanh toán', null,GETDATE(), GETDATE(), 4, 20, 0, 0),
       ('Bill5', 500000, 5, 4, N'Chưa thanh toán', null,GETDATE(), GETDATE(), 5, 25, 0, 0),
	   ('Bill6', 400000, 4, 3, N'Đã thanh toán', 0,'2024-01-12','2024-01-12', 4, 20, 200000, 0);
GO

INSERT INTO dbo.SANPHAM (TenSanPham, NgayTao, MoTa, TrangThai, NgaySua, SoLuong)
VALUES (N'Áo phông Supreme', GETDATE(), N'Áo phông Supreme nổi tiếng',N'Hoạt động', GETDATE(), 0),
       (N'Áo phông Gucci', GETDATE(), N'Áo phông Gucci nổi tiếng', N'Hoạt động', GETDATE(), 300),
       (N'Áo phông Balenciaga', GETDATE(), N'Áo phông Balenciaga nổi tiếng', N'Hoạt động', GETDATE(), 300),
       (N'Áo phông Off-White', GETDATE(), N'Áo phông Off-White nổi tiếng', N'Hoạt động', GETDATE(), 400),
       (N'Áo phông Stussy', GETDATE(), N'Áo phông Stussy nổi tiếng', N'Ngừng hoạt động', GETDATE(), 500);
GO

INSERT INTO dbo.SANPHAMCHITIET (id_MauSac, id_Size, id_ChatLieu, id_SanPham, SoLuong, TrangThai, NgayTao, NgaySua,  GiaSanPham, id_KieuDang)
VALUES (1, 1, 1,  2, 100, 0, GETDATE(), GETDATE() , 100000, 1),
       (2, 2, 2, 2, 200, 1, GETDATE(), GETDATE() , 200000, 2),
       (3, 3, 3, 3, 300, 1, GETDATE(), GETDATE(), 300000, 3),
       (4, 4, 1, 4, 400, 0, GETDATE(), GETDATE(), 400000, 4),
       (5, 5, 2, 5, 500, 0, GETDATE(), GETDATE(), 500000, 5);

INSERT INTO dbo.BILLCHITIET (id_Bill, id_SanPhamChiTiet, SoLuongDaMua, ThanhTien, GiaTienHienTai, NgayTao, NgaySua)
VALUES ('Bill1', 4, 5, 50000, 10000, GETDATE(), GETDATE()),
       ('Bill2', 2, 10, 100000, 20000, GETDATE(), GETDATE()),
       ('Bill3', 3, 15, 150000, 30000, GETDATE(), GETDATE()),
       ('Bill4', 4, 20, 200000, 40000, GETDATE(), GETDATE()),
       ('Bill5', 5, 25, 250000, 50000, GETDATE(), GETDATE()),
	   ('Bill6', 4, 20, 200000, 40000, '2024-01-12','2024-01-12');

INSERT INTO dbo.DANHMUC (TenDanhMuc, TrangThai, NgayTao, NgaySua)
VALUES 
('Danh Mục 1', 1, GETDATE(), GETDATE()),
('Danh Mục 2', 0, GETDATE(), GETDATE()),
('Danh Mục 3', 1, GETDATE(), GETDATE()),
('Danh Mục 4', 0, GETDATE(), GETDATE()),
('Danh Mục 5', 1, GETDATE(), GETDATE())
GO
INSERT INTO dbo.DANHMUCVASANPHAM (id_DanhMuc, id_SanPham)
VALUES 
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5)
GO



