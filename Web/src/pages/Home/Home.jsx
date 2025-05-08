import React from 'react';
import './Home.css';
import IphoneViewer from './IphoneViewer';

export default function Home() {
  return (
    <div className="home-container">
      <div className="home-content">
        <h1>MyFinance</h1>
        <h2>Tus finanzas sin límites</h2>
        <button className="button-primary">Comienza ahora</button>
      </div>
      <div className="home-canva">
        <IphoneViewer />
      </div>
    </div>
  );
}