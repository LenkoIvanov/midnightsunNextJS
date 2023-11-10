'use client'

import dynamic from 'next/dynamic';
 
const ShoppingCart = dynamic(() => import('./ShoppingCart'), { ssr: false }); //disable ssr for now
 
export default function Page() {
  return <ShoppingCart />;
}