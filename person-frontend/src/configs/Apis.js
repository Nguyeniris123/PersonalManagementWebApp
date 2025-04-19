import axios from "axios"
import cookie from 'react-cookies'

const BASE_URL = 'http://localhost:8080/Person/api/'


export const endpoints = {
    'register': '/users',
    'login': '/login',
    'profile': '/secure/profile',
    'health_profile': '/secure/health-profiles',
    'add_health_profile': '/secure/health-profile/add',
    'update_health_profile': '/secure/health-profile/update',
    'workout_plan': '/secure/workout-plans',
    'add_workout_plan': 'secure/workout-plan/add'
}

export const authApis = () => {
    console.info(cookie.load('token'));
    return axios.create({
        baseURL: BASE_URL,
        headers: {
            'Authorization': `Bearer ${cookie.load('token')}`,
            'Content-Type': 'application/json'
        }
    })
}

export default axios.create({
    baseURL: BASE_URL
});