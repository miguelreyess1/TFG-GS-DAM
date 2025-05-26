import React, { useState, useEffect } from 'react';
import { FaDownload,} from 'react-icons/fa';
import './Descarga.css';

export default function Descarga() {
  const [downloadCount, setDownloadCount] = useState(0);

  useEffect(() => {
    // Animación del contador de descargas
    const timer = setInterval(() => {
      setDownloadCount(prev => {
        if (prev < 50000) {
          return prev + 1000;
        }
        clearInterval(timer);
        return 50000;
      });
    }, 50);

    return () => clearInterval(timer);
  }, []);

  return (
    <div className="descarga-container">
      {/* Hero Section */}
      <section className="hero-section">
        <div className="hero-content">
          <div className="hero-text">
            <div className="badge">
              <FaDownload className="badge-icon" />
              Disponible ahora
            </div>
            <h1 className="hero-title">
              Descarga <span className="gradient-text">MyFinance</span>
            </h1>
            <h2 className="hero-subtitle">
              La app que revolucionará tus finanzas personales
            </h2>
            <p className="hero-description">
              Controla tus gastos, ahorra más y alcanza tus metas financieras con la app más completa del mercado.
            </p>

            {/* Estadísticas */}
            <div className="stats">
              <div className="stat-item">
                <div className="stat-number">{downloadCount.toLocaleString()}+</div>
                <div className="stat-label">Descargas</div>
              </div>
              <div className="stat-item">
                <div className="stat-number">4.8</div>
                <div className="stat-label">Rating</div>
              </div>
              <div className="stat-item">
                <div className="stat-number">€2M+</div>
                <div className="stat-label">Ahorrado</div>
              </div>
            </div>

            {/* Botones de descarga */}
            <div className="download-section">
              <div className="download-buttons">
                <a
                  href="/myFinance.zip"
                  download="myFinance.zip"
                  className="download-button"
                >
                  <FaDownload className="button-icon" />
                  <span className="button-text">Descargar</span>
                </a>
              </div>
            </div>
          </div>

          <div className="hero-visual">
            <div className="phone-mockup">
              <div className="phone-screen">
                <div className="app-interface">
                  <div className="app-header">
                    <div className="app-title">MyFinance</div>
                    <div className="app-balance">€2,450.00</div>
                  </div>
                  <div className="app-chart"></div>
                  <div className="app-transactions">
                    <div className="transaction-item">
                      <div className="transaction-icon"></div>
                      <div className="transaction-details">
                        <div className="transaction-name">Supermercado</div>
                        <div className="transaction-amount">-€45.20</div>
                      </div>
                    </div>
                    <div className="transaction-item">
                      <div className="transaction-icon"></div>
                      <div className="transaction-details">
                        <div className="transaction-name">Salario</div>
                        <div className="transaction-amount">+€2,500.00</div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
    </div>
  );
}