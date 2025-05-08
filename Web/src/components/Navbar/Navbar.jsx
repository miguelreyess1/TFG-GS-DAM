import React, { useState } from 'react';   
import './Navbar.css';
import { FaHome, FaStar, FaGamepad, FaDownload, FaEnvelope, FaBars, FaTimes } from 'react-icons/fa';
import { IconContext } from 'react-icons';
import { Link } from 'react-router-dom';

export default function Navbar() {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <IconContext.Provider value={{ className: 'nav-icon' }}>
      {/* Versión escritorio */}
      <nav className="navbar sidebar">
        <div className='navbar-content'>
          <div className="logo">
            <img src="/logo.png" alt="MyFinance logo" className="logo-img" />
            <h3>MyFinance</h3>
          </div>
          <ul className="nav-links">
            <li><Link to="/"><FaHome /> Inicio</Link></li>
            <li><Link to="/caracteristicas"><FaStar /> Características</Link></li>
            <li><Link to="/demo"><FaGamepad /> Demo</Link></li>
            <li><Link to="/descarga"><FaDownload /> Descarga</Link></li>
            <li><Link to="/contacto"><FaEnvelope /> Contacto</Link></li>
          </ul>
        </div>
      </nav>

      {/* Versión móvil */}
      <nav className="navbar mobile-nav">
        <div className='mobile-header'>
          <div className="logo">
            <img src="/logo.png" alt="MyFinance logo" className="logo-img" />
          </div>
          <button
            className="menu-toggle"
            onClick={() => setIsOpen(!isOpen)}
            aria-label="Toggle navigation"
          >
            {isOpen ? <FaTimes /> : <FaBars />}
          </button>
        </div>
        
        <div className={`mobile-menu ${isOpen ? 'open' : ''}`}>
          <ul className="nav-links">
            <li><Link to="/" onClick={() => setIsOpen(false)}><FaHome /> Inicio</Link></li>
            <li><Link to="/caracteristicas" onClick={() => setIsOpen(false)}><FaStar /> Características</Link></li>
            <li><Link to="/demo" onClick={() => setIsOpen(false)}><FaGamepad /> Demo</Link></li>
            <li><Link to="/descarga" onClick={() => setIsOpen(false)}><FaDownload /> Descarga</Link></li>
            <li><Link to="/contacto" onClick={() => setIsOpen(false)}><FaEnvelope /> Contacto</Link></li>
          </ul>
        </div>
      </nav>
    </IconContext.Provider>
  );
}