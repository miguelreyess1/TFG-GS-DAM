import './Toolbar.css';
import { FaGithub, FaMoon } from 'react-icons/fa';

export default function Toolbar() {
  return (
    <div className="toolbar">
      <div className="toolbar-btn">
        <a 
            href="https://github.com/miguelreyess1/TFG-GS-DAM"
            target="_blank"
            rel="noopener noreferrer">
            <FaGithub />
        </a>
      </div>
      <div className="toolbar-btn">
        <FaMoon />
      </div>
      <div className="toolbar-btn">
        <img
          src="/es.png"
          alt="Idioma"
          className="toolbar-flag"
        />
      </div>
    </div>
  );
}
