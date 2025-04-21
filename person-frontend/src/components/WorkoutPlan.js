import { useContext, useEffect, useState } from "react";
import { MyUserContext } from "../configs/MyContexts";
import { Button, Card, Container, Row, Col, Alert } from "react-bootstrap";
import { authApis, endpoints } from "../configs/Apis";
import { Link } from "react-router-dom";
import MySpinner from "./layout/MySpinner";

const WorkoutPlanPage = () => {
    const [plans, setPlans] = useState(null);
    const [loading, setLoading] = useState(true);
    const [msg, setMsg] = useState("");
    const user = useContext(MyUserContext);

    useEffect(() => {
        const loadPlans = async () => {
            try {
                const res = await authApis().get(endpoints["workout_plan"]);
                setPlans(res.data);
            } catch (err) {
                console.error(err);
                setMsg("Lỗi khi tải kế hoạch tập luyện");
                setPlans([]);
            } finally {
                setLoading(false);
            }
        };

        loadPlans();
    }, [user]);

    const deletePlan = async (planId) => {
        if (window.confirm("Bạn có chắc chắn muốn xoá kế hoạch này không?")) {
            try {
                await authApis().delete(endpoints.delete_workout_plan(planId));
                setMsg("Đã xoá thành công");
                // Gọi lại API để cập nhật danh sách
                setPlans(plans.filter(p => p.id !== planId));
            } catch (err) {
                console.error(err);
                setMsg("Có lỗi xảy ra khi xoá!");
            }
        }
    };

    useEffect(() => {
        if (msg) {
            const timer = setTimeout(() => setMsg(""), 1000); // Tự động xóa thông báo sau 1 giây
            return () => clearTimeout(timer); // Dọn dẹp timer khi component unmount hoặc msg thay đổi
        }
    }, [msg]);

    if (!user) {
        return (
            <Container className="mt-4 text-center">
                <p className="text-danger">Bạn cần đăng nhập để xem danh sách kế hoạch</p>
                <Link to="/login">
                    <Button variant="success">Đăng nhập</Button>
                </Link>
            </Container>
        );
    }


    if (loading) return <MySpinner />;

    return (
        <Container className="mt-4">
            <div className="d-flex justify-content-between align-items-center mb-4">
                <h3>Kế hoạch tập luyện của bạn</h3>
                <Link to="/add_workout_plan">
                    <Button variant="success">+ Tạo kế hoạch mới</Button>
                </Link>               
            </div>

            {plans.length === 0 ? (
                <Alert variant="info">Bạn chưa có kế hoạch tập luyện nào.</Alert>
            ) : (
                <Row>
                    {msg && <Alert variant="danger">{msg}</Alert>}
                    {plans.map((p) => (
                        <Col key={p.id} md={6} lg={4} className="mb-4">
                            <Card style={{ backgroundColor: "#ffe4b5", border: "1px solid #ffa500" }}>
                                <Card.Body>
                                    <Card.Title>{p.name}</Card.Title>
                                    <Card.Text>
                                        <strong>Thời gian:</strong> {new Date(p.startDate).toLocaleDateString("vi-VN")} - {new Date(p.endDate).toLocaleDateString("vi-VN")}
                                    </Card.Text>
                                    <Link to={`/workout_plan/${p.id}`}>
                                        <Button variant="primary" size="sm">Xem chi tiết</Button>
                                    </Link>
                                    <Button variant="danger" size="sm" className="ms-2" onClick={() => deletePlan(p.id)}>
                                        Xoá
                                    </Button>
                                </Card.Body>
                            </Card>
                        </Col>
                    ))}
                </Row>
            )}
        </Container>
    );
};

export default WorkoutPlanPage;
