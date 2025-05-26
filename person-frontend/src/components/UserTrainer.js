import React, { useContext, useEffect, useState } from 'react';
import { Container, Button, Card, Alert, Image, Form } from "react-bootstrap";
import { authApis, endpoints } from "../configs/Apis";
import { Link } from 'react-router-dom';
import { MyUserContext } from '../configs/MyContexts';
import MySpinner from './layout/MySpinner';
import ChatBox from "./ChatBox";

const ConnectTrainer = () => {
    const user = useContext(MyUserContext);
    const [trainers, setTrainers] = useState([]);
    const [connections, setConnections] = useState([]);
    const [loading, setLoading] = useState(true);
    const [msg, setMsg] = useState("");
    const [searchTerm, setSearchTerm] = useState(""); // Tìm kiếm
    const [openChatTrainerId, setOpenChatTrainerId] = useState(null);

    // Lấy danh sách huấn luyện viên
    useEffect(() => {
        const loadData = async () => {
            try {
                const [res1, res2] = await Promise.all([
                    authApis().get(endpoints.trainers),
                    authApis().get(endpoints.user_trainer_connections)
                ]);
                setTrainers(res1.data);
                setConnections(res2.data);
            } catch (err) {
                console.error(err);
                setMsg("Lỗi khi tải dữ liệu.");
            } finally {
                setLoading(false);
            }
        };

        if (user) loadData();
    }, [user]);

    // Gửi yêu cầu kết nối
    const handleConnect = async (trainerId) => {
        try {
            const res = await authApis().post(endpoints.connect_trainer, { trainerId });
            console.log(res.data);

            // Cập nhật danh sách kết nối
            // Lấy lại danh sách kết nối từ API
            const updatedConnections = await authApis().get(endpoints.user_trainer_connections);
            setConnections(updatedConnections.data);

            setMsg("Đã gửi yêu cầu kết nối!");
        } catch (err) {
            const error = err.response?.data?.error || "Đã có lỗi xảy ra.";
            setMsg(error);
        }
    };

    const handleDeleteConnection = async (connectionId) => {
        if (!window.confirm("Bạn có chắc chắn muốn xóa kết nối?")) return;
        try {
            const res = await authApis().delete(endpoints.delete_user_trainer(connectionId));
            console.log(res.data); // Kiểm tra phản hồi từ server
            setConnections(connections.filter(c => c.id !== connectionId));
            setMsg("Đã xóa yêu cầu kết nối.");
        } catch (err) {
            console.error(err); // Kiểm tra lỗi chi tiết
            const error = err.response?.data?.error || "Đã có lỗi xảy ra.";
            setMsg(error);
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
                <p className="text-danger">Bạn cần đăng nhập để kết nối với huấn luyện viên.</p>
                <Link to="/login">
                    <Button variant="success">Đăng nhập</Button>
                </Link>
            </Container>
        );
    }

    if (loading) return <MySpinner />;

    // Lấy danh sách ID các trainer đã được gửi yêu cầu
    const connectedTrainerIds = connections.map(c => c.trainerId?.id);

    // Lọc danh sách huấn luyện viên theo từ khóa tìm kiếm
    const filteredTrainers = trainers.filter(trainer =>
        trainer.fullName.toLowerCase().includes(searchTerm.toLowerCase()) ||
        trainer.email.toLowerCase().includes(searchTerm.toLowerCase())
    );

    return (
        <Container className="mt-4">
            <h2>Kết nối với Huấn luyện viên</h2>

            {msg && <Alert variant="info">{msg}</Alert>}

            {/* Input tìm kiếm */}
            <Form className="mb-4">
                <Form.Control type="text" placeholder="Tìm kiếm huấn luyện viên..." value={searchTerm} onChange={(e) => setSearchTerm(e.target.value)} />
            </Form>

            <div className="d-flex flex-wrap gap-3 mt-4">
                {filteredTrainers.length === 0 ? (
                    <p>Không tìm thấy huấn luyện viên nào.</p>
                ) : (
                    filteredTrainers.map(trainer => (
                        <Card key={trainer.id} style={{ width: '18rem' }}>
                            <Card.Body>
                                <div className="text-center mb-4">
                                    <Image src={trainer.avatar} roundedCircle width="120" height="120" alt="Avatar" style={{ objectFit: "cover" }} />
                                </div>
                                <Card.Title>{trainer.fullName}</Card.Title>
                                <Card.Text>Email: {trainer.email}</Card.Text>
                                <Card.Text>Điện thoại: {trainer.phone}</Card.Text>
                                <Card.Text>Ngày sinh: {new Date(trainer.dateOfBirth).toLocaleDateString("vi-VN")}</Card.Text>
                                <Card.Text>Giới tính: {trainer.gender}</Card.Text>
                                <Button variant="primary" onClick={() => handleConnect(trainer.id)} disabled={connectedTrainerIds.includes(trainer.id)}>
                                    {connectedTrainerIds.includes(trainer.id) ? "Đã gửi" : "Gửi yêu cầu"}
                                </Button>
                            </Card.Body>
                        </Card>
                    ))
                )}
            </div>

            <h4 className="mt-5">Yêu cầu đã gửi</h4>
            <div className="d-flex flex-wrap gap-3 mt-3">
                {connections.length === 0 ? (
                    <p>Bạn chưa gửi yêu cầu nào.</p>
                ) : (
                    connections.map(conn => (
                        <Card key={conn.id} style={{ width: '28rem', position: 'relative' }}>
                            <Card.Body>
                                <div className="text-center mb-3">
                                    <Image src={conn.trainerId?.avatar} roundedCircle width="120" height="120" alt="Avatar" />
                                </div>
                                <Card.Title>{conn.trainerId?.fullName}</Card.Title>
                                <Card.Text>Email: {conn.trainerId?.email}</Card.Text>
                                <Card.Text>Trạng thái: {conn.status}</Card.Text>
                                <Button variant="danger" onClick={() => handleDeleteConnection(conn.id)}>Xóa yêu cầu</Button>
                                <Button
                                    variant={openChatTrainerId === conn.trainerId.id ? "secondary" : "success"}
                                    className="ms-2"
                                    onClick={() => setOpenChatTrainerId(openChatTrainerId === conn.trainerId.id ? null : conn.trainerId.id)}
                                >
                                    {openChatTrainerId === conn.trainerId.id ? "Đóng chat" : "Chat"}
                                </Button>

                                {openChatTrainerId === conn.trainerId.id && (
                                    <div
                                        style={{
                                            marginTop: '1rem',
                                            maxHeight: '450px',
                                            overflowY: 'auto',
                                            border: '1px solid #ddd',
                                            borderRadius: '5px',
                                            padding: '0.5rem',
                                            backgroundColor: '#fafafa',
                                            width: '100%',  // Đảm bảo chatbox rộng bằng card
                                            boxSizing: 'border-box',
                                        }}
                                    >
                                        <ChatBox userId={user.id} trainerId={conn.trainerId.id} />
                                    </div>
                                )}
                            </Card.Body>
                        </Card>
                    ))
                )}
            </div>
        </Container>
    );
};

export default ConnectTrainer;