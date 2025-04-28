import { useContext, useEffect, useState } from "react";
import { Container, Row, Col, Image, Card, Button } from "react-bootstrap";
import { authApis, endpoints } from "../configs/Apis";
import { Link } from "react-router-dom";
import { MyUserContext } from "../configs/MyContexts";
import MySpinner from "./layout/MySpinner";

const Profile = () => {
    const [profile, setProfile] = useState(null); // State để lưu thông tin người dùng
    const [loading, setLoading] = useState(true); // Trạng thái tải dữ liệu
    const user = useContext(MyUserContext);

    useEffect(() => {
        const loadsetProfile = async () => {
            try {
                const res = await authApis().get(endpoints['profile']); // Gọi API để lấy thông tin người dùng
                setProfile(res.data); // Lưu thông tin người dùng vào state
            } catch (err) {
                console.error("Lỗi khi tải thông tin người dùng:", err);
            } finally {
                setLoading(false); // Kết thúc trạng thái tải
            }
        };

        loadsetProfile();
    }, []); // Chỉ gọi API khi Component được render lần đầu

    if (loading) {
        return (
            <Container className="mt-4 text-center">
                <MySpinner />
                <p>Đang tải thông tin...</p>
            </Container>
        );
    }

    if (!user) {
        return (
            <Container className="mt-4 text-center">
                <p className="text-danger">Bạn cần đăng nhập để xem Profile</p>
                <Link to="/login">
                    <Button variant="success">Đăng nhập</Button>
                </Link>
            </Container>
        );
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
                                    src={profile.avatar || "/default-avatar.png"}
                                    roundedCircle
                                    width="120"
                                    height="120"
                                    alt="Avatar"
                                    style={{ objectFit: "cover" }}
                                />
                            </div>
                            <p><strong>Họ và tên:</strong> {profile.fullName}</p>
                            <p><strong>Tên đăng nhập:</strong> {profile.username}</p>
                            <p><strong>Email:</strong> {profile.email}</p>
                            <p><strong>Số điện thoại:</strong> {profile.phone}</p>
                            <p><strong>Ngày sinh:</strong> {new Date(profile.dateOfBirth).toLocaleDateString("vi-VN")}</p>
                            <p><strong>Giới tính:</strong> {profile.gender === "MALE" ? "Nam" : user.gender === "FEMALE" ? "Nữ" : "Khác"}</p>
                            <p><strong>Vai trò:</strong> {profile.role === "ROLE_USER" ? "Người dùng" : "Chuyên gia"}</p>
                            <Link to="/update_profile" className="btn btn-primary w-100 mt-3">Cập nhật thông tin</Link>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default Profile;