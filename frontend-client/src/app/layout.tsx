import type { Metadata } from 'next'
 
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
          <div id="root">{children}</div>
        </body>
      </html>
      
    )
  }