import React, { useContext, useEffect, useState } from 'react';
import { Container, Card, Image, Alert, Button } from 'react-bootstrap';
import { authApis, endpoints } from '../configs/Apis';
import { MyUserContext } from '../configs/MyContexts';
import { Link, useNavigate } from 'react-router-dom';
import MySpinner from './layout/MySpinner';

const RequestUserTrainer = () => {
    const user = useContext(MyUserContext);
    const [requests, setRequests] = useState([]);
    const [loading, setLoading] = useState(true);
    const [msg, setMsg] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        const loadRequests = async () => {
            try {
                const res = await authApis().get(endpoints.requests_to_trainer);
                setRequests(res.data);
            } catch (err) {
                console.error(err);
                setMsg("Lỗi khi tải danh sách yêu cầu.");
            } finally {
                setLoading(false);
            }
        };

        if (user) loadRequests();
    }, [user, navigate]);

    const updateStatus = async (id, status) => {
        try {
            if (status === "ACCEPTED") {
                await authApis().put(endpoints.accept_request(id));
            } else if (status === "REJECTED") {
                await authApis().put(endpoints.reject_request(id));
            }

            setRequests((prev) =>
                prev.map((r) => (r.id === id ? { ...r, status } : r))
            );
            setMsg(`Trạng thái đã được cập nhật thành ${status}.`);
        } catch (err) {
            console.error(err);
            setMsg("Lỗi khi cập nhật trạng thái.");
        }
    };

    const deleteRequest = async (id) => {
        if (!window.confirm("Bạn có chắc chắn muốn xóa yêu cầu này?")) return;

        try {
            await authApis().delete(endpoints.delete_user_trainer(id));
            setRequests((prev) => prev.filter((r) => r.id !== id));
            setMsg("Yêu cầu đã được xóa thành công.");
        } catch (err) {
            console.error(err);
            setMsg("Lỗi khi xóa yêu cầu.");
        }
    };

    useEffect(() => {
        if (msg) {
            const timer = setTimeout(() => setMsg(""), 3000);
            return () => clearTimeout(timer);
        }
    }, [msg]);

    if (!user) {
        return (
            <Container className="mt-4 text-center">
                <p className="text-danger">Bạn cần đăng nhập để xem danh sách yêu cầu.</p>
                <Link to="/login">
                    <Button variant="success">Đăng nhập</Button>
                </Link>
            </Container>
        );
    }

    if (loading) return <MySpinner />;

    const renderRequestGroup = (title, status, color = "primary") => {
        const filtered = requests.filter(r => r.status === status);

        return (
            <div className="mt-5">
                <h4 className={`text-${color} fw-bold border-bottom pb-2`}>{title}</h4>

                {filtered.length === 0 ? (
                    <p className="text-muted">Không có yêu cầu.</p>
                ) : (
                    <div className="d-flex flex-wrap gap-3 mt-3">
                        {filtered.map((req) => (
                            <Card key={req.id} style={{ width: '18rem' }}>
                                <Card.Body className="d-flex flex-column justify-content-between">
                                    <div>
                                        <div className="text-center mb-3">
                                            <Image
                                                src={req.userId?.avatar}
                                                roundedCircle
                                                width="120"
                                                height="120"
                                                alt="Avatar"
                                                style={{ objectFit: "cover" }}
                                            />
                                        </div>
                                        <Card.Title>{req.userId?.fullName}</Card.Title>
                                        <Card.Text>Giới tính: {req.userId?.gender}</Card.Text>
                                        <Card.Text>Ngày sinh: {new Date(req.userId?.dateOfBirth).toLocaleDateString("vi-VN")}</Card.Text>
                                        <Card.Text>Email: {req.userId?.email}</Card.Text>
                                        <Card.Text>SĐT: {req.userId?.phone}</Card.Text>
                                    </div>

                                    {status === "PENDING" && (
                                        <div className="d-flex justify-content-between mt-3">
                                            <Button variant="success" size="sm" onClick={() => updateStatus(req.id, "ACCEPTED")}>
                                                Chấp nhận
                                            </Button>
                                            <Button variant="danger" size="sm" onClick={() => updateStatus(req.id, "REJECTED")}>
                                                Từ chối
                                            </Button>
                                        </div>
                                    )}

                                    {status === "ACCEPTED" && (
                                        <div className="d-flex justify-content-end mt-3">
                                            <Button
                                                variant="danger"
                                                size="sm"
                                                onClick={() => deleteRequest(req.id)}
                                            >
                                                Xóa
                                            </Button>
                                        </div>
                                    )}
                                </Card.Body>
                            </Card>
                        ))}
                    </div>
                )}
            </div>
        );
    };

    return (
        <Container className="mt-4">
            <h2>Yêu cầu kết nối từ người dùng</h2>
            {msg && <Alert variant="danger">{msg}</Alert>}

            {renderRequestGroup("🕒 Yêu cầu đang chờ xử lý", "PENDING", "warning")}
            {renderRequestGroup("✅ Yêu cầu đã được chấp nhận", "ACCEPTED", "success")}
            {renderRequestGroup("❌ Yêu cầu đã bị từ chối", "REJECTED", "danger")}
        </Container>
    );
};

export default RequestUserTrainer;