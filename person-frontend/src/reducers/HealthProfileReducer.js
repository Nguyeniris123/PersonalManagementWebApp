const HealthProfileReducer = (state, action) => {
    switch (action.type) {
        case "set":
            return action.payload;
        case "update":
            return { ...state, ...action.payload };
        default:
            return state;
    }
};

export default HealthProfileReducer;