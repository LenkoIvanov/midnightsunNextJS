import type { Metadata } from 'next'
import AppFrame from '../pages/AppFrame/AppFrame'
import "../index.scss";
 
// Replaces the <head>
export const metadata: Metadata = {
  title: 'Ecart',
  description: 'An e-commerce playground app',
}

export default function RootLayout({
    children,
  }: {
    children: React.ReactNode
  }) {
    return (
      <html lang="en">
        <body>
          <div id="root">
            <AppFrame children={children} />
          </div>
        </body>
      </html>
      
    )
  }