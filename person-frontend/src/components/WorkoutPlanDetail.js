import { useContext, useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { MyUserContext } from "../configs/MyContexts";
import { authApis, endpoints } from "../configs/Apis";
import { Container, Button, Card, Row, Col, Alert, Modal, Form } from "react-bootstrap";
import MySpinner from "./layout/MySpinner";

const WorkoutPlanDetail = () => {
    const { id } = useParams(); // Lấy ID kế hoạch từ URL
    const user = useContext(MyUserContext);
    const [exercises, setExercises] = useState([]); // Bài tập trong kế hoạch
    const [allExercises, setAllExercises] = useState([]); // Danh sách bài tập có sẵn
    const [loading, setLoading] = useState(true);
    const [showModal, setShowModal] = useState(false);
    const [newExercise, setNewExercise] = useState({});
    const [selectedExercise, setSelectedExercise] = useState(null); // Bài tập được chọn
    const [sets, setSets] = useState(0);
    const [reps, setReps] = useState(0);
    const [duration, setDuration] = useState(0);

    useEffect(() => {
        const loadExercises = async () => {
            try {
                const res = await authApis().get(`${endpoints["workout_plan"]}/${id}/exercises`);
                setExercises(res.data);
            } catch (err) {
                console.error("Lỗi khi tải danh sách bài tập: ", err);
                setExercises([]);
            } finally {
                setLoading(false);
            }
        };

        const loadAllExercises = async () => {
            try {
                const res = await authApis().get(endpoints["exercise"]);
                setAllExercises(res.data);
            } catch (err) {
                console.error("Lỗi khi tải danh sách bài tập có sẵn: ", err);
            }
        };

        loadExercises();
        loadAllExercises();
    }, [id]);

    const handleAddExercise = async () => {
        if (!selectedExercise) return;

        try {
            const res = await authApis().post(`${endpoints["workout_plan"]}/${id}/exercises`, {
                exerciseId: selectedExercise,
                sets,
                reps,
                duration_minutes: duration,
            });
            if (res.status === 201) {
                setExercises([...exercises, res.data]);
                setSelectedExercise(null);
                setSets(0);
                setReps(0);
                setDuration(0);
            }
        } catch (err) {
            console.error("Lỗi khi thêm bài tập vào kế hoạch: ", err);
        }
    };

    const handleCreateExercise = async () => {
        try {
            const res = await authApis().post(endpoints["exercise"], newExercise);
            if (res.status === 201) {
                setAllExercises([...allExercises, res.data]);
                setShowModal(false);
            }
        } catch (err) {
            console.error("Lỗi khi tạo bài tập mới: ", err);
        }
    };

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

    if (loading) return <MySpinner />;

    return (
        <Container className="mt-4">
            <div className="d-flex justify-content-between align-items-center mb-4">
                <h3>Chi tiết kế hoạch tập luyện</h3>
                <Button variant="success" onClick={() => setShowModal(true)}>+ Tạo bài tập mới</Button>
            </div>

            {exercises.length === 0 ? (
                <Alert variant="info">Kế hoạch này chưa có bài tập nào.</Alert>
            ) : (
                <Row>
                    {exercises.map((e) => (
                        <Col key={e.id} md={6} lg={4} className="mb-4">
                            <Card>
                                <Card.Body>
                                    <Card.Title>{e.name}</Card.Title>
                                    <Card.Text>
                                        <strong>Sets:</strong> {e.sets} <br />
                                        <strong>Reps:</strong> {e.reps} <br />
                                        <strong>Thời gian:</strong> {e.duration_minutes} phút
                                    </Card.Text>
                                </Card.Body>
                            </Card>
                        </Col>
                    ))}
                </Row>
            )}

            <h4 className="mt-5">Danh sách bài tập có sẵn</h4>
            <Row>
                {allExercises.map((e) => (
                    <Col key={e.id} md={6} lg={4} className="mb-4">
                        <Card>
                            <Card.Body>
                                <Card.Title>{e.name}</Card.Title>
                                <Card.Text>
                                    <strong>Mô tả:</strong> {e.description}
                                </Card.Text>
                                <Button variant="primary" onClick={() => setSelectedExercise(e.id)}>Chọn bài tập</Button>
                            </Card.Body>
                        </Card>
                    </Col>
                ))}
            </Row>

            {selectedExercise && (
                <div className="mt-4">
                    <h5>Thêm thông tin bài tập</h5>
                    <Form>
                        <Form.Group className="mb-3">
                            <Form.Label>Số sets</Form.Label>
                            <Form.Control type="number" value={sets} onChange={(e) => setSets(e.target.value)} />
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Số reps</Form.Label>
                            <Form.Control type="number" value={reps} onChange={(e) => setReps(e.target.value)} />
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Thời gian (phút)</Form.Label>
                            <Form.Control type="number" value={duration} onChange={(e) => setDuration(e.target.value)} />
                        </Form.Group>
                        <Button variant="success" onClick={handleAddExercise}>Thêm vào kế hoạch</Button>
                    </Form>
                </div>
            )}

            {/* Modal để tạo bài tập mới */}
            <Modal show={showModal} onHide={() => setShowModal(false)}>
                <Modal.Header closeButton>
                    <Modal.Title>Tạo bài tập mới</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group className="mb-3">
                            <Form.Label>Tên bài tập</Form.Label>
                            <Form.Control
                                type="text"
                                placeholder="Tên bài tập"
                                onChange={(e) => setNewExercise({ ...newExercise, name: e.target.value })}
                            />
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Mô tả</Form.Label>
                            <Form.Control
                                as="textarea"
                                rows={3}
                                placeholder="Mô tả bài tập"
                                onChange={(e) => setNewExercise({ ...newExercise, description: e.target.value })}
                            />
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Nhóm cơ</Form.Label>
                            <Form.Select
                                onChange={(e) => setNewExercise({ ...newExercise, muscle_group: e.target.value })}
                            >
                                <option value="">Chọn nhóm cơ</option>
                                <option value="Ngực">Ngực</option>
                                <option value="Chân">Chân</option>
                                <option value="Lưng">Lưng</option>
                                <option value="Bụng">Bụng</option>
                                <option value="Tay">Tay</option>
                                <option value="Toàn thân">Toàn thân</option>
                            </Form.Select>
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Trình độ</Form.Label>
                            <Form.Select
                                onChange={(e) => setNewExercise({ ...newExercise, level: e.target.value })}
                            >
                                <option value="">Chọn trình độ</option>
                                <option value="Mới bắt đầu">Mới bắt đầu</option>
                                <option value="Trung bình">Trung bình</option>
                                <option value="Nâng cao">Nâng cao</option>
                            </Form.Select>
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Calories Burned</Form.Label>
                            <Form.Control
                                type="number"
                                placeholder="Calories Burned"
                                onChange={(e) => setNewExercise({ ...newExercise, calories_burned: e.target.value })}
                            />
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