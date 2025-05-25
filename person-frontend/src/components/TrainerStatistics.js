import React, { useContext, useEffect, useState } from "react";
import { Bar } from "react-chartjs-2";
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    BarElement,
    Title,
    Tooltip,
    Legend,
} from "chart.js";
import { authApis, endpoints } from "../configs/Apis";
import { MyUserContext } from "../configs/MyContexts";
import { Button, Container, Form } from "react-bootstrap";
import { Link } from "react-router-dom";

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

const TrainerStatistics = () => {
    const user = useContext(MyUserContext);
    const [connectedUsers, setConnectedUsers] = useState([]);
    const [selectedUserId, setSelectedUserId] = useState("");
    const [startDate, setStartDate] = useState("2025-01-01");
    const [endDate, setEndDate] = useState("2025-05-25");
    const [data, setData] = useState(null);
    const [healthProfile, setHealthProfile] = useState(null);
    const [loadingUsers, setLoadingUsers] = useState(false);
    const [loadingStats, setLoadingStats] = useState(false);
    const [error, setError] = useState("");

    useEffect(() => {
        const fetchConnectedUsers = async () => {
            setLoadingUsers(true);
            setError("");
            try {
                const res = await authApis().get(endpoints.connectedUsers);
                setConnectedUsers(res.data);
            } catch (err) {
                setError("Lỗi khi tải danh sách user kết nối.");
            } finally {
                setLoadingUsers(false);
            }
        };

        if (user) fetchConnectedUsers();
    }, [user]);

    const fetchStatistics = async () => {
        if (!selectedUserId) {
            setError("Vui lòng chọn user để xem thống kê.");
            return;
        }
        setError("");
        setLoadingStats(true);
        setData(null);
        setHealthProfile(null);

        try {
            const [statsRes, profileRes] = await Promise.all([
                authApis().get(
                    `${endpoints.trainerStatistics}?userId=${selectedUserId}&startDate=${startDate}&endDate=${endDate}`
),
                authApis().get(`${endpoints.trainer_health_profile}?userId=${selectedUserId}`)
            ]);

            setData(statsRes.data);
            setHealthProfile(profileRes.data);
        } catch (err) {
            setError(
                err.response?.data || "Lỗi khi lấy dữ liệu. Có thể bạn không có quyền xem."
            );
        } finally {
            setLoadingStats(false);
        }
    };

    if (!user) {
        return (
            <Container className="mt-4 text-center">
                <p className="text-danger">Bạn cần đăng nhập để xem thống kê.</p>
                <Link to="/login">
                    <Button variant="success">Đăng nhập</Button>
                </Link>
            </Container>
        );
    }

    // Dữ liệu biểu đồ tuần
    const weeklyLabels = data?.weeklyStats?.map((item) => `Tuần ${item[0]}`) || [];
    const weeklyTimeData = data?.weeklyStats?.map((item) => item[1]) || [];
    const weeklyCaloriesData = data?.weeklyStats?.map((item) => item[2]) || [];

    // Dữ liệu biểu đồ tháng
    const monthlyLabels = data?.monthlyStats?.map((item) => `Tháng ${item[0]}`) || [];
    const monthlyTimeData = data?.monthlyStats?.map((item) => item[1]) || [];
    const monthlyCaloriesData = data?.monthlyStats?.map((item) => item[2]) || [];

    const weeklyChartData = {
        labels: weeklyLabels,
        datasets: [
            {
                label: "Thời gian (phút)",
                data: weeklyTimeData,
                backgroundColor: "rgba(54, 162, 235, 0.7)",
            },
            {
                label: "Calo tiêu thụ",
                data: weeklyCaloriesData,
                backgroundColor: "rgba(255, 99, 132, 0.7)",
            },
        ],
    };

    const monthlyChartData = {
        labels: monthlyLabels,
        datasets: [
            {
                label: "Thời gian (phút)",
                data: monthlyTimeData,
                backgroundColor: "rgba(54, 162, 235, 0.7)",
            },
            {
                label: "Calo tiêu thụ",
                data: monthlyCaloriesData,
                backgroundColor: "rgba(255, 99, 132, 0.7)",
            },
        ],
    };

    return (
        <Container style={{ maxWidth: 1000, marginTop: 40, fontFamily: "Segoe UI, Tahoma, Geneva, Verdana, sans-serif" }}>
            <h2 className="text-center mb-4">Xem thống kê người dùng đã kết nối</h2>
            {error && <p className="text-danger text-center">{error}</p>}
            {loadingUsers ? (
                <p>Đang tải danh sách user...</p>
            ) : (
                <Form.Group className="mb-3 d-flex gap-2 justify-content-center flex-wrap">
                    <Form.Label className="my-auto" style={{ minWidth: 100 }}>
                        Chọn user:
                    </Form.Label>
                    <Form.Select
                        style={{ width: 200 }}
                        value={selectedUserId}
                        onChange={(e) => setSelectedUserId(e.target.value)}
                    >
                        <option value="">-- Chọn user --</option>
                        {connectedUsers.map((u) => (
                            <option key={u.id} value={u.id}>
                                {u.fullName || u.username}
                            </option>
                        ))}
                    </Form.Select>
                </Form.Group>
            )}

            <div className="d-flex justify-content-center gap-2 mb-3 flex-wrap">
                <input
                    type="date"
                    value={startDate}
                    max={endDate}
                    onChange={(e) => setStartDate(e.target.value)}
                    style={{ padding: "8px", fontSize: 16 }}
                />
                <input
                    type="date"
                    value={endDate}
                    min={startDate}
                    onChange={(e) => setEndDate(e.target.value)}
                    style={{ padding: "8px", fontSize: 16 }}
                />
                <Button variant="primary" onClick={fetchStatistics}>
                    Xem thống kê
                </Button>
            </div>

            {loadingStats && <p className="text-center">Đang tải dữ liệu...</p>}

            {/* Hiển thị Health Profile */}
            {healthProfile && (
                <div style={{ marginBottom: 30, padding: 20, border: "1px solid #ccc", borderRadius: 10, backgroundColor: "#fafafa" }}>
                    <h4>Thông tin hồ sơ sức khỏe</h4>
                    <p><strong>Chiều cao:</strong> {healthProfile.height} cm</p>
                    <p><strong>Cân nặng:</strong> {healthProfile.weight} kg</p>
                    <p><strong>BMI:</strong> {healthProfile.bmi}</p>
                    <p><strong>Nhịp tim:</strong> {healthProfile.heartRate} bpm</p>
                    <p><strong>Mục tiêu:</strong> {healthProfile.target}</p>
                    <p><strong>Số bước mỗi ngày:</strong> {healthProfile.stepsPerDay}</p>
                    <p><strong>Lượng nước uống mỗi ngày:</strong> {healthProfile.waterIntake} lít</p>
                </div>
            )}

            {/* Biểu đồ thống kê */}
            {data && (
                <>
                    <div className="d-flex justify-content-center gap-4 mb-4 flex-wrap">
                        <div style={{ backgroundColor: "#fff", padding: 20, borderRadius: 10, boxShadow: "0 0 10px rgba(0,0,0,0.1)", width: 250, textAlign: "center" }}>
                            <p style={{ fontWeight: "bold", fontSize: 16, marginBottom: 8 }}>Tổng thời gian tập luyện</p>
                            <h3 style={{ color: "#0d6efd" }}>
                                {data.weeklyStats.reduce((sum, item) => sum + (item[1] || 0), 0)} phút
                            </h3>
                        </div>
                        <div style={{ backgroundColor: "#fff", padding: 20, borderRadius: 10, boxShadow: "0 0 10px rgba(0,0,0,0.1)", width: 250, textAlign: "center" }}>
                            <p style={{ fontWeight: "bold", fontSize: 16, marginBottom: 8 }}>Tổng calo tiêu thụ</p>
                            <h3 style={{ color: "#198754" }}>
                                {data.weeklyStats.reduce((sum, item) => sum + (item[2] || 0), 0)} calo
                            </h3>
                        </div>
                    </div>

                    <div className="d-flex gap-4 flex-wrap justify-content-center">
                        <div style={{ backgroundColor: "#fff", padding: 30, borderRadius: 12, boxShadow: "0 6px 18px rgba(0,0,0,0.1)", width: "45%" }}>
                            <h3 className="text-center mb-3">Thống kê theo tuần</h3>
                            <Bar data={weeklyChartData} options={{ responsive: true }} />
                        </div>
                        <div style={{ backgroundColor: "#fff", padding: 30, borderRadius: 12, boxShadow: "0 6px 18px rgba(0,0,0,0.1)", width: "45%" }}>
                            <h3 className="text-center mb-3">Thống kê theo tháng</h3>
                            <Bar data={monthlyChartData} options={{ responsive: true }} />
                        </div>
                    </div>
                </>
            )}
        </Container>
    );
};

export default TrainerStatistics;
