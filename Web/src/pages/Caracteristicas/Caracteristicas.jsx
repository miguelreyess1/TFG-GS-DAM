import React from 'react';
import {
  FaPiggyBank,
  FaReceipt,
  FaTags,
  FaHistory,
  FaChartPie,
  FaCalculator,
  FaMoon,
  FaServer,
} from 'react-icons/fa';
import './Caracteristicas.css';

const features = [
  { icon: <FaPiggyBank className="feature-icon" />, title: 'Hucha Virtual', desc: 'Visualiza el crecimiento de tu ahorro de forma lúdica y motivadora.' },
  { icon: <FaReceipt className="feature-icon" />, title: 'Registro de Transacciones', desc: 'Añade ingresos y gastos con fecha, descripción y categoría.' },
  { icon: <FaTags className="feature-icon" />, title: 'Categorización', desc: 'Organiza tus movimientos con categorías predefinidas.' },
  { icon: <FaHistory className="feature-icon" />, title: 'Historial', desc: 'Revisa tu lista completa de transacciones con filtros rápidos.' },
  { icon: <FaChartPie className="feature-icon" />, title: 'Estadísticas Interactivas', desc: 'Analiza ingresos y gastos con gráficos detallados.' },
  { icon: <FaCalculator className="feature-icon" />, title: 'Calculadora de Interés', desc: 'Simula escenarios de ahorro con tasas personalizables.' },
  { icon: <FaMoon className="feature-icon" />, title: 'Modo Claro/Oscuro', desc: 'Cambia temas al instante para adaptarte a tu entorno.' },
  { icon: <FaServer className="feature-icon" />, title: 'Offline Seguro', desc: 'Todos tus datos se almacenan localmente sin conexión.' },
];

export default function Caracteristicas() {
  return (
    <section className="features-section">
      <div className="features-header">
        <h2>Características Destacadas</h2>
        <p className="features-subtitle">
          Descubre todo lo que MyFinance ofrece para gestionar tus finanzas sin complicaciones.
        </p>
      </div>
      <div className="features-grid">
        {features.map((f, idx) => (
          <div key={idx} className="feature-card">
            <div className="icon-wrapper">{f.icon}</div>
            <h3>{f.title}</h3>
            <p>{f.desc}</p>
          </div>
        ))}
      </div>
    </section>
  );
}