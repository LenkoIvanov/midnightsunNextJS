import { RouterProvider, createBrowserRouter } from "react-router-dom";
import { ErrorComponent } from "./shared_components/ErrorComponent/ErrorComponent";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { useEffect } from "react";
import { Auth0Provider, useAuth0 } from "@auth0/auth0-react";
import LoadingOverlay from "./shared_components/LoadingOverlay/LoadingOverlay";
import ContactPage from "./pages/ContactPage/ContactPage";
import Home from "./pages/HomePage/Home";
import { AllProducts } from "./pages/ProductsPage/AllProducts";
import { NewProductForm } from "./pages/NewProductPage/NewProductForm";
import ShoppingCart from "./pages/ShoppingCartPage/ShoppingCart";
import AppFrame from "./pages/AppFrame/AppFrame";

const router = createBrowserRouter([
  {
    path: "/",
    element: <AppFrame />,
    errorElement: <ErrorComponent isPathRelated={true} />,
    children: [
      {
        path: "/",
        element: <Home />,
        errorElement: <ErrorComponent isPathRelated={true} />,
      },
      {
        path: "/products",
        element: <AllProducts />,
        errorElement: <ErrorComponent isPathRelated={false} />,
      },
      {
        path: "/addProduct",
        element: <NewProductForm />,
        errorElement: <ErrorComponent isPathRelated={false} />,
      },
      {
        path: "/contacts",
        element: <ContactPage />,
      },
      {
        path: "/shoppingCart",
        element: <ShoppingCart />,
        errorElement: <ErrorComponent isPathRelated={false} />,
      },
      {
        path: "/userPage",
        element: <div>Account</div>,
      },
    ],
  },
]);

const queryClient = new QueryClient();

function App() {
  const { getAccessTokenSilently, isAuthenticated } = useAuth0();

  useEffect(() => {
    const fetchAccessToken = async () => {
      if (!isAuthenticated) return;
      const accessToken = await getAccessTokenSilently();
      sessionStorage.setItem("accessToken", accessToken);
    };
    fetchAccessToken();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [isAuthenticated]);

  return (
    <div>
    <Auth0Provider
      domain={`${process.env.NEXT_DOMAIN}`}
      clientId={`${process.env.NEXT_CLIENTID}`}
      authorizationParams={{
        audience: process.env.NEXT_AUDIENCE,
        redirect_uri: window.location.origin,
      }}
    >
      <QueryClientProvider client={queryClient}>
        <LoadingOverlay>
          <RouterProvider router={router} />
        </LoadingOverlay>
      </QueryClientProvider>
      </Auth0Provider>
    </div>
  );
}

export default App;
