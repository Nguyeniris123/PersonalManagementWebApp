document.getElementById('statisticsForm').addEventListener('submit', function(e) {
    e.preventDefault();
    const userId = document.getElementById('userId').value;
    const startDate = document.getElementById('startDate').value;
    const endDate = document.getElementById('endDate').value;
    
    if (!userId || !startDate || !endDate) {
        alert('Vui lòng chọn đầy đủ thông tin');
        return;
    }

    if (new Date(startDate) > new Date(endDate)) {
        alert('Ngày bắt đầu phải nhỏ hơn hoặc bằng ngày kết thúc');
        return;
    }

    // Fetch total exercise time
    fetch(`/api/statistics/total-exercise-time?userId=${userId}&startDate=${startDate}&endDate=${endDate}`)
        .then(response => response.json())
        .then(data => {
            document.getElementById('totalExerciseTime').textContent = 
                Math.round(data) + ' phút';
        })
        .catch(error => {
            console.error('Error fetching total exercise time:', error);
            document.getElementById('totalExerciseTime').textContent = '0 phút';
        });

    // Fetch total calories
    fetch(`/api/statistics/total-calories?userId=${userId}&startDate=${startDate}&endDate=${endDate}`)
        .then(response => response.json())
        .then(data => {
            document.getElementById('totalCalories').textContent = 
                Math.round(data) + ' calo';
        })
        .catch(error => {
            console.error('Error fetching total calories:', error);
            document.getElementById('totalCalories').textContent = '0 calo';
        });

    // Fetch exercise details
    fetch(`/api/statistics/exercise-details?userId=${userId}&startDate=${startDate}&endDate=${endDate}`)
        .then(response => response.json())
        .then(data => {
            const tbody = document.getElementById('exerciseDetails');
            tbody.innerHTML = '';
            
            data.forEach(exercise => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${exercise.exerciseName}</td>
                    <td>${Math.round(exercise.totalDuration)} phút</td>
                    <td>${Math.round(exercise.totalCalories)} calo</td>
                `;
                tbody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error fetching exercise details:', error);
            document.getElementById('exerciseDetails').innerHTML = 
                '<tr><td colspan="3" class="text-center">Không có dữ liệu</td></tr>';
        });
});

// Set default date range to current month
const today = new Date();
const firstDay = new Date(today.getFullYear(), today.getMonth(), 1);
const lastDay = new Date(today.getFullYear(), today.getMonth() + 1, 0);

document.getElementById('startDate').value = firstDay.toISOString().split('T')[0];
document.getElementById('endDate').value = lastDay.toISOString().split('T')[0]; 