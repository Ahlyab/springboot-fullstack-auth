import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import SignUp from './Pages/SignUp';
import SignIn from './Pages/SignIn';
import UpdateProfile from './Pages/UpdateProfile';
import Layout from './components/Layout';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<SignIn />} />
          <Route path="sign-up" element={<SignUp />} />
          <Route path="updateProfile/:userId" element={<UpdateProfile />} />
        </Route>
      </Routes>
    </Router>
  );
}

export default App;

