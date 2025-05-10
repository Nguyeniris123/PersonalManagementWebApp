import { useContext, useEffect, useState } from "react";
import { MyUserContext } from "../configs/MyContexts";
import { Button, Card, Container, Row, Col, Alert } from "react-bootstrap";
import { authApis, endpoints } from "../configs/Apis";
import { Link } from "react-router-dom";
import MySpinner from "./layout/MySpinner";

const WorkoutPlanPage = () => {
    const [plans, setPlans] = useState([]);
    const [loading, setLoading] = useState(false);
    const [msg, setMsg] = useState("");
    const [search, setSearch] = useState("");
    const [page, setPage] = useState(1);
    const user = useContext(MyUserContext);

    useEffect(() => {
        const loadPlans = async () => {
            if (page > 0) {
                setLoading(true);
                try {
                    let params = { page };
                    if (search) params.kw = search;

                    const res = await authApis().get(endpoints["workout_plan"], { params });

                    if (page === 1) setPlans(res.data);
                    else if (res.data.length > 0) setPlans(prev => [...prev, ...res.data]);
                    else setPage(0);
                } catch (err) {
                    console.error(err);
                    setMsg("Lỗi khi tải kế hoạch tập luyện");
                } finally {
                    setLoading(false);
                }
            }
        };

        loadPlans();
    }, [page, search]);


    useEffect(() => {
        setPage(1); // Reset về page 1 khi search thay đổi
    }, [search]);

    const deletePlan = async (planId) => {
        if (window.confirm("Bạn có chắc chắn muốn xoá kế hoạch này không?")) {
            try {
                await authApis().delete(endpoints.delete_workout_plan(planId));
                setMsg("Đã xoá thành công");
                setPlans(plans.filter(p => p.id !== planId));
            } catch (err) {
                console.error(err);
                setMsg("Có lỗi xảy ra khi xoá!");
            }
        }
    };

    useEffect(() => {
        if (msg) {
            const timer = setTimeout(() => setMsg(""), 1000);
            return () => clearTimeout(timer);
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

    return (
        <Container className="mt-4">
            <div className="d-flex justify-content-between align-items-center mb-4">
                <h3>Kế hoạch tập luyện của bạn</h3>
                <Link to="/add_workout_plan">
                    <Button variant="success">+ Tạo kế hoạch mới</Button>
                </Link>
            </div>

            {/* Tìm kiếm kế hoạch */}
            <div className="mb-3">
                <input
                    type="text"
                    className="form-control"
                    placeholder="Tìm kiếm kế hoạch theo tên..."
                    value={search}
                    onChange={(e) => setSearch(e.target.value)}
                />
            </div>

            {msg && <Alert variant="danger">{msg}</Alert>}

            {plans.length === 0 && !loading ? (
                <Alert variant="info">Bạn chưa có kế hoạch tập luyện nào.</Alert>
            ) : (
                <Row>
                    {plans.map((p) => (
                        <Col key={p.id} md={6} lg={4} className="mb-4">
                            <Card style={{ backgroundColor: "#ffe4b5", border: "1px solid #ffa500" }}>
                                <Card.Body>
                                    <Card.Title>{p.name}</Card.Title>
                                    <Card.Text>
                                        <strong>Thời gian:</strong>{" "}
                                        {new Date(p.startDate).toLocaleDateString("vi-VN")} -{" "}
                                        {new Date(p.endDate).toLocaleDateString("vi-VN")}
                                    </Card.Text>
                                    <Link to={`/workout_plan/${p.id}`}>
                                        <Button variant="primary" size="sm">Xem chi tiết</Button>
                                    </Link>
                                    <Button
                                        variant="danger"
                                        size="sm"
                                        className="ms-2"
                                        onClick={() => deletePlan(p.id)}
                                    >
                                        Xoá
                                    </Button>
                                </Card.Body>
                            </Card>
                        </Col>
                    ))}
                </Row>
            )}

            {loading && <MySpinner />}

            {page > 0 && !loading && plans.length > 0 && (
                <div className="text-center mt-2">
                    <Button variant="outline-primary" onClick={() => setPage(prev => prev + 1)}>
                        Xem thêm...
                    </Button>
                </div>
            )}
        </Container>
    );
};

export default WorkoutPlanPage;
