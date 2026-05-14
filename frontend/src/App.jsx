import { BrowserRouter, Routes, Route } from 'react-router-dom';
import LoginForm from './pages/guest/LoginForm';
import AdminPage from './pages/auth/admin-page/AdminPage';
import UserPage from './pages/auth/user-page/UserPage';

const App = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<LoginForm />} />
        <Route path="/admin/page" element={<AdminPage />} />
        <Route path="/user/page" element={<UserPage />} />
      </Routes>
    </BrowserRouter>
  );
};

export default App;
