import { Container, Row, Col } from "react-bootstrap";
import { Link } from "react-router-dom";

const Footer = () => {
    return (
        <footer className="bg-dark text-white mt-5 py-4">
            <Container>
                <Row>
                    <Col md={4}>
                        <h5>About us</h5>
                        <p>We are committed to helping individuals monitor their health and fitness with personalized insights and guidance. Our platform empowers you to track your activity, set health goals, and make informed decisions about your well-being.</p>
                    </Col>
                    <Col md={4}>
                        <h5>Quick Links</h5>
                        <ul className="list-unstyled">
                            <li><Link to="/" className="text-white text-decoration-none">Trang chủ</Link></li>
                            <li><Link to="/sanpham" className="text-white text-decoration-none">Sản phẩm</Link></li>
                            <li><Link to="/lienhe" className="text-white text-decoration-none">Liên hệ</Link></li>
                        </ul>
                    </Col>
                    <Col md={4}>
                        <h5>Contact Information</h5>
                        <p>support@healthtracker.com</p>
                        <p>Hotline: 0123 456 789</p>
                        <p>123 Wellness Ave, Healthy City, HC 56789</p>
                    </Col>
                </Row>
                <hr className="border-light" />
                <p className="text-center mb-0">© 2025 Nhom 20. All rights reserved.</p>
            </Container>
        </footer>
    );
}

export default Footer;