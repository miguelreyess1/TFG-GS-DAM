import { Outlet } from 'react-router-dom';
import Navbar from '../components/Navbar/Navbar';
import Toolbar from '../components/Toolbar/Toolbar';

export default function MainLayout() {
  return (
    <>
      <Navbar />
      <Toolbar />
      <main className="content">
        <Outlet />
      </main>
    </>
  );
}
