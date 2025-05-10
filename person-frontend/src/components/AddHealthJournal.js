import { useState, useContext} from "react";
import { Button, Container, Form, Alert, Card } from "react-bootstrap";
import { authApis, endpoints } from "../configs/Apis";
import { Link, useNavigate } from "react-router-dom";
import { MyUserContext } from "../configs/MyContexts";
import MySpinner from "./layout/MySpinner";

const AddHealthJournal = () => {
    const [date, setDate] = useState("");
    const [feeling, setFeeling] = useState("");
    const [content, setContent] = useState("");
    const [msg, setMsg] = useState("");
    const nav = useNavigate();
    const user = useContext(MyUserContext);
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const journal = { date, feeling, content };

            const res = await authApis().post(endpoints['add_health_journal'], journal);
            if (res.status === 201) {
                setLoading(true);
                setMsg("Đã thêm nhật ký thành công!");
                setTimeout(() => nav("/health-journals"), 1000);
            }
        } catch (err) {
            setLoading(false);
            console.error(err);
            setMsg("Có lỗi xảy ra. Vui lòng thử lại!");
        }
    };

    if (!user) {
            return (
                <Container className="mt-4 text-center">
                    <p className="text-danger">Bạn cần đăng nhập để xem danh sách nhật ký</p>
                    <Link to="/login">
                        <Button variant="success">Đăng nhập</Button>
                    </Link>
                </Container>
            );
        }

    return (
        <Container className="mt-5">
            <Card className="shadow-lg p-4">
                <h2 className="text-center text-primary mb-4">📝 THÊM NHẬT KÝ SỨC KHỎE</h2>

                {msg && <Alert variant={msg.includes("thành công") ? "success" : "danger"}>{msg}</Alert>}

                <Form onSubmit={handleSubmit}>
                    <Form.Group className="mb-3">
                        <Form.Label>Ngày</Form.Label>
                        <Form.Control
                            type="date"
                            className="form-control-lg"
                            value={date}
                            onChange={(e) => setDate(e.target.value)}
                            required
                        />
                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>Tâm trạng / Cảm xúc</Form.Label>
                        <Form.Control
                            type="text"
                            placeholder="Ví dụ: Thoải mái, mệt mỏi, tích cực..."
                            className="form-control-lg"
                            value={feeling}
                            onChange={(e) => setFeeling(e.target.value)}
                            required
                        />
                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>Nội dung nhật ký</Form.Label>
                        <Form.Control
                            as="textarea"
                            rows={5}
                            placeholder="Mô tả chi tiết về hoạt động, cảm nhận, ăn uống, sức khỏe trong ngày..."
                            className="form-control-lg"
                            value={content}
                            onChange={(e) => setContent(e.target.value)}
                            required
                        />
                    </Form.Group>

                    <div className="text-center mt-4">
                        <Button variant="secondary" className="me-3 px-4" onClick={() => nav("/health-journals")}>
                            Quay lại
                        </Button>
                        {loading ? (
                            <MySpinner />
                        ) : (
                            <Button variant="success" type="submit" className="px-4">
                                Lưu nhật ký
                            </Button>
                        )}
                    </div>
                </Form>
            </Card>
        </Container>
    );
};

export default AddHealthJournal;
