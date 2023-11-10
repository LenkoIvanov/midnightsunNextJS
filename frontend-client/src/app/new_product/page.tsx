'use client' // marks the component as a client-side component
 
import dynamic from 'next/dynamic';
 
const NewProduct = dynamic(() => import('./NewProductForm'), { ssr: false }); //disable ssr for now
 
export default function Page() {
  return <NewProduct />;
}