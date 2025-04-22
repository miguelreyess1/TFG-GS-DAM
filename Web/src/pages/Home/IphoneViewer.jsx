import React, { Suspense, useRef } from 'react';
import { Canvas, useFrame } from '@react-three/fiber';
import { useGLTF } from '@react-three/drei';

function IphoneModel() {
  const { scene } = useGLTF('iphone.glb');
  const modelRef = useRef();

  const isDragging = useRef(false);
  const previousX = useRef(0);
  const velocity = useRef(0); 
  const friction = 0.95;    

  const handlePointerDown = (e) => {
    isDragging.current = true;
    previousX.current = e.clientX;
  };

  const handlePointerMove = (e) => {
    if (!isDragging.current) return;
    const deltaX = e.clientX - previousX.current;
    velocity.current = deltaX * 0.01; 
    previousX.current = e.clientX;
  };

  const handlePointerUp = () => {
    isDragging.current = false;
  };

  useFrame(() => {
    if (modelRef.current) {
      modelRef.current.rotation.y += velocity.current;

      if (!isDragging.current) {
        velocity.current *= friction;

        if (Math.abs(velocity.current) < 0.0001) {
          velocity.current = 0;
        }
      }
    }
  });

  return (
    <primitive
      ref={modelRef}
      object={scene}
      scale={1.2}
      onPointerDown={handlePointerDown}
      onPointerMove={handlePointerMove}
      onPointerUp={handlePointerUp}
      onPointerOut={handlePointerUp}
    />
  );
}

export default function IphoneViewer() {
  return (
    <Canvas
        className="canva"
        style={{ marginTop: '250px' }}
        camera={{ position: [4, -2, 7], fov: 40 }}
    >
      <ambientLight intensity={1.5} />
      <Suspense fallback={null}>
        <IphoneModel className='iphone'/>
      </Suspense>
    </Canvas>
  );
}
