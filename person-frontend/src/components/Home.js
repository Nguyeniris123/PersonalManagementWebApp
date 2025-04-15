import { Container, Row, Col, Button, Card } from "react-bootstrap";

const Home = () => {
    return (
        <>
            {/* Phần Giới thiệu chính */}
            <Container className="mt-5 mb-5">
                <Row className="align-items-center">
                    <Col md={6}>
                        <h1 className="text-success mb-4">Chào mừng đến với <strong>Personal Manager</strong>!</h1>
                        <p>
                            Nền tảng quản lý thông tin cá nhân thông minh, giúp bạn theo dõi và cải thiện cuộc sống mỗi ngày.
                            Được thiết kế dành cho cá nhân, chuyên gia sức khỏe và huấn luyện viên.
                        </p>
                        <Button href="/register" variant="success" className="me-2">Đăng ký ngay</Button>
                        <Button href="/login" variant="outline-success">Đăng nhập</Button>
                    </Col>
                    <Col md={6}>
                        <img
                            src="https://cdn-icons-png.flaticon.com/512/3135/3135715.png"
                            alt="Personal Manager"
                            className="img-fluid"
                        />
                    </Col>
                </Row>
            </Container>

            {/* Tính năng nổi bật */}
            <Container className="mb-5">
                <h2 className="text-center text-primary mb-4">🌟 Tính năng nổi bật</h2>
                <Row>
                    {[
                        {
                            title: "Quản lý hồ sơ cá nhân",
                            desc: "Theo dõi thông tin như chiều cao, cân nặng, giới tính, ngày sinh,..."
                        },
                        {
                            title: "Theo dõi sức khỏe",
                            desc: "Tính BMI, số bước đi, nhịp tim, lượng nước uống mỗi ngày,..."
                        },
                        {
                            title: "Lập kế hoạch tập luyện",
                            desc: "Tạo và theo dõi kế hoạch luyện tập & chế độ ăn uống phù hợp mục tiêu."
                        },
                        {
                            title: "Kết nối chuyên gia",
                            desc: "Chuyên gia theo dõi và đưa ra lời tư vấn cho người dùng"
                        },
                        {
                            title: "Thống kê & báo cáo",
                            desc: "Hiển thị biểu đồ và báo cáo chi tiết về tiến trình cá nhân."
                        },
                        {
                            title: "Bảo mật & ổn định",
                            desc: "Ứng dụng sử dụng xác thực, phân quyền và lưu trữ dữ liệu an toàn."
                        }
                    ].map((feature, idx) => (
                        <Col md={4} key={idx} className="mb-4">
                            <Card className="h-100 shadow-sm">
                                <Card.Body>
                                    <Card.Title className="text-success">{feature.title}</Card.Title>
                                    <Card.Text>{feature.desc}</Card.Text>
                                </Card.Body>
                            </Card>
                        </Col>
                    ))}
                </Row>
            </Container>

            {/* Feedback người dùng */}
            <Container className="mb-5">
                <h2 className="text-center text-warning mb-4">💬 Phản hồi từ người dùng</h2>
                <Row>
                    {[
                        {
                            name: "Hồ Chí Nguyên",
                            comment: "Ứng dụng rất hữu ích, mình dùng mỗi ngày để theo dõi sức khỏe và lập kế hoạch tập luyện.",
                            avatar: "https://i.pravatar.cc/100?img=1"
                        },
                        {
                            name: "Phước Nguyên",
                            comment: "Giao diện thân thiện, dễ dùng. Tính năng báo cáo rất trực quan!",
                            avatar: "https://i.pravatar.cc/100?img=2"
                        },
                        {
                            name: "Lê Thanh Dân",
                            comment: "Tôi là huấn luyện viên và đã giới thiệu cho học viên dùng. Tất cả đều hài lòng!",
                            avatar: "https://i.pravatar.cc/100?img=3"
                        }
                    ].map((fb, idx) => (
                        <Col md={4} key={idx} className="mb-4">
                            <Card className="text-center h-100 shadow">
                                <Card.Img variant="top" src={fb.avatar} className="rounded-circle w-25 mx-auto mt-3" />
                                <Card.Body>
                                    <Card.Title>{fb.name}</Card.Title>
                                    <Card.Text>"{fb.comment}"</Card.Text>
                                </Card.Body>
                            </Card>
                        </Col>
                    ))}
                </Row>
            </Container>

            {/* Call to Action */}
            <Container className="text-center mb-5">
                <h2 className="text-danger mb-3">Sẵn sàng để bắt đầu?</h2>
                <p>Tham gia ngay để chăm sóc sức khỏe bản thân một cách thông minh và hiệu quả.</p>
                <Button href="/register" variant="danger" size="lg">Đăng ký ngay</Button>
            </Container>
        </>
    );
};

export default Home;
