import './Navbar.css';
import { FaHome, FaStar, FaDownload, FaEnvelope, FaGamepad } from 'react-icons/fa';
import { IconContext } from 'react-icons';

export default function Navbar() {
    return (
        <IconContext.Provider value={{ className: 'nav-icon' }}>
            <nav className='navbar'>
                <div className='navbar-content'>
                    <div className="logo">
                        <img src="/logo.png" alt="MyFinance logo" className="logo-img" />
                        <h3>MyFinance</h3>
                    </div>
                    <ul className="nav-links">
                        <li><a href="#"><FaHome /> Inicio</a></li>
                        <li><a href="#"><FaStar /> Características</a></li>
                        <li><a href="#"><FaGamepad /> Demo</a></li>
                        <li><a href="#"><FaDownload /> Descarga</a></li>
                        <li><a href="#"><FaEnvelope /> Contacto</a></li>
                    </ul>
                </div>
            </nav>
        </IconContext.Provider>
    );
}
