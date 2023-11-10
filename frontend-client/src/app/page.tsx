'use client' // marks the component as a client-side component
 
import dynamic from 'next/dynamic';
 
const Home = dynamic(() => import('../pages/HomePage/Home'), { ssr: false }); //disable ssr for now
 
export default function Page() {
  return <Home />;
}