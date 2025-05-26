import React from 'react';
import { FaGithub, FaLinkedin, FaEnvelope, FaPhone, FaMapMarkerAlt, FaClock } from 'react-icons/fa';
import './Contacto.css';

export default function Contacto() {
  const contactInfo = [
    {
      icon: FaEnvelope,
      label: "Email",
      value: "miguel.reyesgomez1@gmail.com",
      href: "mailto:miguel.reyesgomez1@gmail.com",
    },
    {
      icon: FaPhone,
      label: "Teléfono",
      value: "+34 611 450 893",
      href: "tel:+34611450893",
    },
    {
      icon: FaMapMarkerAlt,
      label: "Ubicación",
      value: "España",
      href: "#",
    }
  ];

  const socialLinks = [
    {
      icon: FaGithub,
      label: "GitHub",
      href: "https://github.com/miguelreyess1",
      className: "github",
    },
    {
      icon: FaLinkedin,
      label: "LinkedIn",
      href: "https://www.linkedin.com/in/miguel-reyes-gómez-66a170293",
      className: "linkedin",
    },
    {
      icon: FaEnvelope,
      label: "Email",
      href: "mailto:miguel.reyesgomez1@gmail.com",
      className: "email",
    },
  ];

  return (
    <div className="contacto-container">
      <div className="contacto-content">
        {/* Header compacto */}
        <div className="header-section">
          <div className="badge">Contacto</div>
          <h1 className="main-title">¡Conectemos!</h1>
          <p className="subtitle">
            ¿Tienes una idea increíble? ¡Me encantaría colaborar contigo!
          </p>
        </div>

        {/* Layout horizontal */}
        <div className="main-layout">
          {/* Información de contacto */}
          <div className="contact-info-section">
            <div className="card">
              <div className="card-header">
                <h2 className="card-title">Información de contacto</h2>
              </div>
              <div className="card-content">
                <div className="contact-grid">
                  {contactInfo.map((item, index) => (
                    <a
                      key={index}
                      href={item.href}
                      className="contact-item"
                    >
                      <div className="contact-icon">
                        <item.icon />
                      </div>
                      <div className="contact-details">
                        <p className="contact-label">{item.label}</p>
                        <p className="contact-value">{item.value}</p>
                      </div>
                    </a>
                  ))}
                </div>
              </div>
            </div>
          </div>

          {/* Columna derecha */}
          <div className="right-column">
            {/* Redes sociales */}
            <div className="social-section">
              <div className="card">
                <div className="card-header">
                  <h2 className="card-title">Sígueme en redes</h2>
                </div>
                <div className="card-content">
                  <div className="social-links">
                    {socialLinks.map((social, index) => (
                      <a
                        key={index}
                        href={social.href}
                        target="_blank"
                        rel="noopener noreferrer"
                        className={`social-link ${social.className}`}
                        title={social.label}
                      >
                        <social.icon />
                      </a>
                    ))}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}