import { useContext, useEffect, useState } from "react";
import { Container, Row, Col, Card, Button } from "react-bootstrap";
import { MyUserContext, HealthProfileContext, HealthProfileDispatchContext } from "../configs/MyContexts";
import { authApis, endpoints } from "../configs/Apis";
import { Link } from "react-router-dom";
import MySpinner from "./layout/MySpinner";

const HealthProfile = () => {
    const user = useContext(MyUserContext);
    const healthProfile = useContext(HealthProfileContext);
    const healthDispatch = useContext(HealthProfileDispatchContext);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        if (!user) {
            setError("Bạn chưa đăng nhập!");
            setLoading(false);
            return;
        }

        const fetchHealthProfile = async () => {
            try {
                const res = await authApis().get(endpoints['health_profile']);
                healthDispatch({ type: "set", payload: res.data });
            } catch (err) {
                if (err.response && err.response.status === 404)
                    setError("Chưa có hồ sơ sức khỏe. Vui lòng thêm mới.");
                else
                    setError("Không thể tải hồ sơ sức khỏe.");
            } finally {
                setLoading(false);
            }
        };

        fetchHealthProfile();
    }, [user, healthDispatch]);

    if (loading) {
        return (
            <Container className="mt-4 text-center">
                <MySpinner />
                <p>Đang tải hồ sơ sức khỏe...</p>
            </Container>
        );
    }

    if (error) {
        return (
            <Container className="mt-4 text-center">
                <p className="text-danger">{error}</p>
                <Link to="/add_health_profile">
                    <Button variant="success">Thêm hồ sơ sức khoẻ</Button>
                </Link>
            </Container>
        );
    }

    return (
        <Container className="mt-4">
            <Row className="justify-content-center">
                <Col md={10}>
                    <Card className="shadow mb-4">
                        <Card.Header className="bg-success text-white text-center">
                            <h4>Hồ sơ sức khỏe</h4>
                        </Card.Header>
                        <Card.Body>
                            <Row>
                                <Col md={6}>
                                    <p><strong>Chiều cao:</strong> {healthProfile.height} cm</p>
                                    <p><strong>Cân nặng:</strong> {healthProfile.weight} kg</p>
                                    <p><strong>Mục tiêu:</strong> {healthProfile.target}</p>
                                    <p><strong>Ngày cập nhật:</strong> {new Date(healthProfile.updatedAt).toLocaleString()}</p>
                                </Col>
                                <Col md={6}>
                                    <Row>
                                        <Col xs={6} className="mb-3">
                                            <Card className="text-center border-info">
                                                <Card.Body>
                                                    <h6>BMI</h6>
                                                    <h4 className="text-info">{healthProfile.bmi}</h4>
                                                </Card.Body>
                                            </Card>
                                        </Col>
                                        <Col xs={6} className="mb-3">
                                            <Card className="text-center border-primary">
                                                <Card.Body>
                                                    <h6>Nhịp tim</h6>
                                                    <h4 className="text-primary">{healthProfile.heartRate} bpm</h4>
                                                </Card.Body>
                                            </Card>
                                        </Col>
                                        <Col xs={6} className="mb-3">
                                            <Card className="text-center border-warning">
                                                <Card.Body>
                                                    <h6>Bước đi</h6>
                                                    <h4 className="text-warning">{healthProfile.stepsPerDay} bước</h4>
                                                </Card.Body>
                                            </Card>
                                        </Col>
                                        <Col xs={6} className="mb-3">
                                            <Card className="text-center border-success">
                                                <Card.Body>
                                                    <h6>Nước uống</h6>
                                                    <h4 className="text-success">{healthProfile.waterIntake} lít</h4>
                                                </Card.Body>
                                            </Card>
                                        </Col>
                                    </Row>
                                </Col>
                            </Row>
                            <div className="text-center mt-3">
                                <Link to="/update_health_profile">
                                    <Button variant="primary">Cập nhật hồ sơ sức khoẻ</Button>
                                </Link>
                            </div>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default HealthProfile;
