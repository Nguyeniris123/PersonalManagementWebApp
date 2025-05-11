import { useContext, useEffect, useState } from "react";
import { MyUserContext } from "../configs/MyContexts";
import { Button, Card, Container, Row, Col, Alert } from "react-bootstrap";
import { authApis, endpoints } from "../configs/Apis";
import { Link, useNavigate } from "react-router-dom";
import MySpinner from "./layout/MySpinner";

const ReminderList = () => {
    const user = useContext(MyUserContext);
    const [reminders, setReminders] = useState([]);
    const [loading, setLoading] = useState(false);
    const [msg, setMsg] = useState("");
    const [search, setSearch] = useState("");
    const [page, setPage] = useState(1);
    const nav = useNavigate();

    useEffect(() => {
        const loadReminders = async () => {
            if (page > 0) {
                setLoading(true);
                try {
                    let params = { page };
                    if (search) params.kw = search;

                    const res = await authApis().get(endpoints["reminders"], { params });

                    if (page === 1) {
                        setReminders(res.data); // Trang đầu: gán mới
                    } else if (res.data.length > 0) {
                        setReminders(prev => [...prev, ...res.data]); // Trang tiếp: nối thêm
                    } else {
                        setPage(0); // Hết dữ liệu
                    }
                } catch (err) {
                    console.error(err);
                    setMsg("Lỗi khi tải danh sách nhắc nhở.");
                } finally {
                    setLoading(false);
                }
            }
        };

        loadReminders();
    }, [page, search]);

    // Reset lại phân trang khi thay đổi từ khóa
    useEffect(() => {
        setPage(1);
    }, [search]);

    const deleteReminder = async (reminderId) => {
        if (window.confirm("Bạn có chắc chắn muốn xoá nhật kí này không?")) {
            try {
                await authApis().delete(endpoints.delete_reminder(reminderId));
                setMsg("Đã xoá thành công");
                // Gọi lại API để cập nhật danh sách
                setReminders(reminders.filter(p => p.id !== reminderId));
            } catch (err) {
                console.error(err);
                setMsg("Có lỗi xảy ra khi xoá!");
            }
        }
    };

    // Tự động xóa thông báo sau 1 giây
    useEffect(() => {
        if (msg) {
            const timer = setTimeout(() => setMsg(""), 1000);
            return () => clearTimeout(timer);
        }
    }, [msg]);

    if (!user) {
        return (
            <Container className="mt-4 text-center">
                <p className="text-danger">Bạn cần đăng nhập để xem danh sách nhắc nhở</p>
                <Link to="/login">
                    <Button variant="success">Đăng nhập</Button>
                </Link>
            </Container>
        );
    }

    return (
        <Container className="mt-4">
            <div className="d-flex justify-content-between align-items-center mb-4">
                <h3>🔔 Danh sách nhắc nhở</h3>
                <Link to="/add-reminder">
                    <Button variant="success">+ Thêm nhắc nhở</Button>
                </Link>
            </div>

            {msg && <Alert variant="danger">{msg}</Alert>}

            {/* Ô tìm kiếm */}
            <div className="mb-3">
                <input
                    type="text"
                    className="form-control"
                    placeholder="Tìm kiếm nhắc nhở theo tiêu đề..."
                    value={search}
                    onChange={e => setSearch(e.target.value)}
                />
            </div>

            {reminders.length === 0 && !loading ? (
                <Alert variant="info">Bạn chưa có nhắc nhở nào.</Alert>
            ) : (
                <Row>
                    {reminders.map((r) => (
                        <Col md={4} key={r.id} className="mb-4 d-flex align-items-stretch">
                            <Card className="shadow w-100 h-100 d-flex flex-column">
                                <Card.Body className="d-flex flex-column">
                                    <Card.Title className="d-flex justify-content-between align-items-center">
                                        <span>{r.title}</span>                                       
                                    </Card.Title>
                                    <Card.Text className="flex-grow-1">
                                        🕒 <strong>{r.time}</strong><br />
                                        <strong>Loại: {r.reminderType}</strong><br />
                                        Trạng thái:{" "}
                                        {r.isActive ? (
                                            <span className="text-success">Đang bật</span>
                                        ) : (
                                            <span className="text-muted">Đã tắt</span>
                                        )}
                                    </Card.Text>
                                    <div className="d-flex justify-content-between mt-3">
                                        <Button variant="primary" onClick={() => nav(`/update-reminder/${r.id}`)}>
                                            Chỉnh sửa
                                        </Button>
                                        <Button variant="danger" size="sm" onClick={() => deleteReminder(r.id)}>Xoá</Button>
                                    </div>
                                </Card.Body>
                            </Card>
                        </Col>
                    ))}
                </Row>
            )}

            {loading && <MySpinner />}

            {page > 0 && !loading && reminders.length > 0 && (
                <div className="text-center mt-2">
                    <Button variant="outline-primary" onClick={() => setPage(prev => prev + 1)}>
                        Xem thêm...
                    </Button>
                </div>
            )}
        </Container>
    );
};

export default ReminderList;
