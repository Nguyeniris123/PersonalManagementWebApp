import { useContext, useState } from "react";
import { Form, Button, Container, Alert, Row, Col, Card } from "react-bootstrap";
import { MyUserContext } from "../configs/MyContexts";
import { authApis, endpoints } from "../configs/Apis";
import { useNavigate } from "react-router-dom";
import MySpinner from "./layout/MySpinner";

const AddHealthProfile = () => {
    const user = useContext(MyUserContext); // Kiểm tra người dùng đã đăng nhập chưa
    const nav = useNavigate();
    const [msg, setMsg] = useState("");
    const [healthProfile, setHealthProfile] = useState({});
    const [loading, setLoading] = useState(false);

    const info = [
        { label: "Chiều cao (cm)", type: "number", field: "height" },
        { label: "Cân nặng (kg)", type: "number", field: "weight" },
        { label: "Nhịp tim (bpm)", type: "number", field: "heartRate" },
        { label: "Số bước đi mỗi ngày", type: "number", field: "stepsPerDay" },
        { label: "Lượng nước uống (lít)", type: "number", field: "waterIntake" },
        { label: "Mục tiêu", type: "text", field: "target" }
    ];

    // Kiểm tra xem người dùng đã đăng nhập chưa
    if (!user) {
        return (
            <Container className="mt-4">
                <Alert variant="danger" className="text-center">
                    Bạn cần đăng nhập để thêm hồ sơ sức khỏe!
                </Alert>
            </Container>
        );
    }

    const setState = (value, field) => {
        setHealthProfile({ ...healthProfile, [field]: value });
    };

    const addHealthProfile = async (e) => {
        e.preventDefault();
    
        try {
            setLoading(true);
    
            // Chuyển dữ liệu thành kiểu phù hợp trước khi gửi
            const data = {
                height: parseFloat(healthProfile.height),
                weight: parseFloat(healthProfile.weight),
                heartRate: parseInt(healthProfile.heartRate),
                stepsPerDay: parseInt(healthProfile.stepsPerDay),
                waterIntake: parseFloat(healthProfile.waterIntake),
                target: healthProfile.target
            };
    
            console.log("Gửi đi:", data); // Debug
            const res = await authApis().post(endpoints['add_health_profile'], data);
    
            if (res.status === 201)
                nav("/health_profile");
        } catch (err) {
            console.error(err);
            setMsg("Có lỗi xảy ra khi thêm hồ sơ sức khỏe!");
        } finally {
            setLoading(false);
        }
    };

    

    return (
        <Container className="mt-5">
            <Card className="shadow-lg p-4">
                <h2 className="text-center text-success mb-4">THÊM HỒ SƠ SỨC KHỎE</h2>

                {msg && <Alert variant="danger">{msg}</Alert>}

                <Form onSubmit={addHealthProfile}>
                    <Row>
                        {info.map((f) => (
                            <Col md={6} key={f.field}>
                                <Form.Group className="mb-3">
                                    <Form.Label>{f.label}</Form.Label>
                                    <Form.Control
                                        type={f.type}
                                        placeholder={f.label}
                                        required
                                        value={healthProfile[f.field] || ""}
                                        onChange={(e) => setState(e.target.value, f.field)}
                                        className="form-control-lg" // Thêm kích thước lớn cho trường nhập liệu
                                    />
                                </Form.Group>
                            </Col>
                        ))}
                    </Row>

                    <div className="text-center">
                        {loading ? (
                            <MySpinner />
                        ) : (
                            <Button type="submit" variant="success" className="btn-lg mt-3 px-4">
                                Thêm hồ sơ
                            </Button>
                        )}
                    </div>
                </Form>
            </Card>
        </Container>
    );
};

export default AddHealthProfile;
