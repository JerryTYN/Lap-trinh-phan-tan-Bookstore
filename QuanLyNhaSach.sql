use master

create database PhanTanNhaSach
go
use PhanTanNhaSach
go

--nha cung cap
create table NhaCungCap
(
	maNCC varchar(255) not null,
	tenNCC nvarchar(255) ,
	diaChi nvarchar(255) ,
	soFax nvarchar(255) ,
PRIMARY KEY CLUSTERED 
(
	maNCC ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
--San pham
create table SanPham
(
	maSanPham varchar(255) not null,
	maNCC varchar(255) null,
	theloai nvarchar (255),
	phanLoai nvarchar(255)  ,
	TenSanPham nvarchar(255) ,
	soLuong int ,
	tacGia nvarchar(255),
	giaThanh money ,
PRIMARY KEY CLUSTERED 
(
	maSanPham ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO


--Khach hang 
create table KhachHang
(
	maKhachHang varchar(255) not null,
	tenKhachHang nvarchar(255) ,
	diaChi nvarchar(255)  ,
	gioiTinh bit  null,
	email varchar(255),
	ngaySinh datetime, 
	soDienThoai varchar(255),
PRIMARY KEY CLUSTERED 
(
	makhachhang ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
--Nhan vien
create table NhanVien 
(
	maNhanVien varchar(255) not null,
	tenNhanVien nvarchar(255), 
	email nvarchar(255)  ,
	gioiTinh bit  ,
	loaiNhanVien int ,
	matKhau varchar(255) ,
	ngaySinh datetime ,
	ngayVaoLam datetime,
	soDienThoai varchar(255),
	trangThai int ,
PRIMARY KEY CLUSTERED 
(
	maNhanVien ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
--Hoa do
create table HoaDon
(
	maHoaDon varchar(255) not null,
	maKhachHang varchar(255) ,
	maNhanVien varchar(255),
	ngayLap datetime
PRIMARY KEY CLUSTERED 
(
	maHoaDon ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
--chi tiet hoa don 
create table [dbo].[CTHoaDon]
(
	maSanPham varchar(255) not null,
	maHoaDon varchar(255) not null,
	donGia float,
	soLuong int ,
PRIMARY KEY CLUSTERED 
(
	maSanPham ASC,
	maHoaDon ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

SET ANSI_PADDING OFF
GO
ALTER TABLE SanPham  WITH CHECK ADD  CONSTRAINT [FK_SanPham_NhaCungCap] FOREIGN KEY([maNCC])
REFERENCES NhaCungCap (maNCC)
GO


ALTER TABLE HoaDon  WITH CHECK ADD  CONSTRAINT [FK_HoaDon_NhanVien] FOREIGN KEY(maNhanVien)
REFERENCES NhanVien (maNhanVien)
GO

ALTER TABLE HoaDon  WITH CHECK ADD  CONSTRAINT [FK_HoaDon_KhachHang] FOREIGN KEY(maKhachHang)
REFERENCES KhachHang (maKhachHang)
GO

ALTER TABLE [dbo].[CTHoaDon]  WITH CHECK ADD  CONSTRAINT [FK_ChiTietHoaDon_SanPham] FOREIGN KEY(maSanPham)
REFERENCES SanPham (maSanPham)
GO


ALTER TABLE [dbo].[CTHoaDon]  WITH CHECK ADD  CONSTRAINT [FK_ChiTietHoaDon_HoaDon] FOREIGN KEY(maHoaDon)
REFERENCES HoaDon (maHoaDon)
GO

use master
go
create login PhanTanNS
with password = 'nhasach',
default_database = [PhanTanNhaSach]
go

ALTER SERVER ROLE [sysadmin] ADD MEMBER PhanTanNS
GO

use [PhanTanNhaSach]
go
insert into [dbo].[NhanVien] values 
('NV01',N'Võ Thành nhớ','vothanhnho8005@gmail.com',1,1,'123456','2001-01-04','2021-08-28','0397485005',0),
('NV02',N'Nguyễn Đặng Hoàng Thi','hoangthi115@gmail.com',1,1,'123456','2001-03-06','2021-08-28','0397489635',0),
('NV03',N'Bùi Sĩ Sơn','sison113@gmail.com',1,0,'123456','2000-04-12','2021-08-28','0361255005',0),
('NV04',N'Nguyễn Tuấn Thanh','tuanthanh114@gmail.com',1,0,'123456','2001-05-03','2021-08-28','0397983005',0)
go 
INSERT [dbo].[KhachHang] ([maKhachHang], [tenKhachHang], [diaChi], [gioiTinh], [email], [ngaySinh], [soDienThoai]) VALUES (N'KH001', N'Võ Thành Luân', N'An Phú', 1, N'', CAST(N'1898-03-09T00:00:00.000' AS DateTime), N'0827192724')
INSERT [dbo].[KhachHang] ([maKhachHang], [tenKhachHang], [diaChi], [gioiTinh], [email], [ngaySinh], [soDienThoai]) VALUES (N'KH002', N'Trần Nhật Hoàng Anh', N'Thanh Hóa', 0, N'', CAST(N'1990-11-09T00:00:00.000' AS DateTime), N'0537191633')
INSERT [dbo].[KhachHang] ([maKhachHang], [tenKhachHang], [diaChi], [gioiTinh], [email], [ngaySinh], [soDienThoai]) VALUES (N'KH004', N'Nguyễn Thị Lan Hương', N'An Phú', 0, N'', CAST(N'2001-03-14T00:00:00.000' AS DateTime), N'0822719231')
INSERT [dbo].[KhachHang] ([maKhachHang], [tenKhachHang], [diaChi], [gioiTinh], [email], [ngaySinh], [soDienThoai]) VALUES (N'KH005', N'Võ Thành Nam', N'An Phú', 1, N'nam12345678@gmail.com', CAST(N'1898-03-09T00:00:00.000' AS DateTime), N'0827192724')
INSERT [dbo].[KhachHang] ([maKhachHang], [tenKhachHang], [diaChi], [gioiTinh], [email], [ngaySinh], [soDienThoai]) VALUES (N'KH006', N'Võ Thành Long', N'Hồ Chí Minh', 1, N'longdeptrai121@gmail.com', CAST(N'1999-03-09T00:00:00.000' AS DateTime), N'0827192893')
INSERT [dbo].[KhachHang] ([maKhachHang], [tenKhachHang], [diaChi], [gioiTinh], [email], [ngaySinh], [soDienThoai]) VALUES (N'KH007', N'Nguyễn Trần Tuấn', N'Óc Eo', 1, N'anhtuan2001@gmail.com', CAST(N'2001-09-11T00:00:00.000' AS DateTime), N'0782917297')
INSERT [dbo].[KhachHang] ([maKhachHang], [tenKhachHang], [diaChi], [gioiTinh], [email], [ngaySinh], [soDienThoai]) VALUES (N'KH008', N'Nguyễn Trần Anh Quân', N'Châu Thành', 1, N'aquan112@gmail.com', CAST(N'2001-09-28T00:00:00.000' AS DateTime), N'0782918996')
INSERT [dbo].[KhachHang] ([maKhachHang], [tenKhachHang], [diaChi], [gioiTinh], [email], [ngaySinh], [soDienThoai]) VALUES (N'KH009', N'Lê Thị Lành', N'Mỹ Tho', 0, N'', CAST(N'1999-09-28T00:00:00.000' AS DateTime), N'0735918971')
INSERT [dbo].[KhachHang] ([maKhachHang], [tenKhachHang], [diaChi], [gioiTinh], [email], [ngaySinh], [soDienThoai]) VALUES (N'KH011', N'Nguyễn Thị Yến Phượng', N'Gia Lai', 0, N'phuongyenthi@gmail.com', CAST(N'1989-06-11T00:00:00.000' AS DateTime), N'0735933371')
INSERT [dbo].[KhachHang] ([maKhachHang], [tenKhachHang], [diaChi], [gioiTinh], [email], [ngaySinh], [soDienThoai]) VALUES (N'KH012', N'Nguyễn Thị Kim Ánh', N'DakNong', 0, N'kimanhthi@gmail.com', CAST(N'1989-12-11T00:00:00.000' AS DateTime), N'0935932371')
INSERT [dbo].[KhachHang] ([maKhachHang], [tenKhachHang], [diaChi], [gioiTinh], [email], [ngaySinh], [soDienThoai]) VALUES (N'KH013', N'Đỗ Thị Thi', N'Đồng Tháp', 0, N'', CAST(N'2000-12-20T00:00:00.000' AS DateTime), N'0935932234')
INSERT [dbo].[KhachHang] ([maKhachHang], [tenKhachHang], [diaChi], [gioiTinh], [email], [ngaySinh], [soDienThoai]) VALUES (N'KH014', N'Lê Ngọc Ánh', N'Đồng Tháp', 0, N'adaolengoc@gmail.com', CAST(N'1975-12-21T00:00:00.000' AS DateTime), N'0935932222')
INSERT [dbo].[KhachHang] ([maKhachHang], [tenKhachHang], [diaChi], [gioiTinh], [email], [ngaySinh], [soDienThoai]) VALUES (N'KH015', N'Lê Ngọc Anh Đào', N'Tiền Giang', 0, N'', CAST(N'1988-07-21T00:00:00.000' AS DateTime), N'0935937898')
INSERT [dbo].[KhachHang] ([maKhachHang], [tenKhachHang], [diaChi], [gioiTinh], [email], [ngaySinh], [soDienThoai]) VALUES (N'KH016', N'Mai Thành Long', N'Tiền Giang', 1, N'longlonglong@gmail.com', CAST(N'1999-08-22T00:00:00.000' AS DateTime), N'0885937896')
INSERT [dbo].[KhachHang] ([maKhachHang], [tenKhachHang], [diaChi], [gioiTinh], [email], [ngaySinh], [soDienThoai]) VALUES (N'KH017', N'Nguyễn Thành Phát', N'Hậu Giang', 1, N'thanhphatbede@gmail.com', CAST(N'2002-08-01T00:00:00.000' AS DateTime), N'0885232111')
INSERT [dbo].[KhachHang] ([maKhachHang], [tenKhachHang], [diaChi], [gioiTinh], [email], [ngaySinh], [soDienThoai]) VALUES (N'KH018', N'Nguyễn Tuấn Thanh', N'Tiền Giang', 1, N'thanhdi2121@gmail.com', CAST(N'2001-12-08T00:00:00.000' AS DateTime), N'0374779028')
INSERT [dbo].[KhachHang] ([maKhachHang], [tenKhachHang], [diaChi], [gioiTinh], [email], [ngaySinh], [soDienThoai]) VALUES (N'KH019', N'Bùi Sỹ Sơn', N'Trà Vinh', 1, N'', CAST(N'2000-03-08T00:00:00.000' AS DateTime), N'0991829028')
INSERT [dbo].[KhachHang] ([maKhachHang], [tenKhachHang], [diaChi], [gioiTinh], [email], [ngaySinh], [soDienThoai]) VALUES (N'KH020', N'Lê Tuấn', N'Bình Định', 1, N'tuanl6969@gmail.com', CAST(N'2001-03-12T00:00:00.000' AS DateTime), N'0991829888')
INSERT [dbo].[KhachHang] ([maKhachHang], [tenKhachHang], [diaChi], [gioiTinh], [email], [ngaySinh], [soDienThoai]) VALUES (N'KH022', N'Tăng Bảo Trấn', N'Trà Vinh', 1, N'', CAST(N'2001-06-11T00:00:00.000' AS DateTime), N'0991828551')
INSERT [dbo].[KhachHang] ([maKhachHang], [tenKhachHang], [diaChi], [gioiTinh], [email], [ngaySinh], [soDienThoai]) VALUES (N'KH023', N'Võ Minh Phương', N'Bến Tre', 1, N'phuongl9696@gmail.com', CAST(N'2001-06-11T00:00:00.000' AS DateTime), N'0667890098')
INSERT [dbo].[KhachHang] ([maKhachHang], [tenKhachHang], [diaChi], [gioiTinh], [email], [ngaySinh], [soDienThoai]) VALUES (N'KH024', N'Trần Thị Anh Loan', N'Hồ Chí Minh', 0, N'', CAST(N'1989-11-28T00:00:00.000' AS DateTime), N'0723456000')
INSERT [dbo].[KhachHang] ([maKhachHang], [tenKhachHang], [diaChi], [gioiTinh], [email], [ngaySinh], [soDienThoai]) VALUES (N'KH025', N'Trần Thị Bích', N'Hồ Chí Minh', 0, N'', CAST(N'1989-11-28T00:00:00.000' AS DateTime), N'0898456000')
INSERT [dbo].[KhachHang] ([maKhachHang], [tenKhachHang], [diaChi], [gioiTinh], [email], [ngaySinh], [soDienThoai]) VALUES (N'KH026', N'Trần Thị Bích Nhi', N'Hồ Chí Minh', 0, N'', CAST(N'2001-07-11T00:00:00.000' AS DateTime), N'0898456011')
INSERT [dbo].[KhachHang] ([maKhachHang], [tenKhachHang], [diaChi], [gioiTinh], [email], [ngaySinh], [soDienThoai]) VALUES (N'KH028', N'Võ Phước Lưu', N'Châu Thành B', 1, N'vpluuu96@gmail.com', CAST(N'2007-11-15T00:00:00.000' AS DateTime), N'0999888222')
INSERT [dbo].[KhachHang] ([maKhachHang], [tenKhachHang], [diaChi], [gioiTinh], [email], [ngaySinh], [soDienThoai]) VALUES (N'KH029', N'Lê Nguyễn Việt Khải', N'Gò Công', 1, N'', CAST(N'1996-11-15T00:00:00.000' AS DateTime), N'0777383838')
INSERT [dbo].[KhachHang] ([maKhachHang], [tenKhachHang], [diaChi], [gioiTinh], [email], [ngaySinh], [soDienThoai]) VALUES (N'KH031', N'Hoàng Thi', N'Hòa An', 1, N'', NULL, N'0897581552')
INSERT [dbo].[KhachHang] ([maKhachHang], [tenKhachHang], [diaChi], [gioiTinh], [email], [ngaySinh], [soDienThoai]) VALUES (N'KH032', N'Lê Văn Lân', N'Óc Eo', 1, N'', CAST(N'2001-09-11T00:00:00.000' AS DateTime), N'0782917294')
INSERT [dbo].[KhachHang] ([maKhachHang], [tenKhachHang], [diaChi], [gioiTinh], [email], [ngaySinh], [soDienThoai]) VALUES (N'KH033', N'Nguyễn Văn Đạt', N'Óc Eo1', 1, N'', CAST(N'2001-09-11T00:00:00.000' AS DateTime), N'0782917292')
INSERT [dbo].[KhachHang] ([maKhachHang], [tenKhachHang], [diaChi], [gioiTinh], [email], [ngaySinh], [soDienThoai]) VALUES (N'KH034', N'Nguyễn Thị Hồng', N'Trà Ôn', 0, N'btn028abc1@gmail.com', CAST(N'2001-09-11T00:00:00.000' AS DateTime), N'0971811272')
INSERT [dbo].[KhachHang] ([maKhachHang], [tenKhachHang], [diaChi], [gioiTinh], [email], [ngaySinh], [soDienThoai]) VALUES (N'KH035', N'Võ Huy Ân', N'Châu Đốc', 1, N'huyan0220@gmail.com', CAST(N'2002-04-16T00:00:00.000' AS DateTime), N'0787657775')
INSERT [dbo].[KhachHang] ([maKhachHang], [tenKhachHang], [diaChi], [gioiTinh], [email], [ngaySinh], [soDienThoai]) VALUES (N'KH036', N'Nguyễn Thị Hồng', N'Trà Ôn', 0, N'', CAST(N'2001-09-11T00:00:00.000' AS DateTime), N'0971811277')
INSERT [dbo].[KhachHang] ([maKhachHang], [tenKhachHang], [diaChi], [gioiTinh], [email], [ngaySinh], [soDienThoai]) VALUES (N'KH037', N'Nguyễn Văn Hoàng', N'Hòa Lạc', 1, N'', CAST(N'2010-11-08T00:00:00.000' AS DateTime), N'0876678876')
go
INSERT [dbo].[NhaCungCap] ([maNCC], [tenNCC], [diaChi], [soFax]) VALUES (N'NCC001', N'Thiên Long', N'Lô 6-8-10-12, Đường số 3, KCN Tân Tạo, Q.Bình Tân, TP. Hồ Chí Minh', N'0934550045')
INSERT [dbo].[NhaCungCap] ([maNCC], [tenNCC], [diaChi], [soFax]) VALUES (N'NCC002', N'Kim Đồng', N'Số 55 Quang Trung, Nguyễn Du, Hai Bà Trưng, Hà Nội', N'0934536971')
INSERT [dbo].[NhaCungCap] ([maNCC], [tenNCC], [diaChi], [soFax]) VALUES (N'NCC003', N'Thanh Niên ', N' 52 Út Tịch, phường 5, quận Tân Bình, thành phố Hồ Chí Minh', N'0934536971')
INSERT [dbo].[NhaCungCap] ([maNCC], [tenNCC], [diaChi], [soFax]) VALUES (N'NCC004', N'Nhà Xuất Bản TP Hồ Chí Minh', N'12, Nguyễn Văn Bảo, Gò Vấp, TP Hồ Chí', N'0345678900')
INSERT [dbo].[NhaCungCap] ([maNCC], [tenNCC], [diaChi], [soFax]) VALUES (N'NCC005', N'Nhà Xuất Bản Hồng Đức', N'65 Tràng Thi, Hàng Bông, Hoàn Kiếm, Hà Nội', N'0127631812')
INSERT [dbo].[NhaCungCap] ([maNCC], [tenNCC], [diaChi], [soFax]) VALUES (N'NCC006', N'Nhà Xuất Bản Hà Nội', N'Số 4 Tống Duy Tân, Hàng Bông, Hoàn Kiếm, Hà Nội', N'0243045406')
INSERT [dbo].[NhaCungCap] ([maNCC], [tenNCC], [diaChi], [soFax]) VALUES (N'NCC007', N'Nhà xuất bản Trẻ', N'161B Lý Chính Thắng, Phường 7, Quận 3, Thành phố Hồ Chí Minh', N'0283843745')
INSERT [dbo].[NhaCungCap] ([maNCC], [tenNCC], [diaChi], [soFax]) VALUES (N'NCC008', N'Hồng Hà', N'Số 55 Quang Trung, Nguyễn Du, Hai Bà Trưng, Hà Nội', N'0126731282')
INSERT [dbo].[NhaCungCap] ([maNCC], [tenNCC], [diaChi], [soFax]) VALUES (N'NCC009', N'Kite', N'Số 3, ngõ 514, ngách 43, đường Thụy Khuê, Phường Bưởi, Quận Tây Hồ, Thành phố Hà Nội, Việt Nam', N'0128369280')
INSERT [dbo].[NhaCungCap] ([maNCC], [tenNCC], [diaChi], [soFax]) VALUES (N'NCC010', N'Campus', N'Số 10 Đường 29, Chợ An Dương Vương, P. 10, Q. 6,Tp. Hồ Chí Minh', N'0192739882')
INSERT [dbo].[NhaCungCap] ([maNCC], [tenNCC], [diaChi], [soFax]) VALUES (N'NCC011', N'Công ty Văn Hóa Sáng Tạo Trí Việt - First News', N'11H, Nguyễn Thị Minh Khai, quận 1, TP Hồ Chí Minh', N'0838227979')
INSERT [dbo].[NhaCungCap] ([maNCC], [tenNCC], [diaChi], [soFax]) VALUES (N'NCC012', N'Nhà xuất bản Giáo Dục Việt Nam', N'81, Trần Hưng Đạo, Hoàn Kiếm,  Hà Nội', N'0243942201')
INSERT [dbo].[NhaCungCap] ([maNCC], [tenNCC], [diaChi], [soFax]) VALUES (N'NCC013', N'Công ty TNHH SV&Tm Tân Tiến', N'15, Đoàn Văn Phối, Phường Mỹ Long, TP.Long Xuyên, An Giang', N'0763933499')
INSERT [dbo].[NhaCungCap] ([maNCC], [tenNCC], [diaChi], [soFax]) VALUES (N'NCC014', N'Nhà xuất bản Báo Tân Dân', N'93 Hàng Bông', N'0765435567')
GO

insert into [dbo].[HoaDon] values
('00000001','KH001','NV01','2021-09-25')
go
INSERT [dbo].[SanPham] ([maSanPham], [maNCC], [theloai], [phanLoai], [TenSanPham], [soLuong], [tacGia], [giaThanh]) VALUES (N'SP001', N'NCC001', N'', N'Dụng cụ học tập', N'Bút Chì 2B', 16, N'', 7000.0000)
INSERT [dbo].[SanPham] ([maSanPham], [maNCC], [theloai], [phanLoai], [TenSanPham], [soLuong], [tacGia], [giaThanh]) VALUES (N'SP002', N'NCC001', N'', N'Dụng cụ học tập', N'Thước Kẻ Nhựa 20CM', 50, N'', 7000.0000)
INSERT [dbo].[SanPham] ([maSanPham], [maNCC], [theloai], [phanLoai], [TenSanPham], [soLuong], [tacGia], [giaThanh]) VALUES (N'SP003', N'NCC002', N'Văn học Việt Nam', N'Sách', N'Truyện Kiều', 8, N'Nguyễn Du', 48000.0000)
INSERT [dbo].[SanPham] ([maSanPham], [maNCC], [theloai], [phanLoai], [TenSanPham], [soLuong], [tacGia], [giaThanh]) VALUES (N'SP004', N'NCC011', N'Sách Tự Lực', N'Sách', N'Đắc Nhân Tâm', 56, N'Dale Carnegie', 74000.0000)
INSERT [dbo].[SanPham] ([maSanPham], [maNCC], [theloai], [phanLoai], [TenSanPham], [soLuong], [tacGia], [giaThanh]) VALUES (N'SP005', N'NCC006', N'Văn học Việt Nam', N'Sách', N'Mắt Biếc', 179, N'Nguyễn Nhật Ánh', 70000.0000)
INSERT [dbo].[SanPham] ([maSanPham], [maNCC], [theloai], [phanLoai], [TenSanPham], [soLuong], [tacGia], [giaThanh]) VALUES (N'SP006', N'NCC004', N'Sách Giáo Khoa', N'Sách', N'Sách Giáo Khoa Tiếng Việt Lớp 1  Tập 2', 178, N'Nhiều Tác Giả', 13000.0000)
INSERT [dbo].[SanPham] ([maSanPham], [maNCC], [theloai], [phanLoai], [TenSanPham], [soLuong], [tacGia], [giaThanh]) VALUES (N'SP008', N'NCC008', N'Văn Học Nước Ngoài', N'Sách', N'Đồi Gió Hú', 198, N'Emily Brontë', 87000.0000)
INSERT [dbo].[SanPham] ([maSanPham], [maNCC], [theloai], [phanLoai], [TenSanPham], [soLuong], [tacGia], [giaThanh]) VALUES (N'SP009', N'NCC012', N'Sách Giáo Khoa', N'Sách', N'Sách Đại Số Lớp 10', 197, N'Nhiều Tác Giả', 10000.0000)
INSERT [dbo].[SanPham] ([maSanPham], [maNCC], [theloai], [phanLoai], [TenSanPham], [soLuong], [tacGia], [giaThanh]) VALUES (N'SP010', N'NCC012', N'Sách giáo khoa', N'Sách', N'Sách Hình Học 10', 188, N'Nhiều Tác Giả', 10000.0000)
INSERT [dbo].[SanPham] ([maSanPham], [maNCC], [theloai], [phanLoai], [TenSanPham], [soLuong], [tacGia], [giaThanh]) VALUES (N'SP011', N'NCC012', N'Sách Giáo Khoa', N'Sách', N'Sách Đại Số Và Giải Tích 11', 198, N'Nhiều Tác Giả', 8000.0000)
INSERT [dbo].[SanPham] ([maSanPham], [maNCC], [theloai], [phanLoai], [TenSanPham], [soLuong], [tacGia], [giaThanh]) VALUES (N'SP012', N'NCC012', N'Sách Giáo Khoa', N'Sách', N'Sách Giải Tích 12', 197, N'Nhiều Tác Giả', 8000.0000)
INSERT [dbo].[SanPham] ([maSanPham], [maNCC], [theloai], [phanLoai], [TenSanPham], [soLuong], [tacGia], [giaThanh]) VALUES (N'SP013', N'NCC012', N'Sách Giáo Khoa', N'Sách', N'Sách Hình Học 11', 199, N'Nhiều Tác Giả', 8000.0000)
INSERT [dbo].[SanPham] ([maSanPham], [maNCC], [theloai], [phanLoai], [TenSanPham], [soLuong], [tacGia], [giaThanh]) VALUES (N'SP014', N'NCC012', N'Sách Giáo Khoa', N'Sách', N'Sách Hình Học 12', 200, N'Nhiều Tác Giả', 6000.0000)
INSERT [dbo].[SanPham] ([maSanPham], [maNCC], [theloai], [phanLoai], [TenSanPham], [soLuong], [tacGia], [giaThanh]) VALUES (N'SP015', N'NCC012', N'Sách Giáo Khoa', N'Sách', N'Sách Ngữ Văn 10 Tập 1', 199, N'Nhiều Tác Giả', 8500.0000)
INSERT [dbo].[SanPham] ([maSanPham], [maNCC], [theloai], [phanLoai], [TenSanPham], [soLuong], [tacGia], [giaThanh]) VALUES (N'SP016', N'NCC012', N'Sách Giáo Khoa', N'Sách', N'Sách Ngữ Văn 10 Tập 2', 200, N'Nhiều Tác Giả', 11000.0000)
INSERT [dbo].[SanPham] ([maSanPham], [maNCC], [theloai], [phanLoai], [TenSanPham], [soLuong], [tacGia], [giaThanh]) VALUES (N'SP017', N'NCC012', N'Sách Giáo Khoa', N'Sách', N'Sách Ngữ Văn 11 Tập 1', 199, N'Nhiều Tác Giả', 8500.0000)
INSERT [dbo].[SanPham] ([maSanPham], [maNCC], [theloai], [phanLoai], [TenSanPham], [soLuong], [tacGia], [giaThanh]) VALUES (N'SP018', N'NCC012', N'Sách Giáo Khoa', N'Sách', N'Sách Ngữ Văn 11 Tập 2', 200, N'Nhiều Tác Giả', 8500.0000)
INSERT [dbo].[SanPham] ([maSanPham], [maNCC], [theloai], [phanLoai], [TenSanPham], [soLuong], [tacGia], [giaThanh]) VALUES (N'SP019', N'NCC012', N'Sách Giáo Khoa', N'Sách', N'Sách Ngữ Văn 12 Tập 1', 200, N'Nhiều Tác Giả', 11000.0000)
INSERT [dbo].[SanPham] ([maSanPham], [maNCC], [theloai], [phanLoai], [TenSanPham], [soLuong], [tacGia], [giaThanh]) VALUES (N'SP020', N'NCC012', N'Sách Giáo Khoa', N'Sách', N'Sách Ngữ Văn 12 Tập 2', 200, N'Nhiều Tác Giả', 11000.0000)
INSERT [dbo].[SanPham] ([maSanPham], [maNCC], [theloai], [phanLoai], [TenSanPham], [soLuong], [tacGia], [giaThanh]) VALUES (N'SP021', N'NCC012', N'Sách Giáo Khoa', N'Sách', N'Sách Tiếng Anh 10', 200, N'Nhiều Tác Giả', 15000.0000)
INSERT [dbo].[SanPham] ([maSanPham], [maNCC], [theloai], [phanLoai], [TenSanPham], [soLuong], [tacGia], [giaThanh]) VALUES (N'SP022', N'NCC012', N'Sách Giáo Khoa', N'Sách', N'Sách Ngữ Văn 12 Tập 2', 200, N'Nhiều Tác Giả', 11000.0000)
INSERT [dbo].[SanPham] ([maSanPham], [maNCC], [theloai], [phanLoai], [TenSanPham], [soLuong], [tacGia], [giaThanh]) VALUES (N'SP023', N'NCC012', N'Sách Giáo Khoa', N'Sách', N'Sách Tiếng Anh 10', 20, N'Nhiều Tác Giả', 15000.0000)
INSERT [dbo].[SanPham] ([maSanPham], [maNCC], [theloai], [phanLoai], [TenSanPham], [soLuong], [tacGia], [giaThanh]) VALUES (N'SP024', N'NCC012', N'Sách Giáo Khoa', N'Sách', N'Sách Tiếng Anh 11', 199, N'Nhiều Tác Giả', 12000.0000)
INSERT [dbo].[SanPham] ([maSanPham], [maNCC], [theloai], [phanLoai], [TenSanPham], [soLuong], [tacGia], [giaThanh]) VALUES (N'SP025', N'NCC001', N'', N'Dụng cụ học tập', N'Cục Tẩy Thiên Long', 200, N'', 7500.0000)
INSERT [dbo].[SanPham] ([maSanPham], [maNCC], [theloai], [phanLoai], [TenSanPham], [soLuong], [tacGia], [giaThanh]) VALUES (N'SP026', N'NCC012', N'Sách Giáo Khoa', N'Sách', N'Sách Vật Lý 10', 200, N'Nhiều Tác Giả', 11000.0000)
INSERT [dbo].[SanPham] ([maSanPham], [maNCC], [theloai], [phanLoai], [TenSanPham], [soLuong], [tacGia], [giaThanh]) VALUES (N'SP027', N'NCC001', N'Sách Giáo Khoa', N'Sách', N'Sách Vật Lý 11', 200, N'Nhiều Tác Giả', 11000.0000)
INSERT [dbo].[SanPham] ([maSanPham], [maNCC], [theloai], [phanLoai], [TenSanPham], [soLuong], [tacGia], [giaThanh]) VALUES (N'SP028', N'NCC012', N'Sách Giáo Khoa', N'Sách', N'Sách Hóa Học 10', 200, N'Nhiều Tác Giả', 11000.0000)
INSERT [dbo].[SanPham] ([maSanPham], [maNCC], [theloai], [phanLoai], [TenSanPham], [soLuong], [tacGia], [giaThanh]) VALUES (N'SP030', N'NCC001', N'', N'Dụng cụ học tập', N'Kéo cắt giấy loại I', 20, N'', 5000.0000)
INSERT [dbo].[SanPham] ([maSanPham], [maNCC], [theloai], [phanLoai], [TenSanPham], [soLuong], [tacGia], [giaThanh]) VALUES (N'SP031', N'NCC014', N'Hư Cấu', N'Sách', N'Dế mèn phiêu lưu kí', 20, N'Tô Hoài', 150000.0000)
INSERT [dbo].[SanPham] ([maSanPham], [maNCC], [theloai], [phanLoai], [TenSanPham], [soLuong], [tacGia], [giaThanh]) VALUES (N'SP032', N'NCC014', N'Tiểu thuyết', N'Sách', N'Làm Đĩ', 19, N'Vũ Trọng Phụng', 70000.0000)
GO

create trigger ThemCTDH on [dbo].[CTHoaDon]
	for insert
	as
	begin
		declare @sl int, @sltrongkho int, @masp nvarchar(255)

		select @masp = i.maSanPham
		from inserted i

		select @sl=i. [soLuong]
		from inserted i

		set @sltrongkho=(select  [soLuong]
						from [dbo].[SanPham]
						where [maSanPham]=@masp)
		if @sl<=@sltrongkho
			update [dbo].[SanPham]
			set [soLuong]=[soLuong]-@sl
			where [maSanPham]=@masp
		else
			begin
				print N'Hết hàng'
				rollback transaction
			end
	end
