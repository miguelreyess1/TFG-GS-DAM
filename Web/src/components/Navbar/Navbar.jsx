import './Navbar.css';
import { FaHome, FaStar, FaDownload, FaEnvelope, FaGamepad } from 'react-icons/fa';
import { IconContext } from 'react-icons';
import { Link } from 'react-router-dom';

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
                        <li>
                        <Link to="/"><FaHome /> Inicio</Link>
                        </li>
                        <li>
                        <Link to="/caracteristicas"><FaStar /> Características</Link>
                        </li>
                        <li>
                        <Link to="/demo"><FaGamepad /> Demo</Link>
                        </li>
                        <li>
                        <Link to="/descarga"><FaDownload /> Descarga</Link>
                        </li>
                        <li>
                        <Link to="/contacto"><FaEnvelope /> Contacto</Link>
                        </li>
                    </ul>
                </div>
            </nav>
        </IconContext.Provider>
    );
}
