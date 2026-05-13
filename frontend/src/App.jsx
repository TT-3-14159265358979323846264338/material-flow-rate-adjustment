import { BrowserRouter, Routes, Route } from 'react-router-dom';
import LoginForm from './pages/guest/LoginForm';

const App = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<LoginForm />} />
      </Routes>
    </BrowserRouter>
  );
};

export default App;
