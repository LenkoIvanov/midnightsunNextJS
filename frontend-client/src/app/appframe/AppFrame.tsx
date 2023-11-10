'use client'

import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { Navbar } from "../../shared_components/Fragments/Navbar/Navbar";
import Footer from "../../shared_components/Fragments/Footer/Footer";
import "primereact/resources/themes/saga-blue/theme.css";
import "primereact/resources/primereact.min.css";
import "primeicons/primeicons.css";
import { UserProvider } from "@auth0/nextjs-auth0/client";

const queryClient = new QueryClient();

const AppFrame = ({children} : {
  children: React.ReactNode
}) => {
  return (
    <>
    <UserProvider>
      <QueryClientProvider client={queryClient}>
        <Navbar />
         {children}
        <Footer/>
      </QueryClientProvider>
    </UserProvider>
    </>
  );
};

export default AppFrame;
