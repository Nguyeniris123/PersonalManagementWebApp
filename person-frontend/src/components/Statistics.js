import React, { useContext, useEffect, useState } from "react";
import { Bar } from "react-chartjs-2";
import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend, } from "chart.js";
import { authApis, endpoints } from "../configs/Apis";
import { MyUserContext } from "../configs/MyContexts";
import { Button, Container } from "react-bootstrap";
import { Link } from "react-router-dom";

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

const Statistics = () => {
    const user = useContext(MyUserContext);
    const [data, setData] = useState(null);

    const [startDate, setStartDate] = useState("2025-01-01");
    const [endDate, setEndDate] = useState("2025-05-25");

    useEffect(() => {
        const fetchStats = async () => {
            try {
                if (!startDate || !endDate) return;
                const res = await authApis().get(
                    `${endpoints.statistics}?startDate=${startDate}&endDate=${endDate}`
                );
                setData(res.data);
            } catch (err) {
                console.error(err);
            }
        };
        fetchStats();
    }, [startDate, endDate]);

    if (!user) {
        return (
            <Container className="mt-4 text-center">
                <p className="text-danger">Bạn cần đăng nhập để xem danh sách nhắc nhở</p>
                <Link to="/login">
                    <Button variant="success">Đăng nhập</Button>
                </Link>
            </Container>
        );
    }

    if (!data) return <div>Loading...</div>;

    const totalTime = data.weeklyStats.reduce((sum, item) => sum + (item[1] || 0), 0);
    const totalCalories = data.weeklyStats.reduce((sum, item) => sum + (item[2] || 0), 0);

    const weeklyLabels = data.weeklyStats.map((item) => `Tuần ${item[0]}`);
    const weeklyTimeData = data.weeklyStats.map((item) => item[1]);
    const weeklyCaloriesData = data.weeklyStats.map((item) => item[2]);

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

    const monthlyLabels = data.monthlyStats.map((item) => `Tháng ${item[0]}`);
    const monthlyTimeData = data.monthlyStats.map((item) => item[1]);
    const monthlyCaloriesData = data.monthlyStats.map((item) => item[2]);

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
        <>
            <div className="statistics-container">
                <h2>Chọn khoảng thời gian</h2>
                <div className="date-inputs">
                    <label>
                        Start Date:{" "}
                        <input
                            type="date"
                            value={startDate}
                            max={endDate}
                            onChange={(e) => setStartDate(e.target.value)}
                        />
                    </label>
                    <label>
                        End Date:{" "}
                        <input
                            type="date"
                            value={endDate}
                            min={startDate}
                            onChange={(e) => setEndDate(e.target.value)}
                        />
                    </label>
                </div>

                <div className="summary-cards">
                    <div className="card time-card">
                        <p>Tổng thời gian tập luyện</p>
                        <h3>{totalTime} phút</h3>
                    </div>
                    <div className="card calories-card">
                        <p>Tổng calo tiêu thụ</p>
                        <h3>{totalCalories} calo</h3>
                    </div>
                </div>

                <div className="charts-row">
                    <div className="chart-box">
                        <h3>Thống kê theo tuần</h3>
                        <Bar data={weeklyChartData} options={{ responsive: true }} />
                    </div>
                    <div className="chart-box">
                        <h3>Thống kê theo tháng</h3>
                        <Bar data={monthlyChartData} options={{ responsive: true }} />
                    </div>
                </div>
            </div>
            <style>{`
            .statistics-container {
                max-width: 1200px;
                margin: 40px auto;
                padding: 30px 40px;
                background-color: #ffffff;
                border-radius: 12px;
                box-shadow: 0 8px 20px rgb(0 0 0 / 0.1);
                font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
                color: #333333;
                box-sizing: border-box;
            }
            .statistics-container h2 {
                text-align: center;
                font-weight: 700;
                margin-bottom: 40px;
                color: #2c3e50;
            }
            .date-inputs {
                display: flex;
                justify-content: center;
                gap: 30px;
                margin-bottom: 40px;
                background: #f9f9f9;
                padding: 20px 30px;
                border-radius: 10px;
                box-shadow: 0 4px 14px rgb(0 0 0 / 0.05);
            }
            .date-inputs label {
                display: flex;
                flex-direction: column;
                font-weight: 600;
                font-size: 14px;
                color: #34495e;
            }
            .date-inputs input[type="date"] {
                margin-top: 8px;
                padding: 8px 12px;
                border-radius: 6px;
                border: 1px solid #ccc;
                font-size: 16px;
                width: 160px;
                box-sizing: border-box;
            }
            .summary-cards {
                display: flex;
                justify-content: center;
                gap: 40px;
                margin-bottom: 50px;
            }
            .card {
                background-color: #ffffff;
                padding: 30px 40px;
                border-radius: 12px;
                box-shadow: 0 6px 18px rgb(0 0 0 / 0.08);
                width: 280px;
                text-align: center;
            }
            .card p {
                font-weight: 700;
                font-size: 16px;
                color: #555555;
                margin-bottom: 15px;
            }
            .card.time-card h3 {
                font-weight: 700;
                font-size: 24px;
                color: #0d6efd;
            }
            .card.calories-card h3 {
                font-weight: 700;
                font-size: 24px;
                color: #198754;
            }
            .charts-row {
                display: flex;
                gap: 40px;
                justify-content: center;
                flex-wrap: nowrap;
            }
            .chart-box {
                background-color: #ffffff;
                padding: 30px 30px 40px 30px;
                border-radius: 12px;
                box-shadow: 0 6px 18px rgb(0 0 0 / 0.1);
                width: 48%;
                text-align: center;
            }
            .chart-box h3 {
                font-weight: 700;
                margin-bottom: 30px;
                color: #2c3e50;
            }
            @media (max-width: 768px) {
            .date-inputs {
                flex-direction: column;
                align-items: center;
            }
            .date-inputs label {
                width: 100%;
                max-width: 300px;
                margin-bottom: 15px;
            }
            .summary-cards {
                flex-direction: column;
                align-items: center;
            }
            .card {
                width: 100% !important;
                max-width: 320px;
                margin-bottom: 20px;
            }
            .charts-row {
                flex-direction: column;
                gap: 30px;
            }
            .chart-box {
                width: 100% !important;
            }
        `}</style>
        </>
    );
};

export default Statistics;
