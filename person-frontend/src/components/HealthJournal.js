import { useContext, useEffect, useState } from "react";
import { MyUserContext } from "../configs/MyContexts";
import { Button, Card, Container, Row, Col, Alert } from "react-bootstrap";
import { authApis, endpoints } from "../configs/Apis";
import { Link, useNavigate } from "react-router-dom";
import MySpinner from "./layout/MySpinner";

const HealthJournal = () => {
    const [journals, setJournals] = useState([]);
    const [loading, setLoading] = useState(false);
    const [msg, setMsg] = useState("");
    const [search, setSearch] = useState("");
    const [page, setPage] = useState(1);
    const user = useContext(MyUserContext);
    const nav = useNavigate();

    // Tải nhật ký khi page hoặc search thay đổi
    useEffect(() => {
        const loadJournals = async () => {
            if (page > 0) {
                setLoading(true);
                try {
                    let params = { page };
                    if (search) params.kw = search;

                    const res = await authApis().get(endpoints['health_journals'], { params });

                    if (page === 1) {
                        setJournals(res.data); // Trang đầu: gán mới
                    } else if (res.data.length > 0) {
                        setJournals(prev => [...prev, ...res.data]); // Trang tiếp: nối thêm
                    } else {
                        setPage(0); // Hết dữ liệu
                    }
                } catch (err) {
                    setMsg("Lỗi khi tải nhật ký sức khỏe");
                    console.error(err);
                } finally {
                    setLoading(false);
                }
            }
        };

        loadJournals();
    }, [page, search]);

    // Khi thay đổi từ khóa tìm kiếm => reset về page 1
    useEffect(() => {
        setPage(1);
    }, [search]);

    const deleteJournal = async (journalId) => {
        if (window.confirm("Bạn có chắc chắn muốn xoá nhật kí này không?")) {
            try {
                await authApis().delete(endpoints.delete_health_journal(journalId));
                setMsg("Đã xoá thành công");
                // Gọi lại API để cập nhật danh sách
                setJournals(journals.filter(p => p.id !== journalId));
            } catch (err) {
                console.error(err);
                setMsg("Có lỗi xảy ra khi xoá!");
            }
        }
    };

    // Xóa msg sau 1 giây
    useEffect(() => {
        if (msg) {
            const timer = setTimeout(() => setMsg(""), 1000);
            return () => clearTimeout(timer);
        }
    }, [msg]);

    if (!user) {
        return (
            <Container className="mt-4 text-center">
                <p className="text-danger">Bạn cần đăng nhập để xem danh sách nhật ký</p>
                <Link to="/login">
                    <Button variant="success">Đăng nhập</Button>
                </Link>
            </Container>
        );
    }

    return (
        <Container className="mt-4">
            <div className="d-flex justify-content-between align-items-center mb-4">
                <h3>Nhật kí của bạn</h3>
                <Link to="/add-health-journal">
                    <Button variant="success">+ Tạo nhật kí mới</Button>
                </Link>
            </div>

            {msg && <Alert variant="danger">{msg}</Alert>}

            {/* Ô tìm kiếm */}
            <div className="mb-3">
                <input
                    type="text"
                    className="form-control"
                    placeholder="Tìm kiếm nội dung nhật ký..."
                    value={search}
                    onChange={e => setSearch(e.target.value)}
                />
            </div>

            {journals.length === 0 && !loading ? (
                <Alert variant="info">Bạn chưa viết nhật kí nào</Alert>
            ) : (
                <Row>
                    {journals.map((journal) => (
                        <Col md={4} key={journal.id} className="mb-4 d-flex align-items-stretch">
                            <Card className="shadow w-100 h-100 d-flex flex-column">
                                <Card.Body className="d-flex flex-column">
                                    <Card.Title>{new Date(journal.date).toLocaleDateString("vi-VN")}</Card.Title>
                                    <Card.Subtitle className="mb-2 text-muted">{journal.feeling}</Card.Subtitle>
                                    <Card.Text className="flex-grow-1">
                                        {journal.content.substring(0, 100)}...
                                    </Card.Text>
                                    <div className="d-flex justify-content-between mt-3">
                                        <Button variant="primary" onClick={() => nav(`/health-journal/${journal.id}`)}>
                                            Xem chi tiết
                                        </Button>
                                        <Button variant="danger" size="sm" onClick={() => deleteJournal(journal.id)}>Xoá</Button>
                                    </div>
                                </Card.Body>
                            </Card>
                        </Col>
                    ))}
                </Row>
            )}

            {loading && <MySpinner />}

            {page > 0 && !loading && journals.length > 0 && (
                <div className="text-center mt-2">
                    <Button variant="outline-primary" onClick={() => setPage(prev => prev + 1)}>
                        Xem thêm...
                    </Button>
                </div>
            )}
        </Container>
    );
};

export default HealthJournal;
