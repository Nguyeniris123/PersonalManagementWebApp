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