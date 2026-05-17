import { BrowserRouter, Routes, Route } from 'react-router-dom';
import TokenGuard from './pages/auth/components/TokenGuard';
import LoginForm from './pages/guest/LoginForm';
import AdminPage from './pages/auth/admin-page/AdminPage';
import UserPage from './pages/auth/user-page/UserPage';

const App = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<LoginForm />} />
        <Route path="/admin/page" element={
          <TokenGuard>
            <AdminPage />
          </TokenGuard>} />
        <Route path="/user/page" element={
          <TokenGuard>
            <UserPage />
          </TokenGuard>} />
      </Routes>
    </BrowserRouter>
  );
};

export default App;
