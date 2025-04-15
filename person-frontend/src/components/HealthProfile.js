import { useContext, useState, useEffect } from "react";
import { Container, Row, Col, Card, Button, Spinner } from "react-bootstrap";
import { MyUserContext } from "../configs/MyContexts";
import { authApis } from "../configs/Apis";

const HealthProfile = () => {
    const user = useContext(MyUserContext);
    const [healthProfile, setHealthProfile] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        if (!user) {
            setError("Bạn chưa đăng nhập!");
            setLoading(false);
            return;
        }

        const userId = user.id;

        const fetchHealthProfile = async () => {
            try {
                const res = await authApis().get(`secure/health-profiles/user/${userId}`);
                setHealthProfile(res.data);
            } catch (err) {
                if (err.response && err.response.status === 404) {
                    setError("Chưa có hồ sơ sức khỏe.");
                } else {
                    setError("Không thể tải hồ sơ sức khỏe.");
                }
            } finally {
                setLoading(false);
            }
        };

        fetchHealthProfile();
    }, [user]);

    if (loading) {
        return (
            <Container className="mt-4 text-center">
                <Spinner animation="border" variant="primary" />
                <p>Đang tải hồ sơ sức khỏe...</p>
            </Container>
        );
    }

    if (error) {
        return (
            <Container className="mt-4 text-center">
                <p className="text-danger">{error}</p>
                {error === "Chưa có hồ sơ sức khỏe." && (
                    <Button variant="success" onClick={() => window.location.href = "/add-health-profile"}>
                        Thêm hồ sơ
                    </Button>
                )}
            </Container>
        );
    }

    return (
        <Container className="mt-4">
            <Row className="justify-content-center">
                <Col md={8}>
                    <Card className="shadow">
                        <Card.Header className="bg-success text-white text-center">
                            <h4>Hồ sơ sức khỏe</h4>
                        </Card.Header>
                        <Card.Body>
                            <p><strong>Chiều cao:</strong> {healthProfile.height} cm</p>
                            <p><strong>Cân nặng:</strong> {healthProfile.weight} kg</p>
                            <p><strong>BMI:</strong> {healthProfile.bmi}</p>
                            <p><strong>Nhịp tim:</strong> {healthProfile.heartRate} bpm</p>
                            <p><strong>Số bước đi mỗi ngày:</strong> {healthProfile.stepsPerDay} bước</p>
                            <p><strong>Lượng nước uống mỗi ngày:</strong> {healthProfile.waterIntake} lít</p>
                            <p><strong>Mục tiêu:</strong> {healthProfile.target}</p>
                            <p><strong>Ngày cập nhật:</strong> {new Date(healthProfile.updatedAt).toLocaleString()}</p>
                            <Button variant="primary" onClick={() => {
                                window.location.href = "/update-health-profile";
                            }}>Cập nhật hồ sơ</Button>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default HealthProfile;
