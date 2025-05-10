import { useState } from "react";
import { Button, Form, Container, Alert, Card } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { authApis, endpoints } from "../configs/Apis";
import MySpinner from "./layout/MySpinner";

const AddReminder = () => {
    const [title, setTitle] = useState("");
    const [reminderType, setReminderType] = useState("DRINK_WATER");
    const [time, setTime] = useState("08:00");
    const [isActive, setIsActive] = useState(true);
    const [loading, setLoading] = useState(false);
    const [msg, setMsg] = useState("");

    const nav = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);

        try {
            const res = await authApis().post(endpoints["add_reminder"], {
                title,
                reminderType,
                time,
                isActive,
            });

            if (res.status === 201) {
                setMsg("Thêm nhắc nhở thành công!");
                setTimeout(() => nav("/reminders"), 1000);
            }
        } catch (err) {
            console.error(err);
            setMsg("Lỗi khi thêm nhắc nhở. Hãy kiểm tra lại dữ liệu.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <Container className="mt-5" style={{ maxWidth: "600px" }}>
            <Card className="shadow-lg p-4">
                <h3 className="text-center text-primary mb-4">📝 Thêm nhắc nhở mới</h3>

                {msg && <Alert variant="info">{msg}</Alert>}

                <Form onSubmit={handleSubmit}>
                    <Form.Group className="mb-3">
                        <Form.Label>Tiêu đề</Form.Label>
                        <Form.Control
                            type="text"
                            className="form-control-lg"
                            placeholder="Ví dụ: Uống nước coca, Nghỉ trưa 20p..."
                            value={title}
                            onChange={(e) => setTitle(e.target.value)}
                            required
                        />
                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>Loại nhắc nhở</Form.Label>
                        <Form.Select
                            className="form-control-lg"
                            value={reminderType}
                            onChange={(e) => setReminderType(e.target.value)}
                            required
                        >
                            <option value="DRINK_WATER">Uống nước</option>
                            <option value="WORKOUT">Tập luyện</option>
                            <option value="REST">Nghỉ ngơi</option>
                        </Form.Select>
                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>Thời gian</Form.Label>
                        <Form.Control
                            type="time"
                            className="form-control-lg"
                            value={time}
                            onChange={(e) => setTime(e.target.value)}
                            required
                        />
                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Check
                            type="switch"
                            id="isActive"
                            label="Kích hoạt nhắc nhở"
                            checked={isActive}
                            onChange={(e) => setIsActive(e.target.checked)}
                        />
                    </Form.Group>

                    <div className="text-center mt-4">
                        <Button
                            variant="secondary"
                            className="me-3 px-4"
                            onClick={() => nav("/reminders")}
                        >
                            Quay lại
                        </Button>
                        {loading ? (
                            <MySpinner />
                        ) : (
                            <Button variant="success" type="submit" className="px-4">
                                Lưu nhắc nhở
                            </Button>
                        )}
                    </div>
                </Form>
            </Card>
        </Container>
    );
};

export default AddReminder;
