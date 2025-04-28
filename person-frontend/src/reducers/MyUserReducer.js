import cookie from 'react-cookies'

const MyUserReducer = (current, action) => {
    switch (action.type) {
        case "login":
            return action.payload;
        case "logout":
            cookie.remove("token");
            return null;
        case "update":
            return { ...current, ...action.payload }; // Giữ lại các trường cũ và cập nhật trường mới
        default:
            return current;
    }
}

export default MyUserReducer;