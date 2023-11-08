'use client'

import dynamic from 'next/dynamic';

const ContactPage = dynamic(() => import('./ContactPage'), { ssr: false }); //disable ssr for now

export default function Page() {
    return <ContactPage />;
}