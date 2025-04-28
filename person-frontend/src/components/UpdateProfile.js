import { useContext, useState } from "react";
import { Container, Form, Button, Alert, Card, Image } from "react-bootstrap";
import { MyDispatchContext, MyUserContext } from "../configs/MyContexts";
import { authApis, endpoints } from "../configs/Apis";
import { useNavigate, Link } from "react-router-dom";
import MySpinner from "./layout/MySpinner";

const UpdateProfile = () => {
    const user = useContext(MyUserContext);
    const [loading, setLoading] = useState(false);
    const userDispatch = useContext(MyDispatchContext); // Lấy dispatch từ Context
    const nav = useNavigate();
    const [msg, setMsg] = useState("");
    const [avatar, setAvatar] = useState(null); // Ảnh đại diện
    const [password, setPassword] = useState(""); // Mật khẩu mới

    const [formData, setFormData] = useState({
        fullName: user?.fullName,
        username: user?.username,
        email: user?.email,
        phone: user?.phone,
        dateOfBirth: user?.dateOfBirth
            ? new Date(user.dateOfBirth).toLocaleDateString("en-CA") // Định dạng yyyy-MM-dd
            : "",
        gender: user?.gender,
        role: user?.role
    });

    if (!user) {
        return (
            <Container className="mt-4 text-center">
                <p className="text-danger">Bạn cần đăng nhập để xem Profile</p>
                <Link to="/login">
                    <Button variant="success">Đăng nhập</Button>
                </Link>
            </Container>
        );
    }

    // Cấu hình các trường nhập liệu
    const info = [
        { label: "Họ và tên", name: "fullName", type: "text" },
        { label: "Email", name: "email", type: "email" },
        { label: "Số điện thoại", name: "phone", type: "text" },
        { label: "Ngày sinh", name: "dateOfBirth", type: "date" },
        {
            label: "Giới tính", name: "gender", type: "select", options: [
                { value: "MALE", label: "Nam" },
                { value: "FEMALE", label: "Nữ" },
                { value: "OTHER", label: "Khác" }
            ]
        }
    ];

    // Xử lý thay đổi dữ liệu trong form
    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({ ...prev, [name]: value }));
    };

    // Xử lý thay đổi ảnh đại diện
    const handleAvatarChange = (e) => {
        setAvatar(e.target.files[0]);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            setLoading(true);
            const form = new FormData();

            // Chuyển đổi tên trường từ fullName -> full_name
            for (let key in formData) {
                const name = key === "fullName" ? "full_name" : key;
                const date = key === "dateOfBirth" ? "date_of_birth" : key;
                form.append(name, formData[key]);
                form.append(date, formData[key]);
            }

            if (avatar) {
                form.append("avatar", avatar);
            }

            if (password.trim() !== "") {
                form.append("password", password);
            }

            const res = await authApis().put(endpoints['update_profile'](user.id), form, {
                headers: {
                    "Content-Type": "multipart/form-data"
                }
            });

            console.log(res.data);
            setMsg("Cập nhật thông tin thành công!");

            // Cập nhật Context với dữ liệu mới
            userDispatch({ type: "update", payload: res.data });

            setTimeout(() => {
                nav("/profile");
            }, 1500);
        } catch (err) {
            console.error(err);
            setMsg("Có lỗi xảy ra khi cập nhật thông tin.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <Container className="mt-5 d-flex justify-content-center">
            <Card style={{ maxWidth: "600px", width: "100%" }} className="shadow p-4 rounded-4">
                <h4 className="text-center mb-4">Cập nhật thông tin cá nhân</h4>
                {msg && <Alert variant="info">{msg}</Alert>}

                <Form onSubmit={handleSubmit}>
                    {/* Avatar */}
                    <Form.Group className="mb-3">
                        <Form.Label>Ảnh đại diện hiện tại</Form.Label>
                        <div className="mb-3">
                            <Image
                                src={avatar ? URL.createObjectURL(avatar) : user?.avatar || "/default-avatar.png"}
                                roundedCircle
                                width="120"
                                height="120"
                                alt="Avatar"
                                style={{ objectFit: "cover" }}
                            />
                        </div>
                        <Form.Control type="file" accept="image/*" onChange={handleAvatarChange} />
                    </Form.Group>

                    {/* Các trường nhập liệu động */}
                    {info.map((field, index) => (
                        <Form.Group className="mb-3" key={index}>
                            <Form.Label>{field.label}</Form.Label>
                            {field.type === "select" ? (
                                <Form.Select
                                    name={field.name}
                                    value={formData[field.name]}
                                    onChange={handleChange}
                                >
                                    {field.options.map((option, idx) => (
                                        <option key={idx} value={option.value}>
                                            {option.label}
                                        </option>
                                    ))}
                                </Form.Select>
                            ) : (
                                <Form.Control
                                    type={field.type}
                                    name={field.name}
                                    value={field.type === "date" && formData[field.name]
                                        ? new Date(formData[field.name]).toISOString().split("T")[0]
                                        : formData[field.name]}
                                    onChange={handleChange}
                                />
                            )}
                        </Form.Group>
                    ))}

                    {/* Password */}
                    <Form.Group className="mb-3">
                        <Form.Label>Mật khẩu mới (nếu muốn thay đổi)</Form.Label>
                        <Form.Control
                            type="password"
                            placeholder="Nhập mật khẩu mới"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                        />
                    </Form.Group>

                    <div className="d-flex justify-content-between">
                        {loading ? (
                            <MySpinner />
                        ) : (
                            <Button variant="success" type="submit">Lưu</Button>
                        )}
                        <Button variant="secondary" onClick={() => nav("/profile")}>Hủy</Button>
                    </div>
                </Form>
            </Card>
        </Container>
    );
};

export default UpdateProfile;