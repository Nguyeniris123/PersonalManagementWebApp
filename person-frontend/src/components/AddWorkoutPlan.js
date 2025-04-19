import { useContext, useState } from "react";
import { Form, Button, Container, Alert, Card } from "react-bootstrap";
import { MyUserContext } from "../configs/MyContexts";
import { authApis, endpoints } from "../configs/Apis";
import { Link, useNavigate } from "react-router-dom";
import MySpinner from "./layout/MySpinner";

const AddWorkoutPlan = () => {
    const user = useContext(MyUserContext);
    const nav = useNavigate();
    const [msg, setMsg] = useState("");
    const [plan, setPlan] = useState({});
    const [loading, setLoading] = useState(false);

    const info = [
        { label: "Tên kế hoạch", type: "text", field: "name" },
        { label: "Ngày bắt đầu", type: "date", field: "startDate" },
        { label: "Ngày kết thúc", type: "date", field: "endDate" },
    ];

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

    const setState = (value, field) => {
        setPlan({ ...plan, [field]: value });
    };

    const addPlan = async (e) => {
        e.preventDefault();

        const start = new Date(plan.startDate);
        const end = new Date(plan.endDate);

        if (end < start) {
            setMsg("Ngày kết thúc không được nhỏ hơn ngày bắt đầu!");
            return;
        }

        try {
            setLoading(true);
            const res = await authApis().post(endpoints['add_workout_plan'], plan);
            if (res.status === 201)
                nav("/workout_plan");
        } catch (err) {
            console.error(err);
            setMsg("Có lỗi xảy ra khi thêm kế hoạch!");
        } finally {
            setLoading(false);
        }
    };

    return (
        <Container className="mt-5">
            <Card className="shadow-lg p-4">
                <h2 className="text-center text-primary mb-4">THÊM KẾ HOẠCH TẬP LUYỆN</h2>

                {msg && <Alert variant="danger">{msg}</Alert>}

                <Form onSubmit={addPlan}>
                    {info.map((f) => (
                        <Form.Group className="mb-3" key={f.field}>
                            <Form.Label>{f.label}</Form.Label>
                            <Form.Control
                                type={f.type}
                                placeholder={f.label}
                                required
                                value={plan[f.field] || ""}
                                onChange={(e) => setState(e.target.value, f.field)}
                                className="form-control-lg"
                            />
                        </Form.Group>
                    ))}

                    <div className="text-center">
                        {loading ? (
                            <MySpinner />
                        ) : (
                            <Button type="submit" variant="primary" className="btn-lg mt-3 px-4">Thêm kế hoạch</Button>
                        )}
                    </div>
                </Form>
            </Card>
        </Container>
    );
};

export default AddWorkoutPlan;
