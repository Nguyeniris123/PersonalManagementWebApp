// Hàm yêu cầu quyền thông báo
function requestNotificationPermission() {
    if (!("Notification" in window)) {
        alert("Trình duyệt này không hỗ trợ thông báo desktop");
        return;
    }

    Notification.requestPermission().then(function (permission) {
        if (permission === "granted") {
            showNotification("Thông báo đã được bật!", "Bạn sẽ nhận được các thông báo nhắc nhở.");
            loadNotificationSettings();
        }
    });
}

// Hàm hiển thị thông báo
function showNotification(title, body) {
    if (Notification.permission === "granted") {
        const notification = new Notification(title, {
            body: body,
            icon: "/images/notification-icon.png"
        });
    }
}

// Hàm tải cài đặt thông báo từ server
function loadNotificationSettings() {
    fetch('/api/notifications')
        .then(response => response.json())
        .then(settings => {
            document.getElementById('waterReminder').checked = settings.waterReminderEnabled;
            document.getElementById('exerciseReminder').checked = settings.exerciseReminderEnabled;
            document.getElementById('restReminder').checked = settings.restReminderEnabled;
        })
        .catch(error => console.error('Error loading notification settings:', error));
}

// Hàm lưu cài đặt thông báo
function saveNotificationSettings() {
    const settings = {
        waterReminderEnabled: document.getElementById('waterReminder').checked,
        exerciseReminderEnabled: document.getElementById('exerciseReminder').checked,
        restReminderEnabled: document.getElementById('restReminder').checked
    };

    fetch('/api/notifications', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(settings)
    })
    .then(response => response.json())
    .then(settings => {
        showNotification("Cài đặt đã được lưu", "Các thông báo nhắc nhở đã được cập nhật.");
    })
    .catch(error => console.error('Error saving notification settings:', error));
}

// Hàm đặt lịch nhắc nhở
function scheduleReminder(type, interval) {
    switch(type) {
        case 'water':
            setInterval(() => {
                showNotification("Nhắc nhở uống nước", "Đã đến lúc uống nước rồi!");
            }, interval);
            break;
        case 'exercise':
            setInterval(() => {
                showNotification("Nhắc nhở tập luyện", "Đã đến lúc tập luyện rồi!");
            }, interval);
            break;
        case 'rest':
            setInterval(() => {
                showNotification("Nhắc nhở nghỉ ngơi", "Đã đến lúc nghỉ ngơi rồi!");
            }, interval);
            break;
    }
}

// Khởi tạo các nhắc nhở khi trang được tải
document.addEventListener('DOMContentLoaded', function() {
    // Yêu cầu quyền thông báo
    requestNotificationPermission();
    
    // Đặt các nhắc nhở (thời gian tính bằng milliseconds)
    scheduleReminder('water', 3600000); // Nhắc uống nước mỗi giờ
    scheduleReminder('exercise', 7200000); // Nhắc tập luyện mỗi 2 giờ
    scheduleReminder('rest', 10800000); // Nhắc nghỉ ngơi mỗi 3 giờ

    // Thêm sự kiện cho nút lưu cài đặt
    document.querySelectorAll('input[type="checkbox"]').forEach(checkbox => {
        checkbox.addEventListener('change', saveNotificationSettings);
    });
}); 