function deleteHealthProfile(id) {
    if (confirm("Bạn có chắc chắn muốn xoá không?")) {
        fetch(`/Person/api/health-profiles/${id}`, {
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

function deleteUser(id) {
    if (confirm("Bạn có chắc chắn muốn xoá không?")) {
        fetch(`/Person/api/users/${id}`, {
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

function deleteWorkoutPlan(id) {
    if (confirm("Bạn có chắc chắn muốn xoá không?")) {
        fetch(`/Person/api/workout-plans/${id}`, {
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

function deleteExercise(id) {
    if (confirm("Bạn có chắc chắn muốn xoá không?")) {
        fetch(`/Person/api/exercises/${id}`, {
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

function deleteWorkoutPlanExercise(id) {
    if (confirm("Bạn có chắc chắn muốn xoá không?")) {
        fetch(`/Person/api/workout-plans-exercise/${id}`, {
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