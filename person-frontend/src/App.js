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
                  <Route path="/health_profile" element={<HealthProfile />} />
                  <Route path="/add_health_profile" element={<AddHealthProfile />} />
                  <Route path="/update_health_profile" element={<UpdateHealthProfile />} />
                  <Route path="/workout_plan" element={<WorkoutPlan />} />
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