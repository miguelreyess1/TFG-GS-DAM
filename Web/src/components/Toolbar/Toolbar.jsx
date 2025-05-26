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
    </div>
  );
}
