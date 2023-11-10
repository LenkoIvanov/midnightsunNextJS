'use client'

import dynamic from 'next/dynamic';
 
const Products = dynamic(() => import('./AllProducts'), { ssr: false }); //disable ssr for now
 
export default function Page() {
  return <Products />;
}