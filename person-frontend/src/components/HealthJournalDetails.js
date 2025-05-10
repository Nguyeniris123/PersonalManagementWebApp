import { useContext, useEffect, useState } from "react";
import { MyUserContext } from "../configs/MyContexts";
import { useParams, useNavigate, Link } from "react-router-dom";
import { Container, Card, Button } from "react-bootstrap";
import { authApis, endpoints } from "../configs/Apis";
import MySpinner from "./layout/MySpinner";

const HealthJournalDetails = () => {
    const { id } = useParams(); // Lấy ID từ URL
    const [journal, setJournal] = useState(null); // State để lưu thông tin nhật ký
    const [loading, setLoading] = useState(true); // Trạng thái tải dữ liệu
    const user = useContext(MyUserContext);
    const nav = useNavigate();

    useEffect(() => {
        const loadJournal = async () => {
            try {
                const res = await authApis().get(endpoints.health_journal_by_id(id)); // Gọi API để lấy chi tiết nhật ký
                setJournal(res.data); // Lưu dữ liệu vào state
            } catch (err) {
                console.error("Lỗi khi tải chi tiết nhật ký:", err);
            } finally {
                setLoading(false); // Kết thúc trạng thái tải
            }
        };

        loadJournal();
    }, [id]);

    if (loading) return <MySpinner />; // Hiển thị spinner khi đang tải dữ liệu

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

    if (!journal) {
        return (
            <Container className="mt-4 text-center">
                <p className="text-danger">Không tìm thấy nhật ký</p>
                <Button variant="secondary" onClick={() => nav("/health-journals")}>
                    Quay lại danh sách
                </Button>
            </Container>
        );
    }

    return (
        <Container className="mt-4">
            <Card className="shadow">
                <Card.Header className="bg-primary text-white text-center">
                    <h4>Chi tiết nhật ký sức khỏe</h4>
                </Card.Header>
                <Card.Body>
                    <Card.Title>Ngày: {new Date(journal.date).toLocaleDateString("vi-VN")}</Card.Title>
                    <Card.Subtitle className="mb-3 text-muted">Cảm xúc: {journal.feeling}</Card.Subtitle>
                    <Card.Text>
                        <strong>Nội dung:</strong>
                        <p>{journal.content}</p>
                    </Card.Text>
                    <Button variant="secondary" onClick={() => nav("/health-journals")}>
                        Quay lại danh sách
                    </Button>
                </Card.Body>
            </Card>
        </Container>
    );
};

export default HealthJournalDetails;