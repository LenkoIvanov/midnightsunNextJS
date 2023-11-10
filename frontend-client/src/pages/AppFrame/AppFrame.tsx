'use client'

import { Navbar } from "../../shared_components/Fragments/Navbar/Navbar";
import Footer from "../../shared_components/Fragments/Footer/Footer";
import { Auth0Provider } from "@auth0/auth0-react";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import "primereact/resources/themes/saga-blue/theme.css";
import "primereact/resources/primereact.min.css";
import "primeicons/primeicons.css";

const queryClient = new QueryClient();

const AppFrame = ({children} : {
  children: React.ReactNode
}) => {
  return (
    <>
      <Auth0Provider
      domain={`${process.env.NEXT_PUBLIC_DOMAIN}`}
      clientId={`${process.env.NEXT_PUBLIC_CLIENTID}`}
      authorizationParams={{
        audience: process.env.NEXT_PUBLIC_AUDIENCE,
        redirect_uri: "/",
      }}
    >
      <QueryClientProvider client={queryClient}>
        <Navbar />
         {children}
        <Footer/>
      </QueryClientProvider>
    </Auth0Provider>
    </>
  );
};

export default AppFrame;
