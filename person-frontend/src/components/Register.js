import { useRef, useState } from "react";
import { Alert, Button, Container, FloatingLabel, Form } from "react-bootstrap";
import Apis, { endpoints } from "../configs/Apis";
import { useNavigate } from "react-router-dom";
import MySpinner from "./layout/MySpinner";

const Register = () => {
    const info = [
        { label: "Họ và tên", type: "text", field: "full_name" },
        { label: "Tên đăng nhập", type: "text", field: "username" },
        { label: "Mật khẩu", type: "password", field: "password" },
        { label: "Xác nhận mật khẩu", type: "password", field: "confirm" },
        { label: "Điện thoại", type: "tel", field: "phone" },
        { label: "Email", type: "email", field: "email" },
        { label: "Ngày sinh", type: "date", field: "date_of_birth" }
    ];

    const avatar = useRef();
    const [msg, setMsg] = useState("");
    const [user, setUser] = useState({});
    const [loading, setLoading] = useState(false);
    const nav = useNavigate();

    const setState = (value, field) => {
        setUser({ ...user, [field]: value });
    };

    const register = async (e) => {
        e.preventDefault();

        if (user.password !== user.confirm) {
            setMsg("Mật khẩu không khớp!");
        } else {
            try {
                setLoading(true);
                let form = new FormData();
                for (let f of info) {
                    if (f.field !== 'confirm') {
                        form.append(f.field, user[f.field]);
                    }
                }

                form.append("gender", user.gender || "OTHER");
                form.append("role", user.role || "ROLE_USER");
                form.append("avatar", avatar.current.files[0]);

                let res = await Apis.post(endpoints['register'], form, {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                });

                if (res.status === 201)
                    nav("/login");
            } catch (err) {
                console.error(err);
                setMsg("Có lỗi xảy ra khi đăng ký (username hay email đã tồn tại!");
            } finally {
                setLoading(false);
            }
        }
    };

    return (
        <Container>
            <h1 className="text-center text-success mt-1">ĐĂNG KÝ NGƯỜI DÙNG</h1>

            {msg && <Alert variant="danger">{msg}</Alert>}

            <Form onSubmit={register}>
                {info.map(f => (
                    <FloatingLabel key={f.field} controlId={f.field} label={f.label} className="mb-3">
                        <Form.Control
                            type={f.type}
                            placeholder={f.label}
                            required
                            value={user[f.field] || ""}
                            onChange={e => setState(e.target.value, f.field)}
                        />
                    </FloatingLabel>
                ))}

                <FloatingLabel label="Giới tính" className="mb-3">
                    <Form.Select value={user.gender || "MALE"} onChange={e => setState(e.target.value, "gender")}>
                        <option value="MALE">Nam</option>
                        <option value="FEMALE">Nữ</option>
                        <option value="OTHER">Khác</option>
                    </Form.Select>
                </FloatingLabel>

                <FloatingLabel label="Vai trò" className="mb-3">
                    <Form.Select value={user.role || "ROLE_USER"} onChange={e => setState(e.target.value, "role")}>
                        <option value="ROLE_USER">Người dùng</option>
                        <option value="ROLE_TRAINER">Chuyên gia</option>
                    </Form.Select>
                </FloatingLabel>

                <FloatingLabel controlId="avatar" label="Ảnh đại diện" className="mb-3">
                    <Form.Control type="file" ref={avatar} required/>
                </FloatingLabel>

                {loading ? <MySpinner /> : <Button type="submit" variant="success" className="mt-1 mb-1">Đăng ký</Button>}
            </Form>
        </Container>
    );
};

export default Register;
