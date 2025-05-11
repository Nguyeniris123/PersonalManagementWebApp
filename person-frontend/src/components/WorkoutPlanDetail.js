import { useContext, useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { MyUserContext } from "../configs/MyContexts";
import { authApis, endpoints } from "../configs/Apis";
import { Container, Button, Card, Row, Col, Alert, Modal, Form } from "react-bootstrap";
import MySpinner from "./layout/MySpinner";

const WorkoutPlanDetail = () => {
    const { id } = useParams();
    const user = useContext(MyUserContext);

    const [exercises, setExercises] = useState([]);
    const [allExercises, setAllExercises] = useState([]);
    const [msg, setMsg] = useState("");
    const [showModal, setShowModal] = useState(false);
    const [newExercise, setNewExercise] = useState({});
    const [sets, setSets] = useState(10);
    const [reps, setReps] = useState(1);
    const [duration, setDuration] = useState(10);
    const [selectedExercise, setSelectedExercise] = useState(null);
    const [searchTerm, setSearchTerm] = useState("");
    const [loading, setLoading] = useState(false);
    const [page, setPage] = useState(1);

    const exerciseFields = [
        { label: "Tên bài tập", type: "text", key: "name", placeholder: "Tên bài tập" },
        { label: "Mô tả", type: "textarea", key: "description", placeholder: "Mô tả bài tập" },
        { label: "Nhóm cơ", type: "select", key: "muscleGroup", options: ["Ngực", "Chân", "Lưng", "Bụng", "Tay", "Toàn thân"] },
        { label: "Trình độ", type: "select", key: "level", options: ["Mới bắt đầu", "Trung bình", "Nâng cao"] },
        { label: "Calories Burned", type: "number", key: "caloriesBurned", placeholder: "Calories Burned" },
    ];

    useEffect(() => {
        const loadExercises = async () => {
            try {
                const res = await authApis().get(endpoints.workout_plan_exercises(id));
                setExercises(res.data);
            } catch (err) {
                console.error("Lỗi khi tải danh sách bài tập trong kế hoạch:", err);
                setExercises([]);
            }
        };

        loadExercises();
    }, [id]);

    useEffect(() => {
        const loadAllExercises = async () => {
            if (page <= 0) return;

            try {
                setLoading(true);
                const res = await authApis().get(endpoints.exercise, {
                    params: {
                        page: page,
                        kw: searchTerm,
                    }
                });

                if (res.data.length === 0)
                    setPage(0); // hết trang
                else {
                    if (page === 1)
                        setAllExercises(res.data);
                    else
                        setAllExercises((prev) => [...prev, ...res.data]);
                }
            } catch (err) {
                console.error("Lỗi khi tải bài tập gợi ý:", err);
                setMsg("Không thể tải bài tập!");
            } finally {
                setLoading(false);
            }
        };

        loadAllExercises();
    }, [page, searchTerm]);

    useEffect(() => {
        setPage(1); // reset trang khi từ khóa đổi
    }, [searchTerm]);

    const handleAddExercise = async () => {
        if (!selectedExercise) return;

        try {
            const res = await authApis().post(endpoints.add_workout_plan_exercises(id), {
                exerciseId: selectedExercise,
                sets,
                reps,
                durationMinutes: duration,
            });

            if (res.status === 201) {
                setExercises([...exercises, res.data]);
                setSelectedExercise(null);
                setSets(10);
                setReps(1);
                setDuration(10);
                setMsg("Đã thêm bài tập vào kế hoạch!");
            }
        } catch (err) {
            console.error(err);
            setMsg("Có lỗi xảy ra khi thêm bài tập vào kế hoạch!");
        }
    };

    const handleCreateExercise = async () => {
        try {
            const res = await authApis().post(endpoints.add_exercise, newExercise);
            if (res.status === 201) {
                const createdExercise = res.data;
                setAllExercises([...allExercises, createdExercise]);

                const addRes = await authApis().post(endpoints.add_workout_plan_exercises(id), {
                    exerciseId: createdExercise.id,
                    sets,
                    reps,
                    durationMinutes: duration,
                });

                if (addRes.status === 201) {
                    setExercises([...exercises, addRes.data]);
                    setShowModal(false);
                    setNewExercise({});
                    setSets(10);
                    setReps(1);
                    setDuration(10);
                    setMsg("Đã tạo và thêm bài tập vào kế hoạch!");
                }
            }
        } catch (err) {
            console.error(err);
            setMsg("Có lỗi xảy ra khi tạo bài tập mới!");
        }
    };

    const handleDeleteExercise = async (exerciseId) => {
        if (!window.confirm("Bạn có chắc chắn muốn xóa bài tập này?")) return;

        try {
            await authApis().delete(endpoints.delete_workout_plan_exercises(exerciseId));
            setExercises(exercises.filter((e) => e.id !== exerciseId));
            setMsg("Xóa bài tập thành công!");
        } catch (err) {
            console.error(err);
            setMsg("Có lỗi xảy ra khi xóa bài tập!");
        }
    };

    useEffect(() => {
        if (msg) {
            const timer = setTimeout(() => setMsg(""), 2000);
            return () => clearTimeout(timer);
        }
    }, [msg]);

    if (!user) {
        return (
            <Container className="mt-4 text-center">
                <p className="text-danger">Bạn cần đăng nhập để xem chi tiết kế hoạch</p>
                <Link to="/login">
                    <Button variant="success">Đăng nhập</Button>
                </Link>
            </Container>
        );
    }

    return (
        <Container className="mt-4">
            <div className="d-flex justify-content-between align-items-center mb-4">
                <h3>Chi tiết kế hoạch tập luyện</h3>
                <Button variant="success" onClick={() => setShowModal(true)}>+ Tạo bài tập mới</Button>
            </div>

            {msg && <Alert variant="info">{msg}</Alert>}

            {exercises.length === 0 ? (
                <Alert variant="warning">Kế hoạch này chưa có bài tập nào.</Alert>
            ) : (
                <Row>
                    {exercises.map((e) => (
                        <Col key={e.id} md={6} lg={4} className="mb-4">
                            <Card className="h-100 border border-success" style={{ backgroundColor: "#e8f5e9" }}>
                                <Card.Body className="d-flex flex-column">
                                    <Card.Title className="bg-success text-white text-center">{e.exerciseId.name}</Card.Title>
                                    <Card.Text>
                                        <strong>Mô tả:</strong> {e.exerciseId.description} <br />
                                        <strong>Nhóm cơ:</strong> {e.exerciseId.muscleGroup} <br />
                                        <strong>Cấp độ:</strong> {e.exerciseId.level} <br />
                                        <strong>Calories:</strong> {e.exerciseId.caloriesBurned} <br />
                                        <strong>Sets:</strong> {e.sets} <br />
                                        <strong>Reps:</strong> {e.reps} <br />
                                        <strong>Thời gian:</strong> {e.durationMinutes} phút
                                    </Card.Text>
                                    <Button
                                        variant="danger"
                                        size="sm"
                                        className="mt-auto"
                                        onClick={() => handleDeleteExercise(e.id)}
                                    >
                                        Xóa
                                    </Button>
                                </Card.Body>
                            </Card>
                        </Col>
                    ))}
                </Row>
            )}

            <h4 className="mt-5 mb-3">Danh sách bài tập gợi ý</h4>
            <Form className="mb-4">
                <Form.Control
                    type="text"
                    placeholder="Tìm kiếm bài tập gợi ý..."
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                />
            </Form>

            <Row>
                {allExercises.map((e) => (
                    <Col key={e.id} md={6} lg={4} className="mb-4">
                        <Card className={`h-100 border ${selectedExercise === e.id ? "border-danger shadow" : "border-success"}`} style={{ backgroundColor: selectedExercise === e.id ? "#ffe6e6" : "#f0f8ff" }}>
                            <Card.Body className="d-flex flex-column">
                                <Card.Title>{e.name}</Card.Title>
                                <Card.Text className="flex-grow-1">
                                    <strong>Mô tả:</strong> {e.description} <br />
                                    <strong>Nhóm cơ:</strong> {e.muscleGroup} <br />
                                    <strong>Trình độ:</strong> {e.level} <br />
                                    <strong>Calories:</strong> {e.caloriesBurned}
                                </Card.Text>
                                <Button variant="primary" onClick={() => setSelectedExercise(e.id)}>Chọn bài tập</Button>
                            </Card.Body>
                        </Card>
                    </Col>
                ))}
            </Row>

            {loading && <MySpinner />}

            {page > 0 && (
                <div className="text-center mt-3">
                    <Button variant="outline-primary" onClick={() => setPage(page + 1)}>Xem thêm</Button>
                </div>
            )}

            {selectedExercise && (
                <div className="mt-4">
                    <h5>Thêm vào kế hoạch</h5>
                    <Form>
                        <Form.Group className="mb-2">
                            <Form.Label>Số sets</Form.Label>
                            <Form.Control type="number" value={sets} onChange={(e) => setSets(e.target.value)} />
                        </Form.Group>
                        <Form.Group className="mb-2">
                            <Form.Label>Số reps</Form.Label>
                            <Form.Control type="number" value={reps} onChange={(e) => setReps(e.target.value)} />
                        </Form.Group>
                        <Form.Group className="mb-2">
                            <Form.Label>Thời gian (phút)</Form.Label>
                            <Form.Control type="number" value={duration} onChange={(e) => setDuration(e.target.value)} />
                        </Form.Group>
                        <Button variant="success" onClick={handleAddExercise}>Thêm vào kế hoạch</Button>
                    </Form>
                </div>
            )}

            {/* Modal tạo bài tập mới */}
            <Modal show={showModal} onHide={() => setShowModal(false)}>
                <Modal.Header closeButton>
                    <Modal.Title>Tạo bài tập mới</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        {exerciseFields.map((field, idx) => (
                            <Form.Group className="mb-3" key={idx}>
                                <Form.Label>{field.label}</Form.Label>
                                {field.type === "textarea" ? (
                                    <Form.Control
                                        as="textarea"
                                        rows={3}
                                        placeholder={field.placeholder}
                                        onChange={(e) => setNewExercise({ ...newExercise, [field.key]: e.target.value })}
                                    />
                                ) : field.type === "select" ? (
                                    <Form.Select onChange={(e) => setNewExercise({ ...newExercise, [field.key]: e.target.value })}>
                                        <option value="">Chọn {field.label.toLowerCase()}</option>
                                        {field.options.map((opt, i) => (
                                            <option key={i} value={opt}>{opt}</option>
                                        ))}
                                    </Form.Select>
                                ) : (
                                    <Form.Control
                                        type={field.type}
                                        placeholder={field.placeholder}
                                        onChange={(e) => setNewExercise({ ...newExercise, [field.key]: e.target.value })}
                                    />
                                )}
                            </Form.Group>
                        ))}

                        <Form.Group className="mb-2">
                            <Form.Label>Số sets</Form.Label>
                            <Form.Control type="number" value={sets} onChange={(e) => setSets(e.target.value)} />
                        </Form.Group>
                        <Form.Group className="mb-2">
                            <Form.Label>Số reps</Form.Label>
                            <Form.Control type="number" value={reps} onChange={(e) => setReps(e.target.value)} />
                        </Form.Group>
                        <Form.Group className="mb-2">
                            <Form.Label>Thời gian (phút)</Form.Label>
                            <Form.Control type="number" value={duration} onChange={(e) => setDuration(e.target.value)} />
                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={() => setShowModal(false)}>Hủy</Button>
                    <Button variant="primary" onClick={handleCreateExercise}>Tạo bài tập</Button>
                </Modal.Footer>
            </Modal>
        </Container>
    );
};

export default WorkoutPlanDetail;
