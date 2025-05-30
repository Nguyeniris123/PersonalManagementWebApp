import { BrowserRouter, Route, Routes } from "react-router-dom";
import Header from "./components/layout/Header";
import Footer from "./components/layout/Footer";
import Home from "./components/Home";
import Profile from "./components/Profile";
import 'bootstrap/dist/css/bootstrap.min.css';
import { Container } from "react-bootstrap";
import Register from "./components/Register";
import Login from "./components/Login";
import { HealthProfileContext, HealthProfileDispatchContext, MyDispatchContext, MyUserContext } from "./configs/MyContexts";
import { useReducer } from "react";
import MyUserReducer from "./reducers/MyUserReducer";
import HealthProfile from "./components/HealthProfile";
import AddHealthProfile from "./components/AddHealthProfile";
import HealthProfileReducer from "./reducers/HealthProfileReducer";
import UpdateHealthProfile from "./components/UpdateHealthProfile";
import WorkoutPlan from "./components/WorkoutPlan";
import AddWorkoutPlan from "./components/AddWorkoutPlan";
import WorkoutPlanDetail from "./components/WorkoutPlanDetail";
import UserTrainer from "./components/UserTrainer";
import RequestUserTrainer from "./components/RequestUserTrainer";
import UpdateProfile from "./components/UpdateProfile";
import HealthJournal from "./components/HealthJournal";
import HealthJournalDetails from "./components/HealthJournalDetails";
import AddHealthJournal from "./components/AddHealthJournal";
import Reminder from "./components/Reminder";
import AddReminder from "./components/AddReminder";
import UpdateReminder from "./components/UpdateReminder";
import Statistics from "./components/Statistics";
import Messenger from "./components/Messenger";
import TrainerStatistics from "./components/TrainerStatistics";


const App = () => {
  const [user, dispatch] = useReducer(MyUserReducer, null);
  const [health_profile, healthProfiledispatch] = useReducer(HealthProfileReducer, null);

  return (
    <MyUserContext.Provider value={user}>
      <MyDispatchContext.Provider value={dispatch}>
        <HealthProfileContext.Provider value={health_profile}>
          <HealthProfileDispatchContext.Provider value={healthProfiledispatch}>
            <BrowserRouter>
              <Header />

              <Container>
                <Routes>
                  <Route path="/" element={<Home />} />
                  <Route path="/register" element={<Register />} />
                  <Route path="/login" element={<Login />} />
                  <Route path="/profile" element={<Profile />} />
                  <Route path="/update_profile" element={<UpdateProfile />} />
                  <Route path="/health_profile" element={<HealthProfile />} />
                  <Route path="/add_health_profile" element={<AddHealthProfile />} />
                  <Route path="/update_health_profile" element={<UpdateHealthProfile />} />
                  <Route path="/workout_plan" element={<WorkoutPlan />} />
                  <Route path="/add_workout_plan" element={<AddWorkoutPlan />} />
                  <Route path="/workout_plan/:id" element={<WorkoutPlanDetail />} />
                  <Route path="/user_trainer" element={<UserTrainer />} />
                  <Route path="/request_user_trainer" element={<RequestUserTrainer />} />
                  <Route path="/health-journals" element={<HealthJournal />} />
                  <Route path="/health-journal/:id" element={<HealthJournalDetails />} />
                  <Route path="/add-health-journal" element={<AddHealthJournal />} />
                  <Route path="/reminders" element={<Reminder />} />,
                  <Route path="/update-reminder/:id" element={<UpdateReminder />} />
                  <Route path="/add-reminder" element={<AddReminder />} />
                  <Route path="/statistics" element={<Statistics />} />
                  <Route path="/trainer-statistics" element={<TrainerStatistics />} />
                  <Route path="/Messenger" element={<Messenger />} />

                </Routes>
              </Container>

              <Footer />
            </BrowserRouter>
          </HealthProfileDispatchContext.Provider>
        </HealthProfileContext.Provider>
      </MyDispatchContext.Provider>
    </MyUserContext.Provider>
  );
}

export default App;