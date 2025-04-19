import { useEffect, useState } from "react";
import { Button, Card, Container, Row, Col, Alert } from "react-bootstrap";
import { authApis, endpoints } from "../configs/Apis";
import { Link } from "react-router-dom";
import MySpinner from "./layout/MySpinner";

const WorkoutPlanPage = () => {
    const [plans, setPlans] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const loadPlans = async () => {
            try {
                const res = await authApis().get(endpoints["workout_plan"]);
                setPlans(res.data);
            } catch (err) {
                console.error("Lỗi khi tải kế hoạch tập luyện: ", err);
                setPlans([]);
            } finally {
                setLoading(false);
            }
        };

        loadPlans();
    }, []);

    if (loading) return <MySpinner />;

    return (
        <Container className="mt-4">
            <div className="d-flex justify-content-between align-items-center mb-4">
                <h3>Kế hoạch tập luyện của bạn</h3>
                <Link to="/workout_plan/add">
                    <Button variant="success">+ Tạo kế hoạch mới</Button>
                </Link>
            </div>

            {plans.length === 0 ? (
                <Alert variant="info">Bạn chưa có kế hoạch tập luyện nào.</Alert>
            ) : (
                <Row>
                    {plans.map((p) => (
                        <Col key={p.id} md={6} lg={4} className="mb-4">
                            <Card>
                                <Card.Body>
                                    <Card.Title>{p.name}</Card.Title>
                                    <Card.Text>
                                        <strong>Thời gian:</strong> {new Date(p.startDate).toLocaleDateString("vi-VN")} - {new Date(p.endDate).toLocaleDateString("vi-VN")}
                                    </Card.Text>
                                    <Link to={`/workout_plan/${p.id}`}>
                                        <Button variant="primary" size="sm">Xem chi tiết</Button>
                                    </Link>
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
