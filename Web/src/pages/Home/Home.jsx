import React from 'react';
import './Home.css';
import IphoneViewer from './IphoneViewer';

export default function Home() {
  return (
    <div className="home-container">
      <h1>MyFinance</h1>
      <h2>Tus finanzas sin limites</h2>
      <button className='button-primary'>Comienza ahora</button>
      <IphoneViewer />
      
    </div>
  );
}

