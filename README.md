# App Com Tam Dem

Ứng dụng "Com Tam Dem" là một dự án sử dụng Node.js, MongoDB và RESTful APIs, giúp người dùng quản lý và đặt món ăn cơm tấm. Backend sử dụng Node.js với Express và kết nối tới MongoDB để lưu trữ dữ liệu.

## Mô Tả

Ứng dụng cung cấp các tính năng như:
- Quản lý danh sách món ăn.
- Tạo, đọc, cập nhật, và xóa món ăn.
- Hệ thống API RESTful giúp giao tiếp giữa client và server.

## Công Nghệ Sử Dụng

- **Node.js**: Chạy trên môi trường JavaScript để xây dựng backend.
- **Express**: Framework của Node.js để xây dựng các API.
- **MongoDB**: Cơ sở dữ liệu NoSQL để lưu trữ dữ liệu món ăn.
- **Mongoose**: Thư viện dùng để kết nối và tương tác với MongoDB.
- **RESTful API**: Các endpoint RESTful để xử lý các yêu cầu HTTP.

## Cài Đặt

1. **Clone Repository**:

```bash
git clone https://github.com/chaolaolo/App_Com_Tam_Dem.git
```

2. **Cài đặt các phụ thuộc**:

Điều hướng đến thư mục dự án và cài đặt các gói phụ thuộc cần thiết:

```bash
cd App_Com_Tam_Dem
npm install
```

3. **Cấu Hình MongoDB**:

Cập nhật thông tin kết nối MongoDB trong file `.env` hoặc `config/db.js`:

```bash
MONGO_URI=mongodb+srv://<username>:<password>@cluster0.ng1qeww.mongodb.net/ComTamDem
```

4. **Chạy Dự Án**:

Chạy server bằng lệnh sau:

```bash
npm start
```

Server sẽ chạy tại `http://localhost:5000` (hoặc cổng khác nếu được cấu hình).

## Các API Endpoints

### 1. **GET /api/monan** - Lấy danh sách tất cả món ăn

- **Response**: Danh sách món ăn dưới dạng JSON.

### 2. **POST /api/monan** - Thêm món ăn mới

- **Body**:
```json
{
  "ten": "Cơm Tấm Sườn",
  "gia": 35000,
  "mo_ta": "Cơm tấm sườn nướng, trứng ốp la"
}
```

- **Response**: Thông tin món ăn đã được tạo.

### 3. **GET /api/monan/:id** - Lấy thông tin chi tiết của món ăn

- **Parameters**: `id` là ID của món ăn.
- **Response**: Thông tin chi tiết món ăn.

### 4. **PUT /api/monan/:id** - Cập nhật thông tin món ăn

- **Parameters**: `id` là ID của món ăn.
- **Body**:
```json
{
  "ten": "Cơm Tấm Nướng",
  "gia": 40000,
  "mo_ta": "Cơm tấm nướng, thêm rau"
}
```

- **Response**: Thông tin món ăn đã được cập nhật.

### 5. **DELETE /api/monan/:id** - Xóa món ăn

- **Parameters**: `id` là ID của món ăn.
- **Response**: Thông báo xóa món ăn thành công.

## Cấu Trúc Thư Mục

```
App_Com_Tam_Dem/
│
├── config/              # Cấu hình kết nối MongoDB
├── controllers/         # Logic xử lý API
├── models/              # Các mô hình dữ liệu
├── routes/              # Các route API
├── .env                 # Cấu hình môi trường
├── server.js            # Tập tin chính khởi động server
└── package.json         # Thông tin và phụ thuộc của dự án
```

## Ghi Chú

- Đảm bảo rằng MongoDB đã được cài đặt và cấu hình đúng trước khi chạy ứng dụng.
- Các API yêu cầu POST, PUT và DELETE sẽ cần dữ liệu được gửi qua body của yêu cầu HTTP.

---
