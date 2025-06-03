import { useEffect, useRef, useState } from "react";
import "./App.css";

// Importar iconos como componentes SVG simples
const PiggyBankIcon = () => (
  <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
    <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm-2 15l-5-5 1.41-1.41L10 14.17l7.59-7.59L19 8l-9 9z"/>
  </svg>
);

const ReceiptIcon = () => (
  <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
    <path d="M18 17H6v-2h12v2zm0-4H6v-2h12v2zm0-4H6V7h12v2zM3 22l1.5-1.5L6 22l1.5-1.5L9 22l1.5-1.5L12 22l1.5-1.5L15 22l1.5-1.5L18 22l1.5-1.5L21 22V2l-1.5 1.5L18 2l-1.5 1.5L15 2l-1.5 1.5L12 2l-1.5 1.5L9 2L7.5 3.5L6 2L4.5 3.5L3 2v20z"/>
  </svg>
);

const TagsIcon = () => (
  <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
    <path d="M17.63 5.84C17.27 5.33 16.67 5 16 5L5 5.01C3.9 5.01 3 5.9 3 7v10c0 1.1.9 1.99 2 1.99L16 19c.67 0 1.27-.33 1.63-.84L22 12l-4.37-6.16z"/>
  </svg>
);

const HistoryIcon = () => (
  <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
    <path d="M13 3c-4.97 0-9 4.03-9 9H1l3.89 3.89.07.14L9 12H6c0-3.87 3.13-7 7-7s7 3.13 7 7-3.13 7-7 7c-1.93 0-3.68-.79-4.94-2.06l-1.42 1.42C8.27 19.99 10.51 21 13 21c4.97 0 9-4.03 9-9s-4.03-9-9-9zm-1 5v5l4.28 2.54.72-1.21-3.5-2.08V8H12z"/>
  </svg>
);

const ChartIcon = () => (
  <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
    <path d="M11 2v20c-5.07-.5-9-4.79-9-10s3.93-9.5 9-10zm2.03 0v8.99H22c-.47-4.74-4.24-8.52-8.97-8.99zm0 11.01V22c4.74-.47 8.5-4.25 8.97-8.99h-8.97z"/>
  </svg>
);

const CalculatorIcon = () => (
  <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
    <path d="M19 3H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zm-5 14H7v-2h7v2zm3-4H7v-2h10v2zm0-4H7V7h10v2z"/>
  </svg>
);

const MoonIcon = () => (
  <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
    <path d="M9 2c-1.05 0-2.05.16-3 .46 4.06 1.27 7 5.06 7 9.54 0 4.48-2.94 8.27-7 9.54.95.3 1.95.46 3 .46 5.52 0 10-4.48 10-10S14.52 2 9 2z"/>
  </svg>
);

const ServerIcon = () => (
  <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
    <path d="M4 1h16c1.1 0 2 .9 2 2v6c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V3c0-1.1.9-2 2-2zm0 12h16c1.1 0 2 .9 2 2v6c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2v-6c0-1.1.9-2 2-2z"/>
  </svg>
);

const DownloadIcon = () => (
  <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
    <path d="M19 9h-4V3H9v6H5l7 7 7-7zM5 18v2h14v-2H5z"/>
  </svg>
);

const EmailIcon = () => (
  <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
    <path d="M20 4H4c-1.1 0-1.99.9-1.99 2L2 18c0 1.1.89 2 2 2h16c1.1 0 2-.9 2-2V6c0-1.1-.9-2-2-2zm0 4l-8 5-8-5V6l8 5 8-5v2z"/>
  </svg>
);

const PhoneIcon = () => (
  <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
    <path d="M6.62 10.79c1.44 2.83 3.76 5.14 6.59 6.59l2.2-2.2c.27-.27.67-.36 1.02-.24 1.12.37 2.33.57 3.57.57.55 0 1 .45 1 1V20c0 .55-.45 1-1 1-9.39 0-17-7.61-17-17 0-.55.45-1 1-1h3.5c.55 0 1 .45 1 1 0 1.25.2 2.45.57 3.57.11.35.03.74-.25 1.02l-2.2 2.2z"/>
  </svg>
);

const LocationIcon = () => (
  <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
    <path d="M12 2C8.13 2 5 5.13 5 9c0 5.25 7 13 7 13s7-7.75 7-13c0-3.87-3.13-7-7-7zm0 9.5c-1.38 0-2.5-1.12-2.5-2.5s1.12-2.5 2.5-2.5 2.5 1.12 2.5 2.5-1.12 2.5-2.5 2.5z"/>
  </svg>
);

const HomeIcon = () => (
  <svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
    <path d="M10 20v-6h4v6h5v-8h3L12 3 2 12h3v8z"/>
  </svg>
);

const StarIcon = () => (
  <svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
    <path d="M12 17.27L18.18 21l-1.64-7.03L22 9.24l-7.19-.61L12 2 9.19 8.63 2 9.24l5.46 4.73L5.82 21z"/>
  </svg>
);

const BarsIcon = () => (
  <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
    <path d="M3 18h18v-2H3v2zm0-5h18v-2H3v2zm0-7v2h18V6H3z"/>
  </svg>
);

const TimesIcon = () => (
  <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
    <path d="M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z"/>
  </svg>
);

const GitHubIcon = () => (
  <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
    <path d="M12 0c-6.626 0-12 5.373-12 12 0 5.302 3.438 9.8 8.207 11.387.599.111.793-.261.793-.577v-2.234c-3.338.726-4.033-1.416-4.033-1.416-.546-1.387-1.333-1.756-1.333-1.756-1.089-.745.083-.729.083-.729 1.205.084 1.839 1.237 1.839 1.237 1.07 1.834 2.807 1.304 3.492.997.107-.775.418-1.305.762-1.604-2.665-.305-5.467-1.334-5.467-5.931 0-1.311.469-2.381 1.236-3.221-.124-.303-.535-1.524.117-3.176 0 0 1.008-.322 3.301 1.23.957-.266 1.983-.399 3.003-.404 1.02.005 2.047.138 3.006.404 2.291-1.552 3.297-1.23 3.297-1.23.653 1.653.242 2.874.118 3.176.77.84 1.235 1.911 1.235 3.221 0 4.609-2.807 5.624-5.479 5.921.43.372.823 1.102.823 2.222v3.293c0 .319.192.694.801.576 4.765-1.589 8.199-6.086 8.199-11.386 0-6.627-5.373-12-12-12z"/>
  </svg>
);

const LinkedInIcon = () => (
  <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
    <path d="M20.447 20.452h-3.554v-5.569c0-1.328-.027-3.037-1.852-3.037-1.853 0-2.136 1.445-2.136 2.939v5.667H9.351V9h3.414v1.561h.046c.477-.9 1.637-1.85 3.37-1.85 3.601 0 4.267 2.37 4.267 5.455v6.286zM5.337 7.433c-1.144 0-2.063-.926-2.063-2.065 0-1.138.92-2.063 2.063-2.063 1.14 0 2.064.925 2.064 2.063 0 1.139-.925 2.065-2.064 2.065zm1.782 13.019H3.555V9h3.564v11.452zM22.225 0H1.771C.792 0 0 .774 0 1.729v20.542C0 23.227.792 24 1.771 24h20.451C23.2 24 24 23.227 24 22.271V1.729C24 .774 23.2 0 22.222 0h.003z"/>
  </svg>
);

function App() {
  const [downloadCount, setDownloadCount] = useState(0);
  const [activeSection, setActiveSection] = useState("home");
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);
  
  // Crear refs individuales para cada sección
  const homeRef = useRef(null);
  const featuresRef = useRef(null);
  const downloadRef = useRef(null);
  const contactRef = useRef(null);

  const navItems = [
    { id: "home", label: "Inicio", icon: HomeIcon },
    { id: "features", label: "Características", icon: StarIcon },
    { id: "download", label: "Descarga", icon: DownloadIcon },
    { id: "contact", label: "Contacto", icon: EmailIcon },
  ];

  useEffect(() => {
    // Animación del contador de descargas
    const timer = setInterval(() => {
      setDownloadCount((prev) => {
        if (prev < 50000) {
          return prev + 1000;
        }
        clearInterval(timer);
        return 50000;
      });
    }, 50);

    // Intersection Observer para animaciones de scroll y navegación activa
    const observer = new IntersectionObserver(
      (entries) => {
        entries.forEach((entry) => {
          if (entry.isIntersecting) {
            entry.target.classList.add("animate-in");
            setActiveSection(entry.target.id);
          }
        });
      },
      { threshold: 0.3, rootMargin: "0px 0px -200px 0px" },
    );

    // Observar todas las secciones
    const sections = [homeRef.current, featuresRef.current, downloadRef.current, contactRef.current];
    sections.forEach((section) => {
      if (section) observer.observe(section);
    });

    return () => {
      clearInterval(timer);
      observer.disconnect();
    };
  }, []);

  const scrollToSection = (sectionId) => {
    const element = document.getElementById(sectionId);
    if (element) {
      element.scrollIntoView({ behavior: "smooth" });
      setMobileMenuOpen(false);
    }
  };

  const features = [
    {
      icon: <PiggyBankIcon />,
      title: "Hucha Virtual",
      desc: "Visualiza el crecimiento de tu ahorro de forma lúdica y motivadora.",
    },
    {
      icon: <ReceiptIcon />,
      title: "Registro de Transacciones",
      desc: "Añade ingresos y gastos con fecha, descripción y categoría.",
    },
    {
      icon: <TagsIcon />,
      title: "Categorización",
      desc: "Organiza tus movimientos con categorías predefinidas.",
    },
    {
      icon: <HistoryIcon />,
      title: "Historial",
      desc: "Revisa tu lista completa de transacciones con filtros rápidos.",
    },
    {
      icon: <ChartIcon />,
      title: "Estadísticas Interactivas",
      desc: "Analiza ingresos y gastos con gráficos detallados.",
    },
    {
      icon: <CalculatorIcon />,
      title: "Calculadora de Interés",
      desc: "Simula escenarios de ahorro con tasas personalizables.",
    },
    {
      icon: <MoonIcon />,
      title: "Modo Claro/Oscuro",
      desc: "Cambia temas al instante para adaptarte a tu entorno.",
    },
    {
      icon: <ServerIcon />,
      title: "Offline Seguro",
      desc: "Todos tus datos se almacenan localmente sin conexión.",
    },
  ];

  const contactInfo = [
    {
      icon: EmailIcon,
      label: "Email",
      value: "miguel.reyesgomez1@gmail.com",
      href: "mailto:miguel.reyesgomez1@gmail.com",
    },
    {
      icon: PhoneIcon,
      label: "Teléfono",
      value: "+34 611 450 893",
      href: "tel:+34611450893",
    },
    {
      icon: LocationIcon,
      label: "Ubicación",
      value: "España",
      href: "#",
    },
  ];

  const socialLinks = [
    {
      icon: GitHubIcon,
      label: "GitHub",
      href: "https://github.com/miguelreyess1",
      className: "github-link",
    },
    {
      icon: LinkedInIcon,
      label: "LinkedIn",
      href: "https://www.linkedin.com/in/miguel-reyes-gómez-66a170293",
      className: "linkedin-link",
    },
    {
      icon: EmailIcon,
      label: "Email",
      href: "mailto:miguel.reyesgomez1@gmail.com",
      className: "email-link",
    },
  ];

  return (
    <div className="app">
      {/* Navigation Bar */}
      <nav className="navbar">
        <div className="nav-container">
          <div className="nav-content">
            {/* Logo */}
            <div className="logo">
              <span className="logo-text">
                My<span className="logo-highlight">Finance</span>
              </span>
            </div>

            {/* Desktop Navigation */}
            <div className="nav-desktop">
              {navItems.map((item) => (
                <button
                  key={item.id}
                  onClick={() => scrollToSection(item.id)}
                  className={`nav-item ${activeSection === item.id ? 'nav-item-active' : ''}`}
                >
                  <item.icon />
                  <span>{item.label}</span>
                </button>
              ))}
            </div>

            {/* Mobile Menu Button */}
            <div className="nav-mobile-toggle">
              <button onClick={() => setMobileMenuOpen(!mobileMenuOpen)} className="mobile-menu-btn">
                {mobileMenuOpen ? <TimesIcon /> : <BarsIcon />}
              </button>
            </div>
          </div>

          {/* Mobile Navigation */}
          {mobileMenuOpen && (
            <div className="nav-mobile">
              {navItems.map((item) => (
                <button
                  key={item.id}
                  onClick={() => scrollToSection(item.id)}
                  className={`nav-mobile-item ${activeSection === item.id ? 'nav-mobile-item-active' : ''}`}
                >
                  <item.icon />
                  <span>{item.label}</span>
                </button>
              ))}
            </div>
          )}
        </div>
      </nav>

      {/* Hero Section */}
      <section id="home" ref={homeRef} className="hero-section scroll-reveal">
        <div className="hero-content">
          <h1 className="hero-title">
            My<span className="title-highlight">Finance</span>
          </h1>
          <h2 className="hero-subtitle">Tus finanzas sin límites</h2>
          <p className="hero-description">
            Controla tus gastos, ahorra más y alcanza tus metas financieras con la app más completa y fácil de usar
            del mercado.
          </p>
          <div className="hero-buttons">
            <button onClick={() => scrollToSection("download")} className="btn-primary">
              Descargar Ahora
            </button>
            <button onClick={() => scrollToSection("features")} className="btn-secondary">
              Ver Características
            </button>
          </div>
        </div>
      </section>

      <div className="section-separator"></div>

      {/* Features Section */}
      <section id="features" ref={featuresRef} className="features-section scroll-reveal">
        <div className="section-container">
          <div className="section-header">
            <div className="section-badge">
              <span>✨ Características</span>
            </div>
            <h2 className="section-title">Características Destacadas</h2>
            <p className="section-description">
              Descubre todo lo que MyFinance ofrece para gestionar tus finanzas sin complicaciones.
            </p>
          </div>

          <div className="features-grid">
            {features.map((feature, idx) => (
              <div key={idx} className="feature-card" style={{ animationDelay: `${idx * 0.1}s` }}>
                <div className="feature-icon">{feature.icon}</div>
                <h3 className="feature-title">{feature.title}</h3>
                <p className="feature-description">{feature.desc}</p>
              </div>
            ))}
          </div>
        </div>
      </section>

      <div className="section-separator"></div>

      {/* Download Section */}
      <section id="download" ref={downloadRef} className="download-section scroll-reveal">
        <div className="section-container">
          <div className="download-badge">
            <DownloadIcon />
            <span>Disponible ahora</span>
          </div>

          <h2 className="section-title">
            Descarga <span className="title-highlight">MyFinance</span>
          </h2>

          <p className="section-description">
            La app que revolucionará tus finanzas personales. Comienza tu viaje hacia la libertad financiera hoy mismo.
          </p>

          {/* Estadísticas */}
          <div className="stats-grid">
            <div className="stat-card">
              <div className="stat-number">{downloadCount.toLocaleString()}+</div>
              <div className="stat-label">Descargas</div>
            </div>
            <div className="stat-card">
              <div className="stat-number">4.8</div>
              <div className="stat-label">Rating</div>
            </div>
            <div className="stat-card">
              <div className="stat-number">€2M+</div>
              <div className="stat-label">Ahorrado</div>
            </div>
          </div>

          {/* Botón de descarga */}
          <a href="/myFinance.zip" download="myFinance.zip" className="download-btn">
            <DownloadIcon />
            <span>Descargar MyFinance</span>
          </a>

          <p className="download-note">Gratis • Sin registro • Disponible offline</p>
        </div>
      </section>

      <div className="section-separator"></div>

      {/* Contact Section */}
      <section id="contact" ref={contactRef} className="contact-section scroll-reveal">
        <div className="section-container">
          <div className="section-header">
            <div className="section-badge">
              <span>📞 Contacto</span>
            </div>
            <h2 className="section-title">¡Conectemos!</h2>
            <p className="section-description">
              ¿Tienes una idea increíble? ¿Necesitas ayuda? ¡Me encantaría colaborar contigo!
            </p>
          </div>

          <div className="contact-grid">
            {/* Información de contacto */}
            <div className="contact-card">
              <h3 className="contact-card-title">Información de contacto</h3>
              <div className="contact-info">
                {contactInfo.map((item, index) => (
                  <a key={index} href={item.href} className="contact-item">
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

            {/* Redes sociales */}
            <div className="contact-card">
              <h3 className="contact-card-title">Sígueme en redes</h3>
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
              <div className="contact-cta">
                <p className="contact-cta-text">¿Tienes un proyecto en mente?</p>
                <a href="mailto:miguel.reyesgomez1@gmail.com" className="contact-cta-btn">
                  <EmailIcon />
                  Envíame un email
                </a>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Footer */}
      <footer className="footer">
        <div className="footer-content">
          <p>© 2024 MyFinance. Desarrollado con ❤️ por Miguel Reyes</p>
        </div>
      </footer>
    </div>
  );
}

export default App;