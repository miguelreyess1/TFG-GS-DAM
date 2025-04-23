import { BrowserRouter, Routes, Route } from 'react-router-dom';
import MainLayout from './layouts/MainLayout';

import Home from './pages/Home/Home';
import Caracteristicas from './pages/Caracteristicas/Caracteristicas';
import Demo from './pages/Demo/Demo';
import Descarga from './pages/Descarga/Descarga';
import Contacto from './pages/Contacto/Contacto';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<MainLayout />}>
          <Route path="/" element={<Home />} />
          <Route path="/caracteristicas" element={<Caracteristicas />} />
          <Route path="/demo" element={<Demo />} />
          <Route path="/descarga" element={<Descarga />} />
          <Route path="/contacto" element={<Contacto />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
