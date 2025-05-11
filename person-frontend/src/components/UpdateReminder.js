import { useContext, useEffect, useState } from "react";
import { useParams, useNavigate, Link } from "react-router-dom";
import { MyUserContext } from "../configs/MyContexts";
import { authApis, endpoints } from "../configs/Apis";
import { Container, Form, Button, Alert } from "react-bootstrap";
import MySpinner from "./layout/MySpinner";

const UpdateReminder = () => {
    const { id } = useParams();
    const user = useContext(MyUserContext);
    const nav = useNavigate();
    const [reminder, setReminder] = useState(null);
    const [loading, setLoading] = useState(true);
    const [msg, setMsg] = useState("");

    useEffect(() => {
        const loadReminder = async () => {
            try {
                const res = await authApis().get(endpoints.reminder_by_id(id));
                setReminder(res.data);
            } catch (err) {
                setMsg("Không tìm thấy nhắc nhở!");
            } finally {
                setLoading(false);
            }
        };
        loadReminder();
    }, [id]);

    const handleChange = (e) => {
        const { name, value, type, checked } = e.target;
        setReminder((prev) => ({
            ...prev,
            [name]: type === "checkbox" ? checked : value,
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        try {
            await authApis().put(endpoints.update_reminder(id), reminder);
            setMsg("Cập nhật thành công!");
            setTimeout(() => nav("/reminders"), 1000);
        } catch (err) {
            setMsg("Có lỗi xảy ra khi cập nhật!");
        } finally {
            setLoading(false);
        }
    };

    if (!user) {
        return (
            <Container className="mt-4 text-center">
                <p className="text-danger">Bạn cần đăng nhập để cập nhật nhắc nhở</p>
                <Link to="/login">
                    <Button variant="success">Đăng nhập</Button>
                </Link>
            </Container>
        );
    }

    if (loading) return <MySpinner />;

    return (
        <Container className="mt-4" style={{ maxWidth: 500 }}>
            <h3 className="mb-4">Cập nhật nhắc nhở</h3>
            {msg && <Alert variant={msg.includes("thành công") ? "success" : "danger"}>{msg}</Alert>}
            <Form onSubmit={handleSubmit}>
                <Form.Group className="mb-3">
                    <Form.Label>Tiêu đề</Form.Label>
                    <Form.Control
                        type="text"
                        name="title"
                        value={reminder.title || ""}
                        onChange={handleChange}
                        required
                    />
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>Loại nhắc nhở</Form.Label>
                    <Form.Select
                        name="reminderType"
                        value={reminder.reminderType || ""}
                        onChange={handleChange}
                        required
                    >
                        <option value="">Chọn loại</option>
                        <option value="DRINK_WATER">Uống nước</option>
                        <option value="WORKOUT">Tập luyện</option>
                        <option value="REST">Nghỉ ngơi</option>
                    </Form.Select>
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>Thời gian</Form.Label>
                    <Form.Control
                        type="time"
                        name="time"
                        value={reminder.time || ""}
                        onChange={handleChange}
                        required
                    />
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Check
                        type="switch"
                        id="isActiveSwitch"
                        label="Kích hoạt nhắc nhở"
                        name="isActive"
                        checked={!!reminder.isActive}
                        onChange={handleChange}
                    />
                </Form.Group>
                <div className="d-flex justify-content-between">
                    <Button variant="secondary" className="mt-1 mb-1" as={Link} to="/reminders">
                        Quay lại
                    </Button>
                    <Button type="submit" variant="success" className="mt-1 mb-1">
                        Cập nhật
                    </Button>
                </div>
            </Form>
        </Container>
    );
};

export default UpdateReminder;