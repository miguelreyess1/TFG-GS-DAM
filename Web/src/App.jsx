import { useEffect, useRef, useState } from "react";
import "./App.css";

// Iconos existentes
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

// Nuevos iconos para las secciones adicionales
const LightbulbIcon = () => (
  <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
    <path d="M9 21c0 .5.4 1 1 1h4c.6 0 1-.5 1-1v-1H9v1zm3-19C8.1 2 5 5.1 5 9c0 2.4 1.2 4.5 3 5.7V17c0 .5.4 1 1 1h6c.6 0 1-.5 1-1v-2.3c1.8-1.3 3-3.4 3-5.7 0-3.9-3.1-7-7-7z" />
  </svg>
);

const SearchIcon = () => (
  <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
    <path d="M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z" />
  </svg>
);

const TargetIcon = () => (
  <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
    <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 18c-4.42 0-8-3.58-8-8s3.58-8 8-8 8 3.58 8 8-3.58 8-8 8zm0-14c-3.31 0-6 2.69-6 6s2.69 6 6 6 6-2.69 6-6-2.69-6-6-6zm0 10c-2.21 0-4-1.79-4-4s1.79-4 4-4 4 1.79 4 4-1.79 4-4 4z" />
  </svg>
);

const FileTextIcon = () => (
  <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
    <path d="M14 2H6c-1.1 0-1.99.9-1.99 2L4 20c0 1.1.89 2 1.99 2H18c1.1 0 2-.9 2-2V8l-6-6zm2 16H8v-2h8v2zm0-4H8v-2h8v2zm-3-5V3.5L18.5 9H13z" />
  </svg>
);

const DatabaseIcon = () => (
  <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
    <path d="M12 2C6.48 2 2 4.02 2 6.5v11C2 19.98 6.48 22 12 22s10-2.02 10-4.5v-11C22 4.02 17.52 2 12 2zm0 2c4.42 0 8 1.34 8 3s-3.58 3-8 3-8-1.34-8-3 3.58-3 8-3zm0 16c-4.42 0-8-1.34-8-3v-2.66c1.68 1.14 4.7 1.66 8 1.66s6.32-.52 8-1.66V17c0 1.66-3.58 3-8 3zm0-5c-4.42 0-8-1.34-8-3v-2.66c1.68 1.14 4.7 1.66 8 1.66s6.32-.52 8-1.66V12c0 1.66-3.58 3-8 3zm0-5c-4.42 0-8-1.34-8-3V7.34C5.68 8.48 8.7 9 12 9s6.32-.52 8-1.66V7c0 1.66-3.58 3-8 3z" />
  </svg>
);

const LayersIcon = () => (
  <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
    <path d="M11.99 18.54l-7.37-5.73L3 14.07l9 7 9-7-1.63-1.27-7.38 5.74zM12 16l7.36-5.73L21 9l-9-7-9 7 1.63 1.27L12 16z" />
  </svg>
);

const GlobeIcon = () => (
  <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
    <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm-1 17.93c-3.94-.49-7-3.85-7-7.93 0-.62.08-1.21.21-1.79L9 15v1c0 1.1.9 2 2 2v1.93zm6.9-2.54c-.26-.81-1-1.39-1.9-1.39h-1v-3c0-.55-.45-1-1-1H8v-2h2c.55 0 1-.45 1-1V7h2c1.1 0 2-.9 2-2v-.41c2.93 1.19 5 4.06 5 7.41 0 2.08-.8 3.97-2.1 5.39z" />
  </svg>
);

const CheckCircleIcon = () => (
  <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
    <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm-2 15l-5-5 1.41-1.41L10 14.17l7.59-7.59L19 8l-9 9z" />
  </svg>
);

const AlertTriangleIcon = () => (
  <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
    <path d="M1 21h22L12 2 1 21zm12-3h-2v-2h2v2zm0-4h-2v-4h2v4z" />
  </svg>
);

const ShieldCheckIcon = () => (
  <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
    <path d="M12 1L3 5v6c0 5.55 3.84 10.74 9 12 5.16-1.26 9-6.45 9-12V5l-9-4zm-2 16l-4-4 1.41-1.41L10 14.17l6.59-6.59L18 9l-8 8z" />
  </svg>
);

const BookOpenIcon = () => (
  <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
    <path d="M21 5c-1.11-.35-2.33-.5-3.5-.5-1.95 0-4.05.4-5.5 1.5-1.45-1.1-3.55-1.5-5.5-1.5S2.45 4.9 1 6v14.65c0 .25.25.5.5.5.1 0 .15-.05.25-.05C3.1 20.45 5.05 20 6.5 20c1.95 0 4.05.4 5.5 1.5 1.35-.85 3.8-1.5 5.5-1.5 1.65 0 3.35.3 4.75 1.05.1.05.15.05.25.05.25 0 .5-.25.5-.5V6c-.6-.45-1.25-.75-2-1zm0 13.5c-1.1-.35-2.3-.5-3.5-.5-1.7 0-4.15.65-5.5 1.5V8c1.35-.85 3.8-1.5 5.5-1.5 1.2 0 2.4.15 3.5.5v11.5z" />
  </svg>
);

const ThumbsUpIcon = () => (
  <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
    <path d="M1 21h4V9H1v12zm22-11c0-1.1-.9-2-2-2h-6.31l.95-4.57.03-.32c0-.41-.17-.79-.44-1.06L14.17 1 7.59 7.59C7.22 7.95 7 8.45 7 9v10c0 1.1.9 2 2 2h9c.83 0 1.54-.5 1.84-1.22l3.02-7.05c.09-.23.14-.47.14-.73v-1.91l-.01-.01L23 10z" />
  </svg>
);

const ArrowUpRightIcon = () => (
  <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
    <path d="M9 5v2h6.59L4 18.59 5.41 20 17 8.41V15h2V5z" />
  </svg>
);

const HeartIcon = () => (
  <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
    <path d="M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z" />
  </svg>
);

// Hook personalizado para detectar el ancho de la ventana
function useViewportWidth() {
  const [width, setWidth] = useState(window.innerWidth);
  
  useEffect(() => {
    const handleResize = () => setWidth(window.innerWidth);
    window.addEventListener('resize', handleResize);
    return () => window.removeEventListener('resize', handleResize);
  }, []);
  
  return width;
}

// Traducciones simples
const translations = {
  es: {
    nav: {
      home: "Inicio",
      motivation: "Motivación",
      analysis: "Análisis",
      development: "Desarrollo",
      testing: "Pruebas",
      results: "Resultados",
      conclusion: "Conclusión",
      features: "Características",
      download: "Descarga",
      contact: "Contacto"
    },
    hero: {
      subtitle: "Tus finanzas sin límites",
      description: "Controla tus gastos, ahorra más y alcanza tus metas financieras con la app más completa y fácil de usar del mercado.",
      downloadButton: "Descargar Ahora",
      featuresButton: "Ver Características"
    },
    motivation: {
      badge: "Motivación y Contexto",
      title: "¿Por qué MyFinance?",
      description: "Descubre las razones que me llevaron a desarrollar esta aplicación y el problema que resuelve.",
      why: {
        title: "¿Por qué elegí este proyecto?",
        description: "La gestión financiera personal es un desafío constante. Quería crear una herramienta que fuera intuitiva, completa y que realmente ayudara a las personas a tomar control de sus finanzas de manera sencilla y efectiva."
      },
      need: {
        title: "¿Qué necesidad detecté?",
        description: "Muchas aplicaciones financieras son complejas, requieren conexión constante a internet o no ofrecen una experiencia visual atractiva. Detecté la necesidad de una app offline, segura y con una interfaz moderna."
      },
      solution: {
        title: "¿Qué aporta mi solución?",
        description: "MyFinance combina simplicidad, seguridad y funcionalidad. Ofrece gestión offline completa, visualizaciones atractivas y herramientas de análisis que motivan el ahorro y la planificación financiera."
      }
    },
    analysis: {
      badge: "Análisis y Diseño",
      title: "Análisis y Diseño del Sistema",
      description: "Exploración de los requisitos, arquitectura y diseño de la aplicación.",
      requirements: {
        title: "Requisitos",
        functional: "Requisitos funcionales: registro de transacciones, categorización, estadísticas, calculadora de interés, etc.",
        nonFunctional: "Requisitos no funcionales: rendimiento, seguridad, usabilidad, funcionamiento offline."
      },
      database: {
        title: "Diseño de Base de Datos",
        description: "Estructura de almacenamiento local con IndexedDB para garantizar persistencia sin conexión y sincronización cuando sea posible."
      },
      architecture: {
        title: "Arquitectura",
        description: "Patrón MVVM (Model-View-ViewModel) para separar la lógica de negocio de la interfaz de usuario y facilitar el testing."
      },
      useCaseDiagram: "Diagrama de Casos de Uso"
    },
    development: {
      badge: "Desarrollo",
      title: "Desarrollo y Funcionamiento",
      description: "Implementación y flujo de uso de la aplicación.",
      flow: {
        title: "Flujo de Uso",
        step1: "Registro/Login del usuario",
        step2: "Configuración inicial de categorías y metas",
        step3: "Registro de transacciones diarias",
        step4: "Visualización de estadísticas y progreso"
      },
      logic: {
        title: "Lógica Destacada",
        description: "Características técnicas principales implementadas:",
        point1: "Sistema de puntos y recompensas para motivar el ahorro",
        point2: "Algoritmos de predicción de gastos basados en históricos",
        point3: "Validaciones avanzadas para evitar errores de entrada"
      },
      screens: {
        login: "Login",
        dashboard: "Dashboard",
        transactions: "Transacciones",
        stats: "Estadísticas"
      }
    },
    testing: {
      badge: "Pruebas",
      title: "Pruebas y Validaciones",
      description: "Metodología de testing y resultados obtenidos.",
      types: {
        title: "Tipos de Pruebas",
        functional: "Pruebas funcionales para verificar el correcto funcionamiento de cada característica.",
        usability: "Pruebas de usabilidad con usuarios reales para mejorar la experiencia.",
        performance: "Pruebas de rendimiento para garantizar velocidad incluso con grandes volúmenes de datos."
      },
      errors: {
        title: "Errores Detectados",
        error1: "Problemas de sincronización en dispositivos con poca memoria.",
        error2: "Inconsistencias en cálculos con decimales en diferentes divisas.",
        error3: "Fallos de renderizado en gráficos con datasets muy grandes."
      },
      validation: {
        title: "Validaciones",
        data: "Validación de integridad de datos y persistencia local.",
        error: "Manejo de errores y recuperación ante fallos.",
        security: "Seguridad de datos sensibles del usuario."
      }
    },
    results: {
      badge: "Resultados",
      title: "Resultados, Aprendizajes y Dificultades",
      description: "Logros alcanzados y lecciones aprendidas durante el desarrollo.",
      features: {
        title: "Funcionalidades Implementadas",
        feature1: "Sistema completo de gestión de transacciones con categorización.",
        feature2: "Dashboard interactivo con visualización de tendencias.",
        feature3: "Calculadora de interés compuesto y proyecciones.",
        feature4: "Sistema de metas financieras con seguimiento visual."
      },
      challenges: {
        title: "Dificultades",
        challenge1: "Optimización del rendimiento con grandes volúmenes de datos.",
        challenge2: "Implementación del modo offline con sincronización posterior.",
        challenge3: "Diseño de una interfaz intuitiva para todas las edades."
      },
      learning: {
        title: "Aprendizajes",
        learning1: "Profundización en patrones de diseño para aplicaciones financieras.",
        learning2: "Mejora en técnicas de optimización de rendimiento en dispositivos móviles.",
        learning3: "Desarrollo de habilidades en UX/UI centrado en el usuario."
      }
    },
    conclusion: {
      badge: "Conclusión",
      title: "Conclusión y Cierre",
      description: "Reflexiones finales sobre el proyecto y su futuro.",
      choice: {
        title: "¿Volvería a elegir este proyecto?",
        description: "Definitivamente sí. Este proyecto me ha permitido combinar mi interés por las finanzas personales con el desarrollo de software, creando una herramienta que realmente puede ayudar a las personas."
      },
      future: {
        title: "Mejoras Futuras",
        description: "Planeo implementar sincronización en la nube, más visualizaciones personalizables y un sistema de consejos financieros basado en los patrones de gasto del usuario."
      },
      thanks: {
        title: "Agradecimientos",
        description: "Agradezco a mi tutor por su guía constante, a la universidad por proporcionar los recursos necesarios y a los usuarios beta que probaron la aplicación y aportaron valiosa retroalimentación."
      }
    },
    features: {
      title: "Características Destacadas",
      description: "Descubre todo lo que MyFinance ofrece para gestionar tus finanzas sin complicaciones.",
      items: [
        {
          title: "Hucha Virtual",
          desc: "Visualiza el crecimiento de tu ahorro de forma lúdica y motivadora."
        },
        {
          title: "Registro de Transacciones",
          desc: "Añade ingresos y gastos con fecha, descripción y categoría."
        },
        {
          title: "Categorización",
          desc: "Organiza tus movimientos con categorías predefinidas."
        },
        {
          title: "Historial",
          desc: "Revisa tu lista completa de transacciones con filtros rápidos."
        },
        {
          title: "Estadísticas Interactivas",
          desc: "Analiza ingresos y gastos con gráficos detallados."
        },
        {
          title: "Calculadora de Interés",
          desc: "Simula escenarios de ahorro con tasas personalizables."
        },
        {
          title: "Modo Claro/Oscuro",
          desc: "Cambia temas al instante para adaptarte a tu entorno."
        },
        {
          title: "Offline Seguro",
          desc: "Todos tus datos se almacenan localmente sin conexión."
        }
      ]
    },
    download: {
      badge: "Disponible ahora",
      title: "Descarga",
      description: "La app que revolucionará tus finanzas personales. Comienza tu viaje hacia la libertad financiera hoy mismo.",
      stats: {
        downloads: "Descargas",
        rating: "Rating",
        saved: "Ahorrado"
      },
      button: "Descargar MyFinance",
      note: "Gratis • Sin registro • Disponible offline"
    },
    contact: {
      title: "¡Conectemos!",
      description: "¿Tienes una idea increíble? ¿Necesitas ayuda? ¡Me encantaría colaborar contigo!",
      info: {
        title: "Información de contacto",
        email: {
          label: "Email"
        },
        phone: {
          label: "Teléfono"
        },
        location: {
          label: "Ubicación",
          value: "España"
        }
      },
      social: {
        title: "Sígueme en redes"
      },
      cta: {
        text: "¿Tienes un proyecto en mente?",
        button: "Envíame un email"
      }
    },
    footer: {
      developedBy: "Desarrollado por"
    },
    languageSwitcher: "EN",
    presentationMode: "Modo Presentación",
    exitPresentation: "Salir de Presentación"
  },
  en: {
    nav: {
      home: "Home",
      motivation: "Motivation",
      analysis: "Analysis",
      development: "Development",
      testing: "Testing",
      results: "Results",
      conclusion: "Conclusion",
      features: "Features",
      download: "Download",
      contact: "Contact"
    },
    hero: {
      subtitle: "Your finances without limits",
      description: "Control your expenses, save more and reach your financial goals with the most complete and easy-to-use app on the market.",
      downloadButton: "Download Now",
      featuresButton: "View Features"
    },
    motivation: {
      badge: "Motivation and Context",
      title: "Why MyFinance?",
      description: "Discover the reasons that led me to develop this application and the problem it solves.",
      why: {
        title: "Why did I choose this project?",
        description: "Personal financial management is a constant challenge. I wanted to create a tool that was intuitive, comprehensive and that really helped people take control of their finances in a simple and effective way."
      },
      need: {
        title: "What need did I detect?",
        description: "Many financial apps are complex, require constant internet connection or don't offer an attractive visual experience. I detected the need for an offline, secure app with a modern interface."
      },
      solution: {
        title: "What does my solution provide?",
        description: "MyFinance combines simplicity, security and functionality. It offers complete offline management, attractive visualizations and analysis tools that motivate saving and financial planning."
      }
    },
    analysis: {
      badge: "Analysis and Design",
      title: "System Analysis and Design",
      description: "Exploration of the requirements, architecture and design of the application.",
      requirements: {
        title: "Requirements",
        functional: "Functional requirements: transaction recording, categorization, statistics, interest calculator, etc.",
        nonFunctional: "Non-functional requirements: performance, security, usability, offline operation."
      },
      database: {
        title: "Database Design",
        description: "Local storage structure with IndexedDB to ensure offline persistence and synchronization when possible."
      },
      architecture: {
        title: "Architecture",
        description: "MVVM (Model-View-ViewModel) pattern to separate business logic from the user interface and facilitate testing."
      },
      useCaseDiagram: "Use Case Diagram"
    },
    development: {
      badge: "Development",
      title: "Development and Operation",
      description: "Implementation and usage flow of the application.",
      flow: {
        title: "Usage Flow",
        step1: "User Registration/Login",
        step2: "Initial setup of categories and goals",
        step3: "Daily transaction recording",
        step4: "Visualization of statistics and progress"
      },
      logic: {
        title: "Featured Logic",
        description: "Main technical features implemented:",
        point1: "Points and rewards system to motivate saving",
        point2: "Expense prediction algorithms based on historical data",
        point3: "Advanced validations to prevent input errors"
      },
      screens: {
        login: "Login",
        dashboard: "Dashboard",
        transactions: "Transactions",
        stats: "Statistics"
      }
    },
    testing: {
      badge: "Testing",
      title: "Tests and Validations",
      description: "Testing methodology and results obtained.",
      types: {
        title: "Types of Tests",
        functional: "Functional tests to verify the correct operation of each feature.",
        usability: "Usability tests with real users to improve the experience.",
        performance: "Performance tests to ensure speed even with large volumes of data."
      },
      errors: {
        title: "Detected Errors",
        error1: "Synchronization problems on devices with low memory.",
        error2: "Inconsistencies in calculations with decimals in different currencies.",
        error3: "Rendering failures in charts with very large datasets."
      },
      validation: {
        title: "Validations",
        data: "Validation of data integrity and local persistence.",
        error: "Error handling and recovery from failures.",
        security: "Security of sensitive user data."
      }
    },
    results: {
      badge: "Results",
      title: "Results, Learnings and Difficulties",
      description: "Achievements and lessons learned during development.",
      features: {
        title: "Implemented Features",
        feature1: "Complete transaction management system with categorization.",
        feature2: "Interactive dashboard with trend visualization.",
        feature3: "Compound interest calculator and projections.",
        feature4: "Financial goals system with visual tracking."
      },
      challenges: {
        title: "Difficulties",
        challenge1: "Performance optimization with large volumes of data.",
        challenge2: "Implementation of offline mode with subsequent synchronization.",
        challenge3: "Design of an intuitive interface for all ages."
      },
      learning: {
        title: "Learnings",
        learning1: "Deepening in design patterns for financial applications.",
        learning2: "Improvement in performance optimization techniques on mobile devices.",
        learning3: "Development of UX/UI skills centered on the user."
      }
    },
    conclusion: {
      badge: "Conclusion",
      title: "Conclusion and Closure",
      description: "Final reflections on the project and its future.",
      choice: {
        title: "Would I choose this project again?",
        description: "Definitely yes. This project has allowed me to combine my interest in personal finance with software development, creating a tool that can really help people."
      },
      future: {
        title: "Future Improvements",
        description: "I plan to implement cloud synchronization, more customizable visualizations and a financial advice system based on the user's spending patterns."
      },
      thanks: {
        title: "Acknowledgments",
        description: "I thank my tutor for his constant guidance, the university for providing the necessary resources and the beta users who tested the application and provided valuable feedback."
      }
    },
    features: {
      title: "Featured Characteristics",
      description: "Discover everything MyFinance offers to manage your finances without complications.",
      items: [
        {
          title: "Virtual Piggy Bank",
          desc: "Visualize the growth of your savings in a playful and motivating way."
        },
        {
          title: "Transaction Recording",
          desc: "Add income and expenses with date, description and category."
        },
        {
          title: "Categorization",
          desc: "Organize your movements with predefined categories."
        },
        {
          title: "History",
          desc: "Review your complete list of transactions with quick filters."
        },
        {
          title: "Interactive Statistics",
          desc: "Analyze income and expenses with detailed charts."
        },
        {
          title: "Interest Calculator",
          desc: "Simulate saving scenarios with customizable rates."
        },
        {
          title: "Light/Dark Mode",
          desc: "Change themes instantly to adapt to your environment."
        },
        {
          title: "Secure Offline",
          desc: "All your data is stored locally without connection."
        }
      ]
    },
    download: {
      badge: "Available now",
      title: "Download",
      description: "The app that will revolutionize your personal finances. Start your journey to financial freedom today.",
      stats: {
        downloads: "Downloads",
        rating: "Rating",
        saved: "Saved"
      },
      button: "Download MyFinance",
      note: "Free • No registration • Available offline"
    },
    contact: {
      title: "Let's Connect!",
      description: "Do you have an amazing idea? Need help? I'd love to collaborate with you!",
      info: {
        title: "Contact information",
        email: {
          label: "Email"
        },
        phone: {
          label: "Phone"
        },
        location: {
          label: "Location",
          value: "Spain"
        }
      },
      social: {
        title: "Follow me on social media"
      },
      cta: {
        text: "Do you have a project in mind?",
        button: "Send me an email"
      }
    },
    footer: {
      developedBy: "Developed by"
    },
    languageSwitcher: "ES",
    presentationMode: "Presentation Mode",
    exitPresentation: "Exit Presentation"
  }
};

function App() {
  const [downloadCount, setDownloadCount] = useState(0);
  const [activeSection, setActiveSection] = useState("home");
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);
  const [language, setLanguage] = useState("es"); // es o en
  const [presentationMode, setPresentationMode] = useState(false);
  const [currentSlide, setCurrentSlide] = useState(0);
  
  const viewportWidth = useViewportWidth();
  const isMobile = viewportWidth < 768;
  
  // Crear refs individuales para cada sección
  const homeRef = useRef(null);
  const motivationRef = useRef(null);
  const analysisRef = useRef(null);
  const developmentRef = useRef(null);
  const testingRef = useRef(null);
  const resultsRef = useRef(null);
  const conclusionRef = useRef(null);
  const featuresRef = useRef(null);
  const downloadRef = useRef(null);
  const contactRef = useRef(null);

  // Obtener traducciones según el idioma seleccionado
  const t = translations[language];

  const navItems = [
    { id: "home", label: t.nav.home, icon: HomeIcon },
    { id: "motivation", label: t.nav.motivation, icon: StarIcon },
    { id: "analysis", label: t.nav.analysis, icon: StarIcon },
    { id: "development", label: t.nav.development, icon: StarIcon },
    { id: "testing", label: t.nav.testing, icon: StarIcon },
    { id: "results", label: t.nav.results, icon: StarIcon },
    { id: "conclusion", label: t.nav.conclusion, icon: StarIcon },
    { id: "features", label: t.nav.features, icon: StarIcon },
    { id: "download", label: t.nav.download, icon: DownloadIcon },
    { id: "contact", label: t.nav.contact, icon: EmailIcon },
  ];

  // Definir las secciones para el modo presentación
  const sections = [
    { id: "home", ref: homeRef },
    { id: "motivation", ref: motivationRef },
    { id: "analysis", ref: analysisRef },
    { id: "development", ref: developmentRef },
    { id: "testing", ref: testingRef },
    { id: "results", ref: resultsRef },
    { id: "conclusion", ref: conclusionRef },
    { id: "features", ref: featuresRef },
    { id: "download", ref: downloadRef },
    { id: "contact", ref: contactRef },
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

    // Intersection Observer para detectar secciones visibles
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
    const sectionRefs = [
      homeRef.current, 
      motivationRef.current,
      analysisRef.current,
      developmentRef.current,
      testingRef.current,
      resultsRef.current,
      conclusionRef.current,
      featuresRef.current, 
      downloadRef.current, 
      contactRef.current
    ];
    
    sectionRefs.forEach((section) => {
      if (section) observer.observe(section);
    });

    return () => {
      clearInterval(timer);
      observer.disconnect();
    };
  }, []);

  // Efecto para cerrar el menú móvil al hacer clic fuera
  useEffect(() => {
    if (mobileMenuOpen) {
      const handleClickOutside = (e) => {
        if (!e.target.closest('.navbar')) {
          setMobileMenuOpen(false);
        }
      };
      
      document.addEventListener('click', handleClickOutside);
      return () => document.removeEventListener('click', handleClickOutside);
    }
  }, [mobileMenuOpen]);

  // Función para cambiar de idioma
  const toggleLanguage = () => {
    setLanguage(prev => prev === "es" ? "en" : "es");
  };

  // Función para activar/desactivar modo presentación
  const togglePresentationMode = () => {
    setPresentationMode(prev => !prev);
    setCurrentSlide(0);
  };

  // Funciones para navegación en modo presentación
  const handleNextSlide = () => {
    if (currentSlide < sections.length - 1) {
      setCurrentSlide(prev => prev + 1);
    }
  };

  const handlePrevSlide = () => {
    if (currentSlide > 0) {
      setCurrentSlide(prev => prev - 1);
    }
  };

  // Función mejorada para scroll con mejor experiencia en móvil
  const scrollToSection = (sectionId) => {
    const element = document.getElementById(sectionId);
    if (element) {
      // Añade un pequeño retraso en móviles para mejor experiencia
      if (isMobile) {
        setTimeout(() => {
          element.scrollIntoView({ behavior: "smooth" });
          setMobileMenuOpen(false);
        }, 100);
      } else {
        element.scrollIntoView({ behavior: "smooth" });
        setMobileMenuOpen(false);
      }
    }
  };

  const features = [
    {
      icon: <PiggyBankIcon />,
      title: t.features.items[0].title,
      desc: t.features.items[0].desc,
    },
    {
      icon: <ReceiptIcon />,
      title: t.features.items[1].title,
      desc: t.features.items[1].desc,
    },
    {
      icon: <TagsIcon />,
      title: t.features.items[2].title,
      desc: t.features.items[2].desc,
    },
    {
      icon: <HistoryIcon />,
      title: t.features.items[3].title,
      desc: t.features.items[3].desc,
    },
    {
      icon: <ChartIcon />,
      title: t.features.items[4].title,
      desc: t.features.items[4].desc,
    },
    {
      icon: <CalculatorIcon />,
      title: t.features.items[5].title,
      desc: t.features.items[5].desc,
    },
    {
      icon: <MoonIcon />,
      title: t.features.items[6].title,
      desc: t.features.items[6].desc,
    },
    {
      icon: <ServerIcon />,
      title: t.features.items[7].title,
      desc: t.features.items[7].desc,
    },
  ];

  const contactInfo = [
    {
      icon: EmailIcon,
      label: t.contact.info.email.label,
      value: "miguel.reyesgomez1@gmail.com",
      href: "mailto:miguel.reyesgomez1@gmail.com",
    },
    {
      icon: PhoneIcon,
      label: t.contact.info.phone.label,
      value: "+34 611 450 893",
      href: "tel:+34611450893",
    },
    {
      icon: LocationIcon,
      label: t.contact.info.location.label,
      value: t.contact.info.location.value,
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

  // Función para determinar la clase CSS según el dispositivo
  const getSectionClass = (baseClass) => {
    return isMobile ? `${baseClass} mobile-visible` : `${baseClass} scroll-reveal`;
  };

  // Renderizado condicional para modo presentación
  if (presentationMode) {
    const currentSection = sections[currentSlide];
    
    return (
      <div className="app presentation-mode">
        {/* Controles de idioma y presentación */}
        <div className="fixed top-4 right-4 z-50 flex gap-2">
          <button
            onClick={toggleLanguage}
            className="language-switcher"
          >
            <GlobeIcon />
            <span>{t.languageSwitcher}</span>
          </button>
          <button
            onClick={togglePresentationMode}
            className="presentation-mode-btn"
          >
            {t.exitPresentation}
          </button>
        </div>
        
        {/* Contenido de la diapositiva actual */}
        <div className="presentation-slide">
          {currentSection.id === "home" && (
            <div className="hero-content">
              <h1 className="hero-title">
                My<span className="title-highlight">Finance</span>
              </h1>
              <h2 className="hero-subtitle">{t.hero.subtitle}</h2>
              <p className="hero-description">{t.hero.description}</p>
            </div>
          )}
          
          {currentSection.id === "motivation" && (
            <div className="motivation-content">
              <div className="section-header">
                <div className="section-badge">{t.motivation.badge}</div>
                <h2 className="section-title">{t.motivation.title}</h2>
                <p className="section-description">{t.motivation.description}</p>
              </div>
              
              <div className="motivation-grid">
                <div className="motivation-card">
                  <div className="motivation-icon"><LightbulbIcon /></div>
                  <h3 className="motivation-card-title">{t.motivation.why.title}</h3>
                  <p className="motivation-card-desc">{t.motivation.why.description}</p>
                </div>
                
                <div className="motivation-card">
                  <div className="motivation-icon"><SearchIcon /></div>
                  <h3 className="motivation-card-title">{t.motivation.need.title}</h3>
                  <p className="motivation-card-desc">{t.motivation.need.description}</p>
                </div>
                
                <div className="motivation-card">
                  <div className="motivation-icon"><TargetIcon /></div>
                  <h3 className="motivation-card-title">{t.motivation.solution.title}</h3>
                  <p className="motivation-card-desc">{t.motivation.solution.description}</p>
                </div>
              </div>
            </div>
          )}
          
          {currentSection.id === "analysis" && (
            <div className="analysis-content">
              <div className="section-header">
                <div className="section-badge">{t.analysis.badge}</div>
                <h2 className="section-title">{t.analysis.title}</h2>
                <p className="section-description">{t.analysis.description}</p>
              </div>
              
              <div className="analysis-grid">
                <div className="analysis-card">
                  <div className="analysis-icon"><FileTextIcon /></div>
                  <h3 className="analysis-card-title">{t.analysis.requirements.title}</h3>
                  <p className="analysis-card-desc">{t.analysis.requirements.functional}</p>
                  <p className="analysis-card-desc">{t.analysis.requirements.nonFunctional}</p>
                </div>
                
                <div className="analysis-card">
                  <div className="analysis-icon"><DatabaseIcon /></div>
                  <h3 className="analysis-card-title">{t.analysis.database.title}</h3>
                  <p className="analysis-card-desc">{t.analysis.database.description}</p>
                </div>
                
                <div className="analysis-card">
                  <div className="analysis-icon"><LayersIcon /></div>
                  <h3 className="analysis-card-title">{t.analysis.architecture.title}</h3>
                  <p className="analysis-card-desc">{t.analysis.architecture.description}</p>
                </div>
              </div>
            </div>
          )}
          
          {currentSection.id === "development" && (
            <div className="development-content">
              <div className="section-header">
                <div className="section-badge">{t.development.badge}</div>
                <h2 className="section-title">{t.development.title}</h2>
                <p className="section-description">{t.development.description}</p>
              </div>
              
              <div className="development-grid">
                <div className="development-flow">
                  <h3 className="development-subtitle">{t.development.flow.title}</h3>
                  <div className="flow-steps">
                    <div className="flow-step">
                      <div className="flow-step-number">1</div>
                      <div className="flow-step-text">{t.development.flow.step1}</div>
                    </div>
                    <div className="flow-step">
                      <div className="flow-step-number">2</div>
                      <div className="flow-step-text">{t.development.flow.step2}</div>
                    </div>
                    <div className="flow-step">
                      <div className="flow-step-number">3</div>
                      <div className="flow-step-text">{t.development.flow.step3}</div>
                    </div>
                    <div className="flow-step">
                      <div className="flow-step-number">4</div>
                      <div className="flow-step-text">{t.development.flow.step4}</div>
                    </div>
                  </div>
                </div>
                
                <div className="development-logic">
                  <h3 className="development-subtitle">{t.development.logic.title}</h3>
                  <p className="development-desc">{t.development.logic.description}</p>
                  <ul className="development-list">
                    <li>{t.development.logic.point1}</li>
                    <li>{t.development.logic.point2}</li>
                    <li>{t.development.logic.point3}</li>
                  </ul>
                </div>
              </div>
            </div>
          )}
          
          {currentSection.id === "testing" && (
            <div className="testing-content">
              <div className="section-header">
                <div className="section-badge">{t.testing.badge}</div>
                <h2 className="section-title">{t.testing.title}</h2>
                <p className="section-description">{t.testing.description}</p>
              </div>
              
              <div className="testing-grid">
                <div className="testing-card">
                  <div className="testing-icon"><CheckCircleIcon /></div>
                  <h3 className="testing-card-title">{t.testing.types.title}</h3>
                  <ul className="testing-list">
                    <li>{t.testing.types.functional}</li>
                    <li>{t.testing.types.usability}</li>
                    <li>{t.testing.types.performance}</li>
                  </ul>
                </div>
                
                <div className="testing-card">
                  <div className="testing-icon"><AlertTriangleIcon /></div>
                  <h3 className="testing-card-title">{t.testing.errors.title}</h3>
                  <ul className="testing-list">
                    <li>{t.testing.errors.error1}</li>
                    <li>{t.testing.errors.error2}</li>
                    <li>{t.testing.errors.error3}</li>
                  </ul>
                </div>
                
                <div className="testing-card">
                  <div className="testing-icon"><ShieldCheckIcon /></div>
                  <h3 className="testing-card-title">{t.testing.validation.title}</h3>
                  <ul className="testing-list">
                    <li>{t.testing.validation.data}</li>
                    <li>{t.testing.validation.error}</li>
                    <li>{t.testing.validation.security}</li>
                  </ul>
                </div>
              </div>
            </div>
          )}
          
          {currentSection.id === "results" && (
            <div className="results-content">
              <div className="section-header">
                <div className="section-badge">{t.results.badge}</div>
                <h2 className="section-title">{t.results.title}</h2>
                <p className="section-description">{t.results.description}</p>
              </div>
              
              <div className="results-grid">
                <div className="results-card">
                  <div className="results-icon"><CheckCircleIcon /></div>
                  <h3 className="results-card-title">{t.results.features.title}</h3>
                  <ul className="results-list">
                    <li>{t.results.features.feature1}</li>
                    <li>{t.results.features.feature2}</li>
                    <li>{t.results.features.feature3}</li>
                    <li>{t.results.features.feature4}</li>
                  </ul>
                </div>
                
                <div className="results-card">
                  <div className="results-icon"><LightbulbIcon /></div>
                  <h3 className="results-card-title">{t.results.challenges.title}</h3>
                  <ul className="results-list">
                                        <li>{t.results.challenges.challenge1}</li>
                    <li>{t.results.challenges.challenge2}</li>
                    <li>{t.results.challenges.challenge3}</li>
                  </ul>
                </div>
                
                <div className="results-card">
                  <div className="results-icon"><BookOpenIcon /></div>
                  <h3 className="results-card-title">{t.results.learning.title}</h3>
                  <ul className="results-list">
                    <li>{t.results.learning.learning1}</li>
                    <li>{t.results.learning.learning2}</li>
                    <li>{t.results.learning.learning3}</li>
                  </ul>
                </div>
              </div>
            </div>
          )}
          
          {currentSection.id === "conclusion" && (
            <div className="conclusion-content">
              <div className="section-header">
                <div className="section-badge">{t.conclusion.badge}</div>
                <h2 className="section-title">{t.conclusion.title}</h2>
                <p className="section-description">{t.conclusion.description}</p>
              </div>
              
              <div className="conclusion-grid">
                <div className="conclusion-card">
                  <div className="conclusion-icon"><ThumbsUpIcon /></div>
                  <h3 className="conclusion-card-title">{t.conclusion.choice.title}</h3>
                  <p className="conclusion-card-desc">{t.conclusion.choice.description}</p>
                </div>
                
                <div className="conclusion-card">
                  <div className="conclusion-icon"><ArrowUpRightIcon /></div>
                  <h3 className="conclusion-card-title">{t.conclusion.future.title}</h3>
                  <p className="conclusion-card-desc">{t.conclusion.future.description}</p>
                </div>
                
                <div className="conclusion-card">
                  <div className="conclusion-icon"><HeartIcon /></div>
                  <h3 className="conclusion-card-title">{t.conclusion.thanks.title}</h3>
                  <p className="conclusion-card-desc">{t.conclusion.thanks.description}</p>
                </div>
              </div>
            </div>
          )}
          
          {currentSection.id === "features" && (
            <div className="features-content">
              <div className="section-header">
                <h2 className="section-title">{t.features.title}</h2>
                <p className="section-description">{t.features.description}</p>
              </div>
              
              <div className="features-grid">
                {features.map((feature, idx) => (
                  <div key={idx} className="feature-card">
                    <div className="feature-icon">{feature.icon}</div>
                    <h3 className="feature-title">{feature.title}</h3>
                    <p className="feature-description">{feature.desc}</p>
                  </div>
                ))}
              </div>
            </div>
          )}
          
          {currentSection.id === "download" && (
            <div className="download-content">
              <div className="section-header">
                <div className="download-badge">
                  <DownloadIcon />
                  <span>{t.download.badge}</span>
                </div>
                <h2 className="section-title">
                  {t.download.title} <span className="title-highlight">MyFinance</span>
                </h2>
                <p className="section-description">{t.download.description}</p>
              </div>
              
              <div className="stats-grid">
                <div className="stat-card">
                  <div className="stat-number">{downloadCount.toLocaleString()}+</div>
                  <div className="stat-label">{t.download.stats.downloads}</div>
                </div>
                <div className="stat-card">
                  <div className="stat-number">4.8</div>
                  <div className="stat-label">{t.download.stats.rating}</div>
                </div>
                <div className="stat-card">
                  <div className="stat-number">€2M+</div>
                  <div className="stat-label">{t.download.stats.saved}</div>
                </div>
              </div>
              
              <div style={{ textAlign: 'center' }}>
                <a href="/myFinance.zip" download="myFinance.zip" className="download-btn">
                  <DownloadIcon />
                  <span>{t.download.button}</span>
                </a>
                <p className="download-note">{t.download.note}</p>
              </div>
            </div>
          )}
          
          {currentSection.id === "contact" && (
            <div className="contact-content">
              <div className="section-header">
                <h2 className="section-title">{t.contact.title}</h2>
                <p className="section-description">{t.contact.description}</p>
              </div>
              
              <div className="contact-grid">
                <div className="contact-card">
                  <h3 className="contact-card-title">{t.contact.info.title}</h3>
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
                
                <div className="contact-card">
                  <h3 className="contact-card-title">{t.contact.social.title}</h3>
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
                    <p className="contact-cta-text">{t.contact.cta.text}</p>
                    <a href="mailto:miguel.reyesgomez1@gmail.com" className="contact-cta-btn">
                      <EmailIcon />
                      {t.contact.cta.button}
                    </a>
                  </div>
                </div>
              </div>
            </div>
          )}
        </div>
        
        {/* Controles de presentación */}
        <div className="presentation-controls">
          <button
            onClick={handlePrevSlide}
            disabled={currentSlide === 0}
            className="presentation-nav-btn"
          >
            <span>← Anterior</span>
          </button>
          
          <div className="presentation-info">
            <div className="presentation-counter">
              {currentSlide + 1} / {sections.length}
            </div>
            <div className="presentation-section-name">
              {navItems.find(item => item.id === currentSection.id)?.label}
            </div>
          </div>
          
          <button
            onClick={handleNextSlide}
            disabled={currentSlide === sections.length - 1}
            className="presentation-nav-btn"
          >
            <span>Siguiente →</span>
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="app">
      {/* Controles de idioma y presentación */}
      <div className="fixed top-4 right-4 z-50 flex gap-2">
        <button
          onClick={toggleLanguage}
          className="language-switcher"
        >
          <GlobeIcon />
          <span>{t.languageSwitcher}</span>
        </button>
        <button
          onClick={togglePresentationMode}
          className="presentation-mode-btn"
        >
          {t.presentationMode}
        </button>
      </div>

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
              <button 
                onClick={() => setMobileMenuOpen(!mobileMenuOpen)} 
                className="mobile-menu-btn"
                aria-label={mobileMenuOpen ? "Cerrar menú" : "Abrir menú"}
              >
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
      <section id="home" ref={homeRef} className={getSectionClass("hero-section")}>
        <div className="hero-content">
          <h1 className="hero-title">
            My<span className="title-highlight">Finance</span>
          </h1>
          <h2 className="hero-subtitle">{t.hero.subtitle}</h2>
          <p className="hero-description">{t.hero.description}</p>
          <div className="hero-buttons">
            <button onClick={() => scrollToSection("download")} className="btn-primary">
              {t.hero.downloadButton}
            </button>
            <button onClick={() => scrollToSection("features")} className="btn-secondary">
              {t.hero.featuresButton}
            </button>
          </div>
        </div>
      </section>

      <div className="section-separator"></div>

      {/* Motivation Section */}
      <section id="motivation" ref={motivationRef} className={getSectionClass("motivation-section")}>
        <div className="section-container">
          <div className="section-header">
            <div className="section-badge">{t.motivation.badge}</div>
            <h2 className="section-title">{t.motivation.title}</h2>
            <p className="section-description">{t.motivation.description}</p>
          </div>

          <div className="motivation-grid">
            <div className="motivation-card">
              <div className="motivation-icon"><LightbulbIcon /></div>
              <h3 className="motivation-card-title">{t.motivation.why.title}</h3>
              <p className="motivation-card-desc">{t.motivation.why.description}</p>
            </div>

            <div className="motivation-card">
              <div className="motivation-icon"><SearchIcon /></div>
              <h3 className="motivation-card-title">{t.motivation.need.title}</h3>
              <p className="motivation-card-desc">{t.motivation.need.description}</p>
            </div>

            <div className="motivation-card">
              <div className="motivation-icon"><TargetIcon /></div>
              <h3 className="motivation-card-title">{t.motivation.solution.title}</h3>
              <p className="motivation-card-desc">{t.motivation.solution.description}</p>
            </div>
          </div>
        </div>
      </section>

      <div className="section-separator"></div>

      {/* Analysis Section */}
      <section id="analysis" ref={analysisRef} className={getSectionClass("analysis-section")}>
        <div className="section-container">
          <div className="section-header">
            <div className="section-badge">{t.analysis.badge}</div>
            <h2 className="section-title">{t.analysis.title}</h2>
            <p className="section-description">{t.analysis.description}</p>
          </div>

          <div className="analysis-grid">
            <div className="analysis-card">
              <div className="analysis-icon"><FileTextIcon /></div>
              <h3 className="analysis-card-title">{t.analysis.requirements.title}</h3>
              <p className="analysis-card-desc">{t.analysis.requirements.functional}</p>
              <p className="analysis-card-desc">{t.analysis.requirements.nonFunctional}</p>
            </div>

            <div className="analysis-card">
              <div className="analysis-icon"><DatabaseIcon /></div>
              <h3 className="analysis-card-title">{t.analysis.database.title}</h3>
              <p className="analysis-card-desc">{t.analysis.database.description}</p>
            </div>

            <div className="analysis-card">
              <div className="analysis-icon"><LayersIcon /></div>
              <h3 className="analysis-card-title">{t.analysis.architecture.title}</h3>
              <p className="analysis-card-desc">{t.analysis.architecture.description}</p>
            </div>
          </div>
        </div>
      </section>

      <div className="section-separator"></div>

      {/* Development Section */}
      <section id="development" ref={developmentRef} className={getSectionClass("development-section")}>
        <div className="section-container">
          <div className="section-header">
            <div className="section-badge">{t.development.badge}</div>
            <h2 className="section-title">{t.development.title}</h2>
            <p className="section-description">{t.development.description}</p>
          </div>

          <div className="development-grid">
            <div className="development-flow">
              <h3 className="development-subtitle">{t.development.flow.title}</h3>
              <div className="flow-steps">
                <div className="flow-step">
                  <div className="flow-step-number">1</div>
                  <div className="flow-step-text">{t.development.flow.step1}</div>
                </div>
                <div className="flow-step">
                  <div className="flow-step-number">2</div>
                  <div className="flow-step-text">{t.development.flow.step2}</div>
                </div>
                <div className="flow-step">
                  <div className="flow-step-number">3</div>
                  <div className="flow-step-text">{t.development.flow.step3}</div>
                </div>
                <div className="flow-step">
                  <div className="flow-step-number">4</div>
                  <div className="flow-step-text">{t.development.flow.step4}</div>
                </div>
              </div>
            </div>

            <div className="development-logic">
              <h3 className="development-subtitle">{t.development.logic.title}</h3>
              <p className="development-desc">{t.development.logic.description}</p>
              <ul className="development-list">
                <li>{t.development.logic.point1}</li>
                <li>{t.development.logic.point2}</li>
                <li>{t.development.logic.point3}</li>
              </ul>
            </div>
          </div>
        </div>
      </section>

      <div className="section-separator"></div>

      {/* Testing Section */}
      <section id="testing" ref={testingRef} className={getSectionClass("testing-section")}>
        <div className="section-container">
          <div className="section-header">
            <div className="section-badge">{t.testing.badge}</div>
            <h2 className="section-title">{t.testing.title}</h2>
            <p className="section-description">{t.testing.description}</p>
          </div>

          <div className="testing-grid">
            <div className="testing-card">
              <div className="testing-icon"><CheckCircleIcon /></div>
              <h3 className="testing-card-title">{t.testing.types.title}</h3>
              <ul className="testing-list">
                <li>{t.testing.types.functional}</li>
                <li>{t.testing.types.usability}</li>
                <li>{t.testing.types.performance}</li>
              </ul>
            </div>

            <div className="testing-card">
              <div className="testing-icon"><AlertTriangleIcon /></div>
              <h3 className="testing-card-title">{t.testing.errors.title}</h3>
              <ul className="testing-list">
                <li>{t.testing.errors.error1}</li>
                <li>{t.testing.errors.error2}</li>
                <li>{t.testing.errors.error3}</li>
              </ul>
            </div>

            <div className="testing-card">
              <div className="testing-icon"><ShieldCheckIcon /></div>
              <h3 className="testing-card-title">{t.testing.validation.title}</h3>
              <ul className="testing-list">
                <li>{t.testing.validation.data}</li>
                <li>{t.testing.validation.error}</li>
                <li>{t.testing.validation.security}</li>
              </ul>
            </div>
          </div>
        </div>
      </section>

      <div className="section-separator"></div>

      {/* Results Section */}
      <section id="results" ref={resultsRef} className={getSectionClass("results-section")}>
        <div className="section-container">
          <div className="section-header">
            <div className="section-badge">{t.results.badge}</div>
            <h2 className="section-title">{t.results.title}</h2>
            <p className="section-description">{t.results.description}</p>
          </div>

          <div className="results-grid">
            <div className="results-card">
              <div className="results-icon"><CheckCircleIcon /></div>
              <h3 className="results-card-title">{t.results.features.title}</h3>
              <ul className="results-list">
                <li>{t.results.features.feature1}</li>
                <li>{t.results.features.feature2}</li>
                <li>{t.results.features.feature3}</li>
                <li>{t.results.features.feature4}</li>
              </ul>
            </div>

            <div className="results-card">
              <div className="results-icon"><LightbulbIcon /></div>
              <h3 className="results-card-title">{t.results.challenges.title}</h3>
              <ul className="results-list">
                <li>{t.results.challenges.challenge1}</li>
                <li>{t.results.challenges.challenge2}</li>
                <li>{t.results.challenges.challenge3}</li>
              </ul>
            </div>

            <div className="results-card">
              <div className="results-icon"><BookOpenIcon /></div>
              <h3 className="results-card-title">{t.results.learning.title}</h3>
              <ul className="results-list">
                <li>{t.results.learning.learning1}</li>
                <li>{t.results.learning.learning2}</li>
                <li>{t.results.learning.learning3}</li>
              </ul>
            </div>
          </div>
        </div>
      </section>

      <div className="section-separator"></div>

      {/* Conclusion Section */}
      <section id="conclusion" ref={conclusionRef} className={getSectionClass("conclusion-section")}>
        <div className="section-container">
          <div className="section-header">
            <div className="section-badge">{t.conclusion.badge}</div>
            <h2 className="section-title">{t.conclusion.title}</h2>
            <p className="section-description">{t.conclusion.description}</p>
          </div>

          <div className="conclusion-grid">
            <div className="conclusion-card">
              <div className="conclusion-icon"><ThumbsUpIcon /></div>
              <h3 className="conclusion-card-title">{t.conclusion.choice.title}</h3>
              <p className="conclusion-card-desc">{t.conclusion.choice.description}</p>
            </div>

            <div className="conclusion-card">
              <div className="conclusion-icon"><ArrowUpRightIcon /></div>
              <h3 className="conclusion-card-title">{t.conclusion.future.title}</h3>
              <p className="conclusion-card-desc">{t.conclusion.future.description}</p>
            </div>

            <div className="conclusion-card">
              <div className="conclusion-icon"><HeartIcon /></div>
              <h3 className="conclusion-card-title">{t.conclusion.thanks.title}</h3>
              <p className="conclusion-card-desc">{t.conclusion.thanks.description}</p>
            </div>
          </div>
        </div>
      </section>

      <div className="section-separator"></div>

      {/* Features Section */}
      <section id="features" ref={featuresRef} className={getSectionClass("features-section")}>
        <div className="section-container">
          <div className="section-header">
            <h2 className="section-title">{t.features.title}</h2>
            <p className="section-description">{t.features.description}</p>
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
      <section id="download" ref={downloadRef} className={getSectionClass("download-section")}>
        <div className="section-container">
          <div className="download-badge">
            <DownloadIcon />
            <span>{t.download.badge}</span>
          </div>

          <h2 className="section-title">
            {t.download.title} <span className="title-highlight">MyFinance</span>
          </h2>

          <p className="section-description">{t.download.description}</p>

          {/* Estadísticas */}
          <div className="stats-grid">
            <div className="stat-card">
              <div className="stat-number">{downloadCount.toLocaleString()}+</div>
              <div className="stat-label">{t.download.stats.downloads}</div>
            </div>
            <div className="stat-card">
              <div className="stat-number">4.8</div>
              <div className="stat-label">{t.download.stats.rating}</div>
            </div>
            <div className="stat-card">
              <div className="stat-number">€2M+</div>
              <div className="stat-label">{t.download.stats.saved}</div>
            </div>
          </div>

          {/* Botón de descarga */}
          <a href="/myFinance.zip" download="myFinance.zip" className="download-btn">
            <DownloadIcon />
            <span>{t.download.button}</span>
          </a>

          <p className="download-note">{t.download.note}</p>
        </div>
      </section>

      <div className="section-separator"></div>

      {/* Contact Section */}
      <section id="contact" ref={contactRef} className={getSectionClass("contact-section")}>
        <div className="section-container">
          <div className="section-header">
            <h2 className="section-title">{t.contact.title}</h2>
            <p className="section-description">{t.contact.description}</p>
          </div>

          <div className="contact-grid">
            {/* Información de contacto */}
            <div className="contact-card">
              <h3 className="contact-card-title">{t.contact.info.title}</h3>
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
              <h3 className="contact-card-title">{t.contact.social.title}</h3>
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
                <p className="contact-cta-text">{t.contact.cta.text}</p>
                <a href="mailto:miguel.reyesgomez1@gmail.com" className="contact-cta-btn">
                  <EmailIcon />
                  {t.contact.cta.button}
                </a>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Footer */}
      <footer className="footer">
        <div className="footer-content">
          <p>© 2025 MyFinance. {t.footer.developedBy} Miguel Reyes Gómez</p>
        </div>
      </footer>
    </div>
  );
}

export default App;