function deleteHealthProfile(endpoint, id) {
    if (confirm("Bạn có chắc chắn muốn xoá không?")) {
        fetch(endpoint + id, {
            method: "DELETE"
        }).then(res => {
            if (res.status === 204) {
                alert("Đã xoá thành công!");
                location.reload();
            } else {
                alert("Xoá thất bại!");
            }
        });
    }
}

function deleteUser(endpoint, id) {
    if (confirm("Bạn có chắc chắn muốn xoá không?")) {
        fetch(endpoint + id, {
            method: "DELETE"
        }).then(res => {
            if (res.status === 204) {
                alert("Đã xoá thành công!");
                location.reload();
            } else {
                alert("Xoá thất bại!");
            }
        });
    }
}

function deleteWorkoutPlan(endpoint, id) {
    if (confirm("Bạn có chắc chắn muốn xoá không?")) {
        fetch(endpoint + id, {
            method: "DELETE"
        }).then(res => {
            if (res.status === 204) {
                alert("Đã xoá thành công!");
                location.reload();
            } else {
                alert("Xoá thất bại!");
            }
        });
    }
}

function deleteExercise(endpoint, id) {
    if (confirm("Bạn có chắc chắn muốn xoá không?")) {
        fetch(endpoint + id, {
            method: "DELETE"
        }).then(res => {
            if (res.status === 204) {
                alert("Đã xoá thành công!");
                location.reload();
            } else {
                alert("Xoá thất bại!");
            }
        });
    }
}

function deleteWorkoutPlanExercise(endpoint, id) {
    if (confirm("Bạn có chắc chắn muốn xoá không?")) {
        fetch(endpoint + id, {
            method: "DELETE"
        }).then(res => {
            if (res.status === 204) {
                alert("Đã xoá thành công!");
                location.reload();
            } else {
                alert("Xoá thất bại!");
            }
        });
    }
}

function deleteUserTrainer(endpoint, id) {
    if (confirm("Bạn có chắc chắn muốn xoá không?")) {
        fetch(endpoint + id, {
            method: "DELETE"
        }).then(res => {
            if (res.status === 204) {
                alert("Đã xoá thành công!");
                location.reload();
            } else {
                alert("Xoá thất bại!");
            }
        });
    }
}

function deleteHealthJournal(endpoint, id) {
    if (confirm("Bạn có chắc chắn muốn xoá không?")) {
        fetch(endpoint + id, {
            method: "DELETE"
        }).then(res => {
            if (res.status === 204) {
                alert("Đã xoá thành công!");
                location.reload();
            } else {
                alert("Xoá thất bại!");
            }
        });
    }
}

function deleteReminder(endpoint, id) {
    if (confirm("Bạn có chắc chắn muốn xoá không?")) {
        fetch(endpoint + id, {
            method: "DELETE"
        }).then(res => {
            if (res.status === 204) {
                alert("Đã xoá thành công!");
                location.reload();
            } else {
                alert("Xoá thất bại!");
            }
        });
    }
}