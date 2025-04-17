import { useContext, useEffect, useState } from "react";
import { Container, Form, Button, Alert, Card } from "react-bootstrap";
import { HealthProfileContext, HealthProfileDispatchContext } from "../configs/MyContexts";
import { authApis, endpoints } from "../configs/Apis";
import { useNavigate } from "react-router-dom";
import MySpinner from "./layout/MySpinner";

const UpdateHealthProfile = () => {
    const healthProfile = useContext(HealthProfileContext);
    const healthDispatch = useContext(HealthProfileDispatchContext);
    const nav = useNavigate();
    const [formData, setFormData] = useState(null);
    const [message, setMessage] = useState(null);
    const [loading, setLoading] = useState(false);

    const inputs = [
        { name: "height", label: "Chiều cao (cm)", type: "number", required: true },
        { name: "weight", label: "Cân nặng (kg)", type: "number", required: true },
        { name: "target", label: "Mục tiêu", type: "text" },
        { name: "stepsPerDay", label: "Số bước mỗi ngày", type: "number" },
        { name: "heartRate", label: "Nhịp tim (bpm)", type: "number" },
        { name: "waterIntake", label: "Lượng nước uống (lít)", type: "number", step: 0.1 },
    ];

    useEffect(() => {
        if (healthProfile) setFormData(healthProfile);
    }, [healthProfile]);

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            setLoading(true);
            const dataToSend = { ...formData };
            delete dataToSend.userId;
            delete dataToSend.updatedAt;
            delete dataToSend.bmi;

            const res = await authApis().put(endpoints["update_health_profile"], dataToSend);
            setMessage("Cập nhật thành công!");
            healthDispatch({ type: "set", payload: res.data });
            if (res.status === 200) nav("/health_profile");
        } catch (err) {
            console.error("Error during update: ", err);
            setMessage("Lỗi khi cập nhật hồ sơ!");
        } finally {
            setLoading(false);
        }
    };

    if (!formData)
        return (
            <Container className="mt-4 text-center">
                <MySpinner />
                <p>Đang tải dữ liệu...</p>
            </Container>
        );

    return (
        <Container className="mt-5 d-flex justify-content-center">
            <Card style={{ maxWidth: "600px", width: "100%" }} className="shadow p-4 rounded-4">
                <h3 className="text-center mb-4 text-primary">Cập nhật hồ sơ sức khỏe</h3>

                {message && <Alert variant="info">{message}</Alert>}

                <Form onSubmit={handleSubmit}>
                    {inputs.map((input) => (
                        <Form.Group key={input.name} className="mb-3">
                            <Form.Label className="fw-semibold">{input.label}</Form.Label>
                            <Form.Control
                                type={input.type}
                                name={input.name}
                                value={formData[input.name] || ""}
                                onChange={handleChange}
                                required={input.required}
                                step={input.step}
                                className="rounded-3"
                            />
                        </Form.Group>
                    ))}

                    <div className="text-center mt-4">
                        {loading ? (
                            <MySpinner />
                        ) : (
                            <Button type="submit" variant="primary" className="px-4 py-2 rounded-pill">
                                Cập nhật hồ sơ sức khoẻ
                            </Button>
                        )}
                    </div>
                </Form>
            </Card>
        </Container>
    );
};

export default UpdateHealthProfile;
