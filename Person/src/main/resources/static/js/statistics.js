let weeklyChart = null;
let monthlyChart = null;

document.getElementById('dateRangeForm').addEventListener('submit', function(e) {
    e.preventDefault();
    const startDate = document.getElementById('startDate').value;
    const endDate = document.getElementById('endDate').value;
    
    // Fetch weekly statistics
    fetch(`/api/statistics/weekly?startDate=${startDate}&endDate=${endDate}`)
        .then(response => response.json())
        .then(data => {
            updateWeeklyChart(data);
        })
        .catch(error => console.error('Error fetching weekly statistics:', error));

    // Fetch monthly statistics
    fetch(`/api/statistics/monthly?startDate=${startDate}&endDate=${endDate}`)
        .then(response => response.json())
        .then(data => {
            updateMonthlyChart(data);
        })
        .catch(error => console.error('Error fetching monthly statistics:', error));

    // Fetch total exercise time
    fetch(`/api/statistics/total-exercise-time?startDate=${startDate}&endDate=${endDate}`)
        .then(response => response.json())
        .then(data => {
            document.getElementById('totalExerciseTime').textContent = 
                Math.round(data) + ' phút';
        })
        .catch(error => console.error('Error fetching total exercise time:', error));

    // Fetch total calories
    fetch(`/api/statistics/total-calories?startDate=${startDate}&endDate=${endDate}`)
        .then(response => response.json())
        .then(data => {
            document.getElementById('totalCalories').textContent = 
                Math.round(data) + ' calo';
        })
        .catch(error => console.error('Error fetching total calories:', error));
});

function updateWeeklyChart(data) {
    const dates = data.map(item => new Date(item.date).toLocaleDateString('vi-VN'));
    const exerciseTimes = data.map(item => item.totalExerciseTime);
    const calories = data.map(item => item.totalCaloriesBurned);

    if (weeklyChart) {
        weeklyChart.destroy();
    }

    weeklyChart = new Chart(document.getElementById('weeklyChart'), {
        type: 'line',
        data: {
            labels: dates,
            datasets: [
                {
                    label: 'Thời gian tập (phút)',
                    data: exerciseTimes,
                    borderColor: 'rgb(75, 192, 192)',
                    tension: 0.1
                },
                {
                    label: 'Calo tiêu thụ',
                    data: calories,
                    borderColor: 'rgb(255, 99, 132)',
                    tension: 0.1
                }
            ]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
}

function updateMonthlyChart(data) {
    const dates = data.map(item => new Date(item.date).toLocaleDateString('vi-VN', { month: 'long', year: 'numeric' }));
    const exerciseTimes = data.map(item => item.totalExerciseTime);
    const calories = data.map(item => item.totalCaloriesBurned);

    if (monthlyChart) {
        monthlyChart.destroy();
    }

    monthlyChart = new Chart(document.getElementById('monthlyChart'), {
        type: 'bar',
        data: {
            labels: dates,
            datasets: [
                {
                    label: 'Thời gian tập (phút)',
                    data: exerciseTimes,
                    backgroundColor: 'rgba(75, 192, 192, 0.5)',
                    borderColor: 'rgb(75, 192, 192)',
                    borderWidth: 1
                },
                {
                    label: 'Calo tiêu thụ',
                    data: calories,
                    backgroundColor: 'rgba(255, 99, 132, 0.5)',
                    borderColor: 'rgb(255, 99, 132)',
                    borderWidth: 1
                }
            ]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
}

// Set default date range to current month
const today = new Date();
const firstDay = new Date(today.getFullYear(), today.getMonth(), 1);
const lastDay = new Date(today.getFullYear(), today.getMonth() + 1, 0);

document.getElementById('startDate').value = firstDay.toISOString().split('T')[0];
document.getElementById('endDate').value = lastDay.toISOString().split('T')[0];

// Load initial data
document.getElementById('dateRangeForm').dispatchEvent(new Event('submit')); 