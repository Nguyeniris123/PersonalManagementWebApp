import { Button } from 'react-bootstrap';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import { Link } from 'react-router-dom';
import { MyDispatchContext, MyUserContext } from "../../configs/MyContexts";
import { useContext } from "react";

const Header = () => {
    const user = useContext(MyUserContext);
    const dispatch = useContext(MyDispatchContext);

    return (
        <Navbar collapseOnSelect expand="lg" className="bg-body-tertiary">
            <Container>
                <Navbar.Brand as={Link} to="/" className="d-flex align-items-center gap-2 text-danger fw-bold fs-4">🍀Personal Manager</Navbar.Brand>
                <Navbar.Toggle aria-controls="responsive-navbar-nav" />
                <Navbar.Collapse id="responsive-navbar-nav">
                    <Nav className="me-auto">
                        {user?.role === "ROLE_USER" && (
                            <>
                                <Link to="/health_profile" className="nav-link text-success">
                                    Hồ sơ sức khoẻ
                                </Link>
                                <Link to="/workout_plan" className="nav-link text-success">
                                    Kế hoạch tập luyện
                                </Link>
                                <Link to="/user_trainer" className="nav-link text-success">
                                    Huấn luyện viên
                                </Link>
                                
                            </>
                        )}

                        {user?.role === "ROLE_TRAINER" && (
                            <>
                                <Link to="/request_user_trainer" className="nav-link text-success">
                                    Danh sách yêu cầu kết nối
                                </Link>
                                <NavDropdown title="Dropdown" id="collapsible-nav-dropdown">
                                    <NavDropdown.Item href="#action/3.1">Action</NavDropdown.Item>
                                    <NavDropdown.Item href="#action/3.2">
                                        Another action
                                    </NavDropdown.Item>
                                    <NavDropdown.Item href="#action/3.3">Something</NavDropdown.Item>
                                    <NavDropdown.Divider />
                                    <NavDropdown.Item href="#action/3.4">
                                        Separated link
                                    </NavDropdown.Item>
                                </NavDropdown>
                            </>
                        )}
                    </Nav>
                    <Nav>
                        {user === null ? (
                            <>
                                <Link to="/register" className="nav-link text-success">Đăng ký</Link>
                                <Link to="/login" className="nav-link text-danger">Đăng nhập</Link>
                            </>
                        ) : (
                            <div className="d-flex align-items-center gap-2 ">
                                <Link to="/profile" className="d-flex align-items-center text-decoration-none text-dark">
                                    <img src={user.avatar || "/default-avatar.png"} alt="Avatar" width="40" height="40" style={{ borderRadius: "50%", objectFit: "cover" }} /></Link>
                                <Link to="/profile" className="nav-link text-success">Chào {user.username}!</Link>
                                <Button variant="outline-danger" size="sm" onClick={() => dispatch({ type: "logout" })}>Đăng xuất</Button>
                            </div>
                        )}
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
}

export default Header;