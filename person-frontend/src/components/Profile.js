import { useContext } from "react";
import { Container, Row, Col, Image, Card } from "react-bootstrap";
import { MyUserContext } from "../configs/MyContexts";

const Profile = () => {
    const user = useContext(MyUserContext);

    if (!user) {
        return <p className="text-center text-danger">Bạn chưa đăng nhập!</p>;
    }

    return (
        <Container className="mt-4">
            <Row className="justify-content-center">
                <Col md={6}>
                    <Card className="shadow">
                        <Card.Header className="bg-success text-white text-center">
                            <h4>Thông tin cá nhân</h4>
                        </Card.Header>
                        <Card.Body>
                            <div className="text-center mb-4">
                                <Image
                                    src={user.avatar || "/default-avatar.png"}
                                    roundedCircle
                                    width="120"
                                    height="120"
                                    alt="Avatar"
                                    style={{ objectFit: "cover" }}
                                />
                            </div>
                            <p><strong>Họ và tên:</strong> {user.fullName}</p>
                            <p><strong>Tên đăng nhập:</strong> {user.username}</p>
                            <p><strong>Email:</strong> {user.email}</p>
                            <p><strong>Số điện thoại:</strong> {user.phone}</p>
                            <p><strong>Ngày sinh:</strong> {new Date(user.dateOfBirth).toLocaleDateString("vi-VN")}</p>
                            <p><strong>Giới tính:</strong> {user.gender === "MALE" ? "Nam" : user.gender === "FEMALE" ? "Nữ" : "Khác"}</p>
                            <p><strong>Vai trò:</strong> {user.role === "ROLE_USER" ? "Người dùng" : "Chuyên gia"}</p>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default Profile;
