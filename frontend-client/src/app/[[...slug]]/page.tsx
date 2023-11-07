'use client' // marks the component as a client-side component
 
import dynamic from 'next/dynamic';
import '../../index.scss';
 
const App = dynamic(() => import('../../App'), { ssr: false }); //disable ssr for now
 
export default function Page() {
  return <App />;
}