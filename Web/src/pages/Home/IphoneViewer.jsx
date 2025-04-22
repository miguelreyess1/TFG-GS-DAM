import React, { Suspense, useRef } from 'react';
import { Canvas, useFrame } from '@react-three/fiber';
import { useGLTF } from '@react-three/drei';

function IphoneModel({posicion}) {
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
      scale={1.0}
      onPointerDown={handlePointerDown}
      onPointerMove={handlePointerMove}
      onPointerUp={handlePointerUp}
      onPointerOut={handlePointerUp}
      position={posicion}
    />
  );
}

export default function IphoneViewer() {
  return (
    <Canvas
        className="canva"
        camera={{ position: [1.5, 0, 4], fov: 50}}
    >
      <ambientLight intensity={1.5} />
      <Suspense fallback={null}>
        <IphoneModel 
          posicion={[0, -1.2, 0]}
        />
      </Suspense>
    </Canvas>
  );
}
