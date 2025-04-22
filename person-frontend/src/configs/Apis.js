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
    'add_workout_plan': '/secure/workout-plan/add',
    'delete_workout_plan': (planId) => `/workout-plans/${planId}`,
    'exercise': '/secure/exercises',
    'add_exercise': '/secure/exercises/add',
    'workout_plan_exercises': (workoutPlanId) => `/secure/workout-plan-exercise/${workoutPlanId}`,
    'add_workout_plan_exercises': (workoutPlanId) => `/secure/workout-plan-exercise/add/${workoutPlanId}`,
    'delete_workout_plan_exercises': (workoutPlanExerciseId) => `/workout-plans-exercise/${workoutPlanExerciseId}`,
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