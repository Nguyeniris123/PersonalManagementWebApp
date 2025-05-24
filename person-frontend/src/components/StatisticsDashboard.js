import React, { useState, useEffect, useContext } from 'react';
import { Line, Bar } from 'react-chartjs-2';
import { Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, BarElement, Title, Tooltip, Legend } from 'chart.js';
import Apis, { authApis, endpoints } from '../configs/Apis';
import { MyUserContext } from '../configs/MyContexts';
import { Container, Form, Row, Col, Card, Spinner, Alert, Button } from 'react-bootstrap';

ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, BarElement, Title, Tooltip, Legend);

const StatisticsDashboard = () => {
    const user = useContext(MyUserContext);
    const [exerciseStats, setExerciseStats] = useState(null);
    const [healthProgress, setHealthProgress] = useState(null);
    const [selectedClient, setSelectedClient] = useState(null); // For trainers
    const [clients, setClients] = useState([]); // For trainers
    const [period, setPeriod] = useState('month');
    const [year, setYear] = useState(new Date().getFullYear());
    const [month, setMonth] = useState(new Date().getMonth() + 1);
    const [week, setWeek] = useState(Math.ceil(new Date().getDate() / 7));
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');

    useEffect(() => {
        if (user && user.role === 'ROLE_TRAINER') {
            const fetchClients = async () => {
                try {
                    const res = await authApis().get(endpoints['clients']); 
                    setClients(res.data);
                } catch (err) {
                    console.error("Failed to fetch clients: ", err);
                    setError('Không thể tải danh sách khách hàng.');
                }
            };
            fetchClients();
        }
    }, [user]);

    const fetchData = async () => {
        if (!user) return;
        setLoading(true);
        setError('');
        try {
            let exerciseParams = { period, year, month: period === 'year' ? null : month, week: period === 'week' ? week : null }; // Adjust params based on period
            let healthParams = { period, year, month: period === 'year' ? null : month, week: period === 'week' ? week : null }; // Adjust params based on period
            let exerciseEndpoint = endpoints['userExerciseStats'];
            let healthEndpoint = endpoints['userHealthProgress'];

            if (user.role === 'ROLE_TRAINER' && selectedClient) {
                exerciseEndpoint = endpoints['clientExerciseStats'](selectedClient);
                healthEndpoint = endpoints['clientHealthProgress'](selectedClient);
            } else if (user.role === 'ROLE_TRAINER' && !selectedClient){
                // If trainer doesn't select a client, show their own stats or a message
                // For now, let's assume they see their own if no client is selected.
                // Or, you could choose to show nothing or a prompt to select a client.
            }
            
            const exerciseRes = await authApis().get(exerciseEndpoint, { params: exerciseParams });
            setExerciseStats(exerciseRes.data);
            const healthRes = await authApis().get(healthEndpoint, { params: healthParams });
            setHealthProgress(healthRes.data);

        } catch (err) {
            setError('Không thể tải dữ liệu thống kê. Vui lòng kiểm tra lại các lựa chọn hoặc thử lại sau.');
            console.error("Fetch data error:", err);
            // Clear old data on error to avoid showing stale charts
            setExerciseStats(null);
            setHealthProgress(null);
        }
        setLoading(false);
    };

    useEffect(() => {
        fetchData();
    }, [user, period, year, month, week, selectedClient]);

    const exerciseTimeData = exerciseStats && exerciseStats.datasets && exerciseStats.datasets[0] ? {
        labels: exerciseStats.labels,
        datasets: [exerciseStats.datasets[0]],
    } : { labels: [], datasets: [] };

    const caloriesData = exerciseStats && exerciseStats.datasets && exerciseStats.datasets[1] ? {
        labels: exerciseStats.labels,
        datasets: [exerciseStats.datasets[1]],
    } : { labels: [], datasets: [] };

    const healthTrendData = healthProgress && healthProgress.datasets ? {
        labels: healthProgress.labels,
        datasets: healthProgress.datasets,
    } : { labels: [], datasets: [] };

    const chartOptions = (title) => ({
        responsive: true,
        plugins: {
            legend: { position: 'top' },
            title: { display: true, text: title }
        }
    });

    if (!user) return <Alert variant="warning">Vui lòng đăng nhập để xem thống kê.</Alert>;

    return (
        <Container className="mt-4">
            <h2 className="text-center text-success mb-4">Thống kê sức khỏe và hoạt động</h2>
            
            <Form className="mb-4 p-3 border rounded bg-light shadow-sm">
                <Row>
                    {user.role === 'ROLE_TRAINER' && (
                        <Col md={3}>
                            <Form.Group controlId="clientSelect">
                                <Form.Label><strong>Chọn Khách hàng</strong></Form.Label>
                                <Form.Select 
                                    onChange={(e) => setSelectedClient(e.target.value)} 
                                    value={selectedClient || ''}
                                    className="form-control-lg"
                                >
                                    <option value="">-- Xem thống kê của bạn --</option>
                                    {clients.map(c => (
                                        <option key={c.id} value={c.id}>
                                            {c.fullName || c.username} (ID: {c.id})
                                        </option>
                                    ))}
                                </Form.Select>
                            </Form.Group>
                        </Col>
                    )}
                    <Col md={user.role === 'ROLE_TRAINER' ? 2 : 3}>
                        <Form.Group controlId="periodSelect">
                            <Form.Label><strong>Giai đoạn</strong></Form.Label>
                            <Form.Select value={period} onChange={e => setPeriod(e.target.value)} className="form-control-lg">
                                <option value="week">Hàng tuần</option>
                                <option value="month">Hàng tháng</option>
                                <option value="year">Hàng năm</option>
                            </Form.Select>
                        </Form.Group>
                    </Col>
                    <Col md={2}>
                        <Form.Group controlId="yearSelect">
                            <Form.Label><strong>Năm</strong></Form.Label>
                            <Form.Control 
                                type="number" 
                                value={year} 
                                onChange={e => setYear(parseInt(e.target.value))} 
                                className="form-control-lg"
                            />
                        </Form.Group>
                    </Col>
                    {period !== 'year' && (
                        <Col md={2}>
                            <Form.Group controlId="monthSelect">
                                <Form.Label><strong>Tháng</strong></Form.Label>
                                <Form.Select 
                                    value={month} 
                                    onChange={e => setMonth(parseInt(e.target.value))} 
                                    disabled={period === 'year'}
                                    className="form-control-lg"
                                >
                                    {Array.from({ length: 12 }, (_, i) => i + 1).map(m => 
                                        <option key={m} value={m}>{m}</option>
                                    )}
                                </Form.Select>
                            </Form.Group>
                        </Col>
                    )}
                    {period === 'week' && (
                        <Col md={2}>
                            <Form.Group controlId="weekSelect">
                                <Form.Label><strong>Tuần</strong></Form.Label>
                                <Form.Select 
                                    value={week} 
                                    onChange={e => setWeek(parseInt(e.target.value))} 
                                    disabled={period !== 'week'}
                                    className="form-control-lg"
                                >
                                    {[1, 2, 3, 4, 5].map(w => 
                                        <option key={w} value={w}>{w}</option>
                                    )}
                                </Form.Select>
                            </Form.Group>
                        </Col>
                    )}
                </Row>
            </Form>

            {loading && (
                <div className="text-center my-5">
                    <Spinner animation="border" variant="success" style={{ width: '3rem', height: '3rem' }} />
                    <p className="mt-3">Đang tải dữ liệu thống kê...</p>
                </div>
            )}
            
            {error && <Alert variant="danger" className="my-3">{error}</Alert>}

            {!loading && !error && (
                <>
                    <Row className="mb-4">
                        <Col md={6} className="mb-3">
                            <Card className="shadow h-100">
                                <Card.Header className="bg-primary text-white">
                                    <h4 className="mb-0">Thời gian tập luyện</h4>
                                </Card.Header>
                                <Card.Body>
                                    {exerciseStats && exerciseStats.datasets && exerciseStats.datasets[0] ? 
                                        <Bar 
                                            options={chartOptions('Thời gian tập luyện (phút)')} 
                                            data={exerciseTimeData} 
                                        /> : 
                                        <Alert variant="info">Không có dữ liệu thời gian tập luyện trong giai đoạn này.</Alert>
                                    }
                                </Card.Body>
                                {exerciseStats && exerciseStats.totalExerciseTime && (
                                    <Card.Footer className="text-center">
                                        <strong>Tổng thời gian: </strong> 
                                        {exerciseStats.totalExerciseTime} phút
                                    </Card.Footer>
                                )}
                            </Card>
                        </Col>
                        <Col md={6} className="mb-3">
                            <Card className="shadow h-100">
                                <Card.Header className="bg-danger text-white">
                                    <h4 className="mb-0">Lượng calo tiêu thụ</h4>
                                </Card.Header>
                                <Card.Body>
                                    {exerciseStats && exerciseStats.datasets && exerciseStats.datasets[1] ? 
                                        <Bar 
                                            options={chartOptions('Lượng calo tiêu thụ (kcal)')} 
                                            data={caloriesData} 
                                        /> : 
                                        <Alert variant="info">Không có dữ liệu calo trong giai đoạn này.</Alert>
                                    }
                                </Card.Body>
                                {exerciseStats && exerciseStats.totalCaloriesBurned && (
                                    <Card.Footer className="text-center">
                                        <strong>Tổng calo tiêu thụ: </strong> 
                                        {exerciseStats.totalCaloriesBurned} kcal
                                    </Card.Footer>
                                )}
                            </Card>
                        </Col>
                    </Row>
                    
                    <Card className="shadow mb-4">
                        <Card.Header className="bg-success text-white">
                            <h4 className="mb-0">Tiến độ sức khỏe</h4>
                        </Card.Header>
                        <Card.Body>
                            {healthProgress && healthProgress.datasets ? 
                                <Line 
                                    options={chartOptions('Tiến độ sức khỏe theo thời gian')} 
                                    data={healthTrendData} 
                                    height={100}
                                /> : 
                                <Alert variant="info">Không có dữ liệu tiến độ sức khỏe trong giai đoạn này.</Alert>
                            }
                        </Card.Body>
                    </Card>
                    
                    {user.role === 'ROLE_TRAINER' && selectedClient && (
                        <Card className="shadow mb-4">
                            <Card.Header className="bg-info text-white">
                                <h4 className="mb-0">Đề xuất cho khách hàng</h4>
                            </Card.Header>
                            <Card.Body>
                                <Form>
                                    <Form.Group className="mb-3">
                                        <Form.Label>Ghi chú cho khách hàng</Form.Label>
                                        <Form.Control
                                            as="textarea"
                                            rows={3}
                                            placeholder="Nhập đề xuất, nhận xét hoặc kế hoạch điều chỉnh cho khách hàng dựa trên số liệu..."
                                        />
                                    </Form.Group>
                                    <div className="text-end">
                                        <Button variant="primary">Gửi đến khách hàng</Button>
                                    </div>
                                </Form>
                            </Card.Body>
                        </Card>
                    )}
                </>
            )}
        </Container>
    );
};

export default StatisticsDashboard;
