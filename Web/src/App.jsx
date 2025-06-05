"use client"

import { useEffect, useRef, useState } from "react"
import "./App.css"

// Iconos existentes
const PiggyBankIcon = () => (
  <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
    <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm-2 15l-5-5 1.41-1.41L10 14.17l7.59-7.59L19 8l-9 9z" />
  </svg>
)

const ReceiptIcon = () => (
  <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
    <path d="M18 17H6v-2h12v2zm0-4H6v-2h12v2zm0-4H6V7h12v2zM3 22l1.5-1.5L6 22l1.5-1.5L9 22l1.5-1.5L12 22l1.5-1.5L15 22l1.5-1.5L18 22l1.5-1.5L21 22V2l-1.5 1.5L18 2l-1.5 1.5L15 2l-1.5 1.5L12 2l-1.5 1.5L9 2L7.5 3.5L6 2L4.5 3.5L3 2v20z" />
  </svg>
)

const TagsIcon = () => (
  <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
    <path d="M17.63 5.84C17.27 5.33 16.67 5 16 5L5 5.01C3.9 5.01 3 5.9 3 7v10c0 1.1.9 1.99 2 2L16 19c.67 0 1.27-.33 1.63-.84L22 12l-4.37-6.16z" />
  </svg>
)

const HistoryIcon = () => (
  <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
    <path d="M13 3c-4.97 0-9 4.03-9 9H1l3.89 3.89.07.14L9 12H6c0-3.87 3.13-7 7-7s7 3.13 7 7-3.13 7-7 7c-1.93 0-3.68-.79-4.94-2.06l-1.42 1.42C8.27 19.99 10.51 21 13 21c4.97 0 9-4.03 9-9s-4.03-9-9-9zm-1 5v5l4.28 2.54.72-1.21-3.5-2.08V8H12z" />
  </svg>
)

const ChartIcon = () => (
  <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
    <path d="M11 2v20c-5.07-.5-9-4.79-9-10s3.93-9.5 9-10zm2.03 0v8.99H22c-.47-4.74-4.24-8.52-8.97-8.99zm0 11.01V22c4.74-.47 8.5-4.25 8.97-8.99h-8.97z" />
  </svg>
)

const CalculatorIcon = () => (
  <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
    <path d="M19 3H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zm-5 14H7v-2h7v2zm3-4H7v-2h10v2zm0-4H7V7h10v2z" />
  </svg>
)

const MoonIcon = () => (
  <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
    <path d="M9 2c-1.05 0-2.05.16-3 .46 4.06 1.27 7 5.06 7 9.54 0 4.48-2.94 8.27-7 9.54.95.3 1.95.46 3 .46 5.52 0 10-4.48 10-10S14.52 2 9 2z" />
  </svg>
)

const ServerIcon = () => (
  <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
    <path d="M4 1h16c1.1 0 2 .9 2 2v6c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V3c0-1.1.9-2 2-2zm0 12h16c1.1 0 2 .9 2 2v6c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2v-6c0-1.1.9-2 2-2z" />
  </svg>
)

const DownloadIcon = () => (
  <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
    <path d="M19 9h-4V3H9v6H5l7 7 7-7zM5 18v2h14v-2H5z" />
  </svg>
)

const EmailIcon = () => (
  <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
    <path d="M20 4H4c-1.1 0-1.99.9-1.99 2L2 18c0 1.1.89 2 2 2h16c1.1 0 2-.9 2-2V6c0-1.1-.9-2-2-2zm0 4l-8 5-8-5V6l8 5 8-5v2z" />
  </svg>
)

const PhoneIcon = () => (
  <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
    <path d="M6.62 10.79c1.44 2.83 3.76 5.14 6.59 6.59l2.2-2.2c.27-.27.67-.36 1.02-.24 1.12.37 2.33.57 3.57.57.55 0 1 .45 1 1V20c0 .55-.45 1-1 1-9.39 0-17-7.61-17-17 0-.55.45-1 1-1h3.5c.55 0 1 .45 1 1 0 1.25.2 2.45.57 3.57.11.35.03.74-.25 1.02l-2.2 2.2z" />
  </svg>
)

const LocationIcon = () => (
  <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
    <path d="M12 2C8.13 2 5 5.13 5 9c0 5.25 7 13 7 13s7-7.75 7-13c0-3.87-3.13-7-7-7zm0 9.5c-1.38 0-2.5-1.12-2.5-2.5s1.12-2.5 2.5-2.5 2.5 1.12 2.5 2.5-1.12 2.5-2.5 2.5z" />
  </svg>
)

const HomeIcon = () => (
  <svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
    <path d="M10 20v-6h4v6h5v-8h3L12 3 2 12h3v8z" />
  </svg>
)

const StarIcon = () => (
  <svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
    <path d="M12 17.27L18.18 21l-1.64-7.03L22 9.24l-7.19-.61L12 2 9.19 8.63 2 9.24l5.46 4.73L5.82 21z" />
  </svg>
)

const BarsIcon = () => (
  <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
    <path d="M3 18h18v-2H3v2zm0-5h18v-2H3v2zm0-7v2h18V6H3z" />
  </svg>
)

const TimesIcon = () => (
  <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
    <path d="M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z" />
  </svg>
)

const GitHubIcon = () => (
  <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
    <path d="M12 0c-6.626 0-12 5.373-12 12 0 5.302 3.438 9.8 8.207 11.387.599.111.793-.261.793-.577v-2.234c-3.338.726-4.033-1.416-4.033-1.416-.546-1.387-1.333-1.756-1.333-1.756-1.089-.745.083-.729.083-.729 1.205.084 1.839 1.237 1.839 1.237 1.07 1.834 2.807 1.304 3.492.997.107-.775.418-1.305.762-1.604-2.665-.305-5.467-1.334-5.467-5.931 0-1.311.469-2.381 1.236-3.221-.124-.303-.535-1.524.117-3.176 0 0 1.008-.322 3.301 1.23.957-.266 1.983-.399 3.003-.404 1.02.005 2.047.138 3.006.404 2.291-1.552 3.297-1.23 3.297-1.23.653 1.653.242 2.874.118 3.176.77.84 1.235 1.911 1.235 3.221 0 4.609-2.807 5.624-5.479 5.921.43.372.823 1.102.823 2.222v3.293c0 .319.192.694.801.576 4.765-1.589 8.199-6.086 8.199-11.386 0-6.627-5.373-12-12-12z" />
  </svg>
)

const LinkedInIcon = () => (
  <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
    <path d="M20.447 20.452h-3.554v-5.569c0-1.328-.027-3.037-1.852-3.037-1.853 0-2.136 1.445-2.136 2.939v5.667H9.351V9h3.414v1.561h.046c.477-.9 1.637-1.85 3.37-1.85 3.601 0 4.267 2.37 4.267 5.455v6.286zM5.337 7.433c-1.144 0-2.063-.926-2.063-2.065 0-1.138.92-2.063 2.063-2.063 1.14 0 2.064.925 2.064 2.063 0 1.139-.925 2.065-2.064 2.065zm1.782 13.019H3.555V9h3.564v11.452zM22.225 0H1.771C.792 0 0 .774 0 1.729v20.542C0 23.227.792 24 1.771 24h20.451C23.2 24 24 23.227 24 22.271V1.729C24 .774 23.2 0 22.222 0h.003z" />
  </svg>
)

// Nuevos iconos para las secciones adicionales
const LightbulbIcon = () => (
  <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
    <path d="M9 21c0 .5.4 1 1 1h4c.6 0 1-.5 1-1v-1H9v1zm3-19C8.1 2 5 5.1 5 9c0 2.4 1.2 4.5 3 5.7V17c0 .5.4 1 1 1h6c.6 0 1-.5 1-1v-2.3c1.8-1.3 3-3.4 3-5.7 0-3.9-3.1-7-7-7z" />
  </svg>
)

const SearchIcon = () => (
  <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
    <path d="M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z" />
  </svg>
)

const TargetIcon = () => (
  <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
    <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 18c-4.42 0-8-3.58-8-8s3.58-8 8-8 8 3.58 8 8-3.58 8-8 8zm0-14c-3.31 0-6 2.69-6 6s2.69 6 6 6 6-2.69 6-6-2.69-6-6-6zm0 10c-2.21 0-4-1.79-4-4s1.79-4 4-4 4 1.79 4 4-1.79 4-4 4z" />
  </svg>
)

const FileTextIcon = () => (
  <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
    <path d="M14 2H6c-1.1 0-1.99.9-1.99 2L4 20c0 1.1.89 2 1.99 2H18c1.1 0 2-.9 2-2V8l-6-6zm2 16H8v-2h8v2zm0-4H8v-2h8v2zm-3-5V3.5L18.5 9H13z" />
  </svg>
)

const GlobeIcon = () => (
  <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
    <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm-1 17.93c-3.94-.49-7-3.85-7-7.93 0-.62.08-1.21.21-1.79L9 15v1c0 1.1.9 2 2 2v1.93zm6.9-2.54c-.26-.81-1-1.39-1.9-1.39h-1v-3c0-.55-.45-1-1-1H8v-2h2c.55 0 1-.45 1-1V7h2c1.1 0 2-.9 2-2v-.41c2.93 1.19 5 4.06 5 7.41 0 2.08-.8 3.97-2.1 5.39z" />
  </svg>
)

const ThumbsUpIcon = () => (
  <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
    <path d="M1 21h4V9H1v12zm22-11c0-1.1-.9-2-2-2h-6.31l.95-4.57.03-.32c0-.41-.17-.79-.44-1.06L14.17 1 7.59 7.59C7.22 7.95 7 8.45 7 9v10c0 1.1.9 2 2 2h9c.83 0 1.54-.5 1.84-1.22l3.02-7.05c.09-.23.14-.47.14-.73v-1.91l-.01-.01L23 10z" />
  </svg>
)

const ArrowUpRightIcon = () => (
  <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
    <path d="M9 5v2h6.59L4 18.59 5.41 20 17 8.41V15h2V5z" />
  </svg>
)

const HeartIcon = () => (
  <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
    <path d="M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z" />
  </svg>
)

const ToolIcon = () => (
  <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
    <path d="M22.7 19l-9.1-9.1c.9-2.3.4-5-1.5-6.9-2-2-5-2.4-7.4-1.3L9 6 6 9 1.6 4.7C.4 7.1.9 10.1 2.9 12.1c1.9 1.9 4.6 2.4 6.9 1.5l9.1 9.1c.4.4 1 .4 1.4 0l2.3-2.3c.5-.4.5-1.1.1-1.4z" />
  </svg>
)

// Hook personalizado para detectar el ancho de la ventana
function useViewportWidth() {
  const [width, setWidth] = useState(typeof window !== "undefined" ? window.innerWidth : 1200)

  useEffect(() => {
    if (typeof window === "undefined") return

    const handleResize = () => setWidth(window.innerWidth)
    window.addEventListener("resize", handleResize)
    return () => window.removeEventListener("resize", handleResize)
  }, [])

  return width
}

// Traducciones actualizadas con contenido del TFG
const translations = {
  es: {
    nav: {
      home: "Inicio",
      motivation: "Motivación",
      objectives: "Objetivos",
      proposal: "Propuesta",
      features: "Características",
      conclusion: "Conclusión",
      download: "Descarga",
      contact: "Contacto",
    },
    hero: {
      subtitle: "Tus finanzas sin límites",
      description:
        "MyFinance es una aplicación móvil desarrollada para el Trabajo de Fin de Grado, cuyo objetivo principal es facilitar el control financiero personal de forma práctica, accesible e intuitiva.",
      downloadButton: "Descargar Aplicación",
    },
    motivation: {
      badge: "Motivación del Proyecto",
      title: "¿Por qué MyFinance?",
      description:
        "Las razones personales y profesionales que me llevaron a desarrollar esta aplicación de finanzas personales.",
      personal: {
        title: "Motivación Personal",
        description:
          "Mi motivación surge de mi inquietud por profundizar en Kotlin y Jetpack Compose, ambos los descubrí durante el ciclo y me resultaron muy atractivos para el desarrollo móvil. Al pensar en el tema de mi TFG, me resultó muy fácil elegirlo. El mundo de las finanzas personales siempre me ha llamado la atención y quise unir ambas pasiones en una sola aplicación.",
      },
      professional: {
        title: "Motivación Profesional",
        description:
          "Desde un enfoque profesional, este proyecto representa una oportunidad única para afianzar mis competencias en el diseño y construcción de aplicaciones Android. La experiencia práctica reforzará mi perfil como desarrollador especializado en el ecosistema Android, un sector con alta demanda en la industria tecnológica.",
      },
      impact: {
        title: "Impacto Esperado",
        description:
          "Desarrollar una aplicación que realmente ayude a las personas a gestionar sus finanzas de manera sencilla, segura y efectiva.",
      },
    },
    objectives: {
      badge: "Objetivos del Proyecto",
      title: "Objetivos General y Específicos",
      description: "Los objetivos que guían el desarrollo de MyFinance y definen el alcance del proyecto.",
      general: {
        title: "Objetivo General",
        description:
          "El propósito principal de este Trabajo de Fin de Grado es desarrollar una aplicación móvil que ofrezca una solución integral para la gestión de finanzas personales, combinando una interfaz intuitiva y accesible con funcionalidades avanzadas de análisis y proyección económica.",
      },
      specific: {
        title: "Objetivos Específicos",
        items: [
          "Implementar un sistema de registro y categorización de ingresos y gastos con validación de datos",
          "Diseñar una interfaz moderna aplicando Material Design y utilizando Jetpack Compose",
          "Desarrollar una calculadora de interés compuesto totalmente personalizable",
          "Crear un sistema de perfil con modo oscuro/claro y almacenamiento local con SQLite",
        ],
      },
    },
    proposal: {
      badge: "Propuesta de Valor",
      title: "Propuesta e Innovaciones",
      description: "La propuesta de valor única de MyFinance y sus características diferenciadoras en el mercado.",
      context: {
        title: "Contextualización del Dominio",
        description:
          "Este TFG se posiciona en el contexto del desarrollo de aplicaciones móviles enfocadas a la gestión de finanzas personales, un área con creciente demanda de soluciones accesibles y seguras para el usuario.",
      },
      value: {
        title: "Propuesta de Valor",
        description:
          "MyFinance se distingue por ofrecer privacidad total y gran facilidad de uso, cubriendo registro, categorización y visualización de gastos sin depender de suscripciones o servicios en la nube.",
      },
      innovations: {
        title: "Innovaciones y Características Diferenciadoras",
        items: [
          "Hucha virtual que se actualiza automáticamente con cada transacción registrada",
          "Calculadora de interés compuesto con gráficos que muestran cuánto dinero generaría la cuenta",
          "Funcionamiento tanto en modo claro como oscuro, adaptándose correctamente en función del modo seleccionado",
          "Almacenamiento local seguro sin dependencia de servicios externos",
        ],
      },
    },
        features: {
      title: "Características de MyFinance",
      description:
        "Funcionalidades principales que hacen de MyFinance una herramienta completa para la gestión financiera personal.",
      items: [
        {
          title: "Hucha Virtual Inteligente",
          desc: "Visualización automática del balance actualizado con cada transacción registrada.",
        },
        {
          title: "Sistema de Trasacciones",
          desc: "Sistema de registro de ingresos y gastos con fecha, descripción y categorización automática.",
        },
        {
          title: "Categorización",
          desc: "Organización inteligente de transacciones con categorías predefinidas.",
        },
        {
          title: "Historial Detallado",
          desc: "Acceso completo al historial de transacciones con filtros y búsqueda avanzada.",
        },
        {
          title: "Gráficos Interactivos",
          desc: "Visualización de datos financieros con gráficos dinámicos y análisis de tendencias.",
        },
        {
          title: "Calculadora de Interés",
          desc: "Simulación de escenarios de ahorro con interés compuesto y proyecciones personalizables.",
        },
        {
          title: "Modo Dual",
          desc: "Interfaz adaptable con modo claro y oscuro, con persistencia de preferencias.",
        },
        {
          title: "Privacidad Total",
          desc: "Almacenamiento local con SQLite, sin dependencia de servicios en la nube.",
        },
      ],
    },
    conclusion: {
      badge: "Conclusiones",
      title: "Reflexiones y Futuro",
      description: "Conclusiones del proyecto y perspectivas de desarrollo futuro.",
      satisfaction: {
        title: "Satisfacción con el Resultado",
        description:
          "El proyecto ha cumplidao con las expectativas, logrando una aplicación robusta que combina exitosamente tecnología moderna con utilidad práctica para la gestión financiera personal.",
      },
      impact: {
        title: "Impacto Profesional",
        description:
          "Este TFG ha consolidado mis habilidades como desarrollador Android y me ha preparado para enfrentar proyectos complejos en el ámbito profesional.",
      },
    },
    download: {
      badge: "Aplicación Disponible",
      title: "Descarga MyFinance",
      description:
        "Aplicación desarrollada como Trabajo de Fin de Grado. Gestiona tus finanzas personales con tecnología Android moderna.",
      stats: {
        downloads: "Descargas",
        rating: "Valoración",
        features: "Funcionalidades",
      },
      button: "Descargar MyFinance APK",
      note: "Aplicación gratuita • Código abierto • Funcionamiento offline",
    },
    contact: {
      title: "Contacto - Miguel Reyes Gómez",
      description:
        "Desarrollador Android especializado en Kotlin y Jetpack Compose. ¡Conectemos para hablar de tecnología y oportunidades!",
      info: {
        title: "Información de contacto",
        email: {
          label: "Email",
        },
        phone: {
          label: "Teléfono",
        },
        location: {
          label: "Ubicación",
          value: "España",
        },
      },
      social: {
        title: "Redes profesionales",
      },
      cta: {
        text: "¿Interesado en colaborar o conocer más sobre el proyecto?",
        button: "Envíame un mensaje",
      },
    },
    footer: {
      developedBy: "TFG desarrollado por",
    },
    languageSwitcher: "/spain.png",
  },
  en: {
    nav: {
      home: "Home",
      motivation: "Motivation",
      objectives: "Objectives",
      proposal: "Proposal",
      features: "Features",
      conclusion: "Conclusion",
      download: "Download",
      contact: "Contact",
    },
    hero: {
      subtitle: "Your finances without limits",
      description:
        "MyFinance is a mobile application developed for the Final Degree Project, whose main objective is to facilitate personal financial management in a practical, accessible and intuitive way.",
      downloadButton: "Download Application",
    },
    motivation: {
      badge: "Project Motivation",
      title: "Why MyFinance?",
      description: "The personal and professional reasons that led me to develop this personal finance application.",
      personal: {
        title: "Personal Motivation",
        description:
          "My motivation stems from my interest in deepening my knowledge of Kotlin and Jetpack Compose, both of which I discovered during my studies and found very attractive for mobile development. When thinking about my Final Project topic, it was very easy to choose: the world of personal finance has always caught my attention.",
      },
      professional: {
        title: "Professional Motivation",
        description:
          "From a professional approach, this project represents a unique opportunity to strengthen my skills in the design and construction of Android applications. The practical experience will reinforce my profile as a developer specialized in the Android ecosystem, a sector with high demand in the technology industry.",
      },
      impact: {
        title: "Expected Impact",
        description:
          "Develop an application that truly helps people manage their finances simply, securely and effectively.",
      },
    },
    objectives: {
      badge: "Project Objectives",
      title: "General and Specific Objectives",
      description: "The objectives that guide MyFinance development and define the project scope.",
      general: {
        title: "General Objective",
        description:
          "The main purpose of this Final Degree Project is to develop a mobile application that offers a comprehensive solution for personal finance management, combining an intuitive and accessible interface with advanced analysis and economic projection functionalities.",
      },
      specific: {
        title: "Specific Objectives",
        items: [
          "Implement an income and expense registration and categorization system with data validation",
          "Design a modern interface applying Material Design and using Jetpack Compose",
          "Develop a fully customizable compound interest calculator",
          "Create a profile system with dark/light mode and local storage with SQLite",
        ],
      },
    },
    proposal: {
      badge: "Value Proposition",
      title: "Proposal and Innovations",
      description: "MyFinance's unique value proposition and its differentiating characteristics in the market.",
      context: {
        title: "Domain Contextualization",
        description:
          "This Final Project is positioned in the context of mobile application development focused on personal finance management, an area with growing demand for accessible and secure solutions for users.",
      },
      value: {
        title: "Value Proposition",
        description:
          "MyFinance stands out for offering total privacy and great ease of use, covering registration, categorization and visualization of expenses without depending on subscriptions or cloud services.",
      },
      innovations: {
        title: "Innovations and Differentiating Features",
        items: [
          "Virtual piggy bank that automatically updates with each registered transaction",
          "Compound interest calculator with charts showing how much money the account would generate",
          "Operation in both light and dark mode, adapting correctly according to the selected mode",
          "Secure local storage without dependency on external services",
        ],
      },
    },
        features: {
      title: "MyFinance Features",
      description: "Main functionalities that make MyFinance a complete tool for personal financial management.",
      items: [
        {
          title: "Smart Virtual Piggy Bank",
          desc: "Automatic visualization of updated balance with each registered transaction.",
        },
        {
          title: "Transaction System",
          desc: "Income and expense registration system with date, description and automatic categorization.",
        },
        {
          title: "Categorization",
          desc: "Intelligent organization of transactions with predefined categories.",
        },
        {
          title: "Detailed History",
          desc: "Complete access to transaction history with filters.",
        },
        {
          title: "Interactive Charts",
          desc: "Financial data visualization with dynamic charts.",
        },
        {
          title: "Interest Calculator",
          desc: "Saving scenario simulation with compound interest and customizable projections.",
        },
        {
          title: "Dual Mode",
          desc: "Adaptable interface with light and dark mode.",
        },
        {
          title: "Total Privacy",
          desc: "Local storage with SQLite, without dependency on cloud services.",
        },
      ],
    },
    conclusion: {
      badge: "Conclusions",
      title: "Reflections and Future",
      description: "Project conclusions and future development perspectives.",
      satisfaction: {
        title: "Satisfaction with the Result",
        description:
          "The project has exceeded initial expectations, achieving a robust application that successfully combines modern technology with practical utility for personal financial management.",
      },
      impact: {
        title: "Professional Impact",
        description:
          "This Final Project has consolidated my skills as an Android developer and prepared me to face complex projects in the professional field.",
      },
    },
    download: {
      badge: "Application Available",
      title: "Download MyFinance",
      description:
        "Application developed as Final Degree Project. Manage your personal finances with modern Android technology.",
      stats: {
        downloads: "Downloads",
        rating: "Rating",
        features: "Features",
      },
      button: "Download MyFinance APK",
      note: "Free application • Open source • Offline operation",
    },
    contact: {
      title: "Contact - Miguel Reyes Gómez",
      description:
        "Android developer specialized in Kotlin and Jetpack Compose. Let's connect to talk about technology and opportunities!",
      info: {
        title: "Contact information",
        email: {
          label: "Email",
        },
        phone: {
          label: "Phone",
        },
        location: {
          label: "Location",
          value: "Spain",
        },
      },
      social: {
        title: "Professional networks",
      },
      cta: {
        text: "Interested in collaborating or learning more about the project?",
        button: "Send me a message",
      },
    },
    footer: {
      developedBy: "Final project developed by",
    },
    languageSwitcher: "/english.png",
  },
}

function App() {
  const [downloadCount, setDownloadCount] = useState(0)
  const [activeSection, setActiveSection] = useState("home")
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false)
  const [language, setLanguage] = useState("es")
  const [presentationMode, setPresentationMode] = useState(false)
  const [currentSlide, setCurrentSlide] = useState(0)

  const viewportWidth = useViewportWidth()
  const isMobile = viewportWidth < 768

  // Crear refs individuales para cada sección
  const homeRef = useRef(null)
  const motivationRef = useRef(null)
  const objectivesRef = useRef(null)
  const proposalRef = useRef(null)
  const conclusionRef = useRef(null)
  const featuresRef = useRef(null)
  const downloadRef = useRef(null)
  const contactRef = useRef(null)

  // Obtener traducciones según el idioma seleccionado
  const t = translations[language]

  const navItems = [
    { id: "home", label: t.nav.home, icon: HomeIcon },
    { id: "motivation", label: t.nav.motivation, icon: LightbulbIcon },
    { id: "objectives", label: t.nav.objectives, icon: TargetIcon },
    { id: "proposal", label: t.nav.proposal, icon: StarIcon },
    { id: "features", label: t.nav.features, icon: StarIcon },
    { id: "conclusion", label: t.nav.conclusion, icon: ThumbsUpIcon },
    { id: "download", label: t.nav.download, icon: DownloadIcon },
    { id: "contact", label: t.nav.contact, icon: EmailIcon },
  ]

  // Definir las secciones para el modo presentación
  const sections = [
    { id: "home", ref: homeRef },
    { id: "motivation", ref: motivationRef },
    { id: "objectives", ref: objectivesRef },
    { id: "proposal", ref: proposalRef },
    { id: "features", ref: featuresRef },
    { id: "conclusion", ref: conclusionRef },
    { id: "download", ref: downloadRef },
    { id: "contact", ref: contactRef },
  ]

  useEffect(() => {
    // Animación del contador de descargas
    const timer = setInterval(() => {
      setDownloadCount((prev) => {
        if (prev < 8) {
          return prev + 1
        }
        clearInterval(timer)
        return 8
      })
    }, 200)

    // Intersection Observer para detectar secciones visibles
    const observer = new IntersectionObserver(
      (entries) => {
        entries.forEach((entry) => {
          if (entry.isIntersecting) {
            entry.target.classList.add("animate-in")
            setActiveSection(entry.target.id)
          }
        })
      },
      { threshold: 0.3, rootMargin: "0px 0px -200px 0px" },
    )

    // Observar todas las secciones
    const sectionRefs = [
      homeRef.current,
      motivationRef.current,
      objectivesRef.current,
      proposalRef.current,
      conclusionRef.current,
      featuresRef.current,
      downloadRef.current,
      contactRef.current,
    ]

    sectionRefs.forEach((section) => {
      if (section) observer.observe(section)
    })

    return () => {
      clearInterval(timer)
      observer.disconnect()
    }
  }, [])

  // Efecto para cerrar el menú móvil al hacer clic fuera
  useEffect(() => {
    if (mobileMenuOpen) {
      const handleClickOutside = (e) => {
        if (!e.target.closest(".navbar")) {
          setMobileMenuOpen(false)
        }
      }

      document.addEventListener("click", handleClickOutside)
      return () => document.removeEventListener("click", handleClickOutside)
    }
  }, [mobileMenuOpen])

  // Función para cambiar de idioma
  const toggleLanguage = () => {
    setLanguage((prev) => (prev === "es" ? "en" : "es"))
  }

  // Función para activar/desactivar modo presentación
  const togglePresentationMode = () => {
    setPresentationMode((prev) => !prev)
    setCurrentSlide(0)
  }

  // Funciones para navegación en modo presentación
  const handleNextSlide = () => {
    if (currentSlide < sections.length - 1) {
      setCurrentSlide((prev) => prev + 1)
    }
  }

  const handlePrevSlide = () => {
    if (currentSlide > 0) {
      setCurrentSlide((prev) => prev - 1)
    }
  }

  // Función mejorada para scroll con mejor experiencia en móvil
  const scrollToSection = (sectionId) => {
    const element = document.getElementById(sectionId)
    if (element) {
      if (isMobile) {
        setTimeout(() => {
          element.scrollIntoView({ behavior: "smooth" })
          setMobileMenuOpen(false)
        }, 100)
      } else {
        element.scrollIntoView({ behavior: "smooth" })
        setMobileMenuOpen(false)
      }
    }
  }

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
  ]

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
  ]

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
  ]

  // Función para determinar la clase CSS según el dispositivo
  const getSectionClass = (baseClass) => {
    return isMobile ? `${baseClass} mobile-visible` : `${baseClass} scroll-reveal`
  }

  // Renderizado condicional para modo presentación
  if (presentationMode) {
    const currentSection = sections[currentSlide]

    return (
      <div className="app presentation-mode">
        {/* Controles de idioma y presentación */}
        <div className="fixed top-10 right-6 z-50 flex gap-3">
          <button onClick={toggleLanguage} className="language-switcher-prominent">
            <img src={t.languageSwitcher} alt="Bandera del idioma" style={{ width: '36px', height: 'auto' }} />
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
                  <div className="motivation-icon">
                    <LightbulbIcon />
                  </div>
                  <h3 className="motivation-card-title">{t.motivation.personal.title}</h3>
                  <p className="motivation-card-desc">{t.motivation.personal.description}</p>
                </div>

                <div className="motivation-card">
                  <div className="motivation-icon">
                    <ToolIcon />
                  </div>
                  <h3 className="motivation-card-title">{t.motivation.professional.title}</h3>
                  <p className="motivation-card-desc">{t.motivation.professional.description}</p>
                </div>

                <div className="motivation-card">
                  <div className="motivation-icon">
                    <TargetIcon />
                  </div>
                  <h3 className="motivation-card-title">{t.motivation.impact.title}</h3>
                  <p className="motivation-card-desc">{t.motivation.impact.description}</p>
                </div>
              </div>
            </div>
          )}

          {currentSection.id === "objectives" && (
            <div className="analysis-content">
              <div className="section-header">
                <div className="section-badge">{t.objectives.badge}</div>
                <h2 className="section-title">{t.objectives.title}</h2>
                <p className="section-description">{t.objectives.description}</p>
              </div>

              <div className="analysis-grid">
                <div className="analysis-card">
                  <div className="analysis-icon">
                    <TargetIcon />
                  </div>
                  <h3 className="analysis-card-title">{t.objectives.general.title}</h3>
                  <p className="analysis-card-desc">{t.objectives.general.description}</p>
                </div>

                <div className="analysis-card">
                  <div className="analysis-icon">
                    <FileTextIcon />
                  </div>
                  <h3 className="analysis-card-title">{t.objectives.specific.title}</h3>
                  <ul className="testing-list">
                    {t.objectives.specific.items.map((item, index) => (
                      <li key={index}>{item}</li>
                    ))}
                  </ul>
                </div>
              </div>
            </div>
          )}

          {currentSection.id === "proposal" && (
            <div className="analysis-content">
              <div className="section-header">
                <div className="section-badge">{t.proposal.badge}</div>
                <h2 className="section-title">{t.proposal.title}</h2>
                <p className="section-description">{t.proposal.description}</p>
              </div>

              <div className="analysis-grid">
                <div className="analysis-card">
                  <div className="analysis-icon">
                    <SearchIcon />
                  </div>
                  <h3 className="analysis-card-title">{t.proposal.context.title}</h3>
                  <p className="analysis-card-desc">{t.proposal.context.description}</p>
                </div>

                <div className="analysis-card">
                  <div className="analysis-icon">
                    <StarIcon />
                  </div>
                  <h3 className="analysis-card-title">{t.proposal.value.title}</h3>
                  <p className="analysis-card-desc">{t.proposal.value.description}</p>
                </div>

                <div className="analysis-card">
                  <div className="analysis-icon">
                    <LightbulbIcon />
                  </div>
                  <h3 className="analysis-card-title">{t.proposal.innovations.title}</h3>
                  <ul className="testing-list">
                    {t.proposal.innovations.items.map((item, index) => (
                      <li key={index}>{item}</li>
                    ))}
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
                  <div className="conclusion-icon">
                    <ThumbsUpIcon />
                  </div>
                  <h3 className="conclusion-card-title">{t.conclusion.satisfaction.title}</h3>
                  <p className="conclusion-card-desc">{t.conclusion.satisfaction.description}</p>
                </div>

                <div className="conclusion-card">
                  <div className="conclusion-icon">
                    <HeartIcon />
                  </div>
                  <h3 className="conclusion-card-title">{t.conclusion.impact.title}</h3>
                  <p className="conclusion-card-desc">{t.conclusion.impact.description}</p>
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
                <h2 className="section-title">{t.download.title}</h2>
                <p className="section-description">{t.download.description}</p>
              </div>

              <div className="stats-grid">
                <div className="stat-card">
                  <div className="stat-number">{downloadCount}</div>
                  <div className="stat-label">{t.download.stats.features}</div>
                </div>
                <div className="stat-card">
                  <div className="stat-number">4.9</div>
                  <div className="stat-label">{t.download.stats.rating}</div>
                </div>
                <div className="stat-card">
                  <div className="stat-number">100%</div>
                  <div className="stat-label">Offline</div>
                </div>
              </div>

              <div style={{ textAlign: "center" }}>
                <a href="#" className="download-btn">
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
      </div>
    )
  }

  return (
    <div className="app">
      {/* Controles de idioma y presentación */}
      <div className="fixed top-6 right-6 z-50 flex gap-3">
        <button onClick={toggleLanguage} className="language-switcher-prominent">
          <img src={t.languageSwitcher} alt="Bandera del idioma" style={{ width: '36px', height: 'auto' }} />
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
                  className={`nav-item ${activeSection === item.id ? "nav-item-active" : ""}`}
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
                  className={`nav-mobile-item ${activeSection === item.id ? "nav-mobile-item-active" : ""}`}
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
            <a href="/MyFinance.apk" download>
              <button className="btn-primary">
                {t.hero.downloadButton}
              </button>
            </a>
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
              <div className="motivation-icon">
                <LightbulbIcon />
              </div>
              <h3 className="motivation-card-title">{t.motivation.personal.title}</h3>
              <p className="motivation-card-desc">{t.motivation.personal.description}</p>
            </div>

            <div className="motivation-card">
              <div className="motivation-icon">
                <ToolIcon />
              </div>
              <h3 className="motivation-card-title">{t.motivation.professional.title}</h3>
              <p className="motivation-card-desc">{t.motivation.professional.description}</p>
            </div>

            <div className="motivation-card">
              <div className="motivation-icon">
                <TargetIcon />
              </div>
              <h3 className="motivation-card-title">{t.motivation.impact.title}</h3>
              <p className="motivation-card-desc">{t.motivation.impact.description}</p>
            </div>
          </div>
        </div>
      </section>

      <div className="section-separator"></div>

      {/* Objectives Section */}
      <section id="objectives" ref={objectivesRef} className={getSectionClass("analysis-section")}>
        <div className="section-container">
          <div className="section-header">
            <div className="section-badge">{t.objectives.badge}</div>
            <h2 className="section-title">{t.objectives.title}</h2>
            <p className="section-description">{t.objectives.description}</p>
          </div>

          <div className="analysis-grid">
            <div className="analysis-card">
              <div className="analysis-icon">
                <TargetIcon />
              </div>
              <h3 className="analysis-card-title">{t.objectives.general.title}</h3>
              <p className="analysis-card-desc">{t.objectives.general.description}</p>
            </div>

            <div className="analysis-card">
              <div className="analysis-icon">
                <FileTextIcon />
              </div>
              <h3 className="analysis-card-title">{t.objectives.specific.title}</h3>
              <ul className="testing-list">
                {t.objectives.specific.items.map((item, index) => (
                  <li key={index}>{item}</li>
                ))}
              </ul>
            </div>
          </div>
        </div>
      </section>

      <div className="section-separator"></div>

      {/* Proposal Section */}
      <section id="proposal" ref={proposalRef} className={getSectionClass("development-section")}>
        <div className="section-container">
          <div className="section-header">
            <div className="section-badge">{t.proposal.badge}</div>
            <h2 className="section-title">{t.proposal.title}</h2>
            <p className="section-description">{t.proposal.description}</p>
          </div>

          <div className="analysis-grid">
            <div className="analysis-card">
              <div className="analysis-icon">
                <SearchIcon />
              </div>
              <h3 className="analysis-card-title">{t.proposal.context.title}</h3>
              <p className="analysis-card-desc">{t.proposal.context.description}</p>
            </div>

            <div className="analysis-card">
              <div className="analysis-icon">
                <StarIcon />
              </div>
              <h3 className="analysis-card-title">{t.proposal.value.title}</h3>
              <p className="analysis-card-desc">{t.proposal.value.description}</p>
            </div>

            <div className="analysis-card">
              <div className="analysis-icon">
                <LightbulbIcon />
              </div>
              <h3 className="analysis-card-title">{t.proposal.innovations.title}</h3>
              <ul className="testing-list">
                {t.proposal.innovations.items.map((item, index) => (
                  <li key={index}>{item}</li>
                ))}
              </ul>
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
              <div className="conclusion-icon">
                <ThumbsUpIcon />
              </div>
              <h3 className="conclusion-card-title">{t.conclusion.satisfaction.title}</h3>
              <p className="conclusion-card-desc">{t.conclusion.satisfaction.description}</p>
            </div>

            <div className="conclusion-card">
              <div className="conclusion-icon">
                <HeartIcon />
              </div>
              <h3 className="conclusion-card-title">{t.conclusion.impact.title}</h3>
              <p className="conclusion-card-desc">{t.conclusion.impact.description}</p>
            </div>
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

          <h2 className="section-title">{t.download.title}</h2>

          <p className="section-description">{t.download.description}</p>

          {/* Estadísticas */}
          <div className="stats-grid">
            <div className="stat-card">
              <div className="stat-number">{downloadCount}</div>
              <div className="stat-label">{t.download.stats.features}</div>
            </div>
            <div className="stat-card">
              <div className="stat-number">4.9</div>
              <div className="stat-label">{t.download.stats.rating}</div>
            </div>
            <div className="stat-card">
              <div className="stat-number">100%</div>
              <div className="stat-label">Offline</div>
            </div>
          </div>

          <a href="/MyFinance.apk" download className="download-btn">
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
  )
}

export default App
