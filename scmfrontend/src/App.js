import "./App.css";
import {
  Outlet,
  createBrowserRouter,
  RouterProvider,
  Navigate,
} from "react-router-dom";
import Login from "./containers/login";
import HomePage from "./containers/home";
import About from "./containers/about";
import Register from "./containers/register";
import NavbarLayout from "./components/navLayout";
import { createContext, useContext, useState } from "react";
import Services from "./containers/services";
import "react-toastify/dist/ReactToastify.css";
import { useEffect } from "react";

import { ToastContainer } from "react-toastify";

export const ThemeContext = createContext();
export const AuthContext = createContext();

const AppLayout = () => {
  const [theme, setTheme] = useState("light");
  const { isAuthenticated, setIsAuthenticated } = useContext(AuthContext);

  const toggleTheme = () => {
    setTheme((prevTheme) => (prevTheme === "light" ? "dark" : "light"));
  };

  useEffect(() => {
    const urlParams = new URLSearchParams(window.location.search);

    if (urlParams.get("isauthenticated") === "true") {
      setIsAuthenticated(true);
    }
  }, [setIsAuthenticated]);

  return (
    <>
      <ThemeContext.Provider value={{ theme, toggleTheme }}>
        <div className={`app ${theme}`}>
          <div className="navbar">
            <NavbarLayout />
          </div>
          <div className="main-content">
            <Outlet />
          </div>
        </div>
      </ThemeContext.Provider>
    </>
  );
};

const ProtectedRoute = ({ element }) => {
  const { isAuthenticated } = useContext(AuthContext);
  return isAuthenticated ? element : <Navigate to="/login" />;
};

const appRouter = createBrowserRouter([
  {
    path: "/",
    element: <AppLayout />,
    children: [
      {
        path: "/",
        element: <ProtectedRoute element={<HomePage />} />,
      },
      {
        path: "/about",
        element: <ProtectedRoute element={<About />} />,
      },
      {
        path: "/services",
        element: <ProtectedRoute element={<Services />} />,
      },
      { path: "/login", element: <Login /> },
      { path: "/register", element: <Register /> },
    ],
  },
]);

const App = () => {
  const urlParams = new URLSearchParams(window.location.search);
  const initialAuthState = urlParams.get("isauthenticated") === "true";

  const [isAuthenticated, setIsAuthenticated] = useState(initialAuthState);
  return (
    <>
      <AuthContext.Provider value={{ isAuthenticated, setIsAuthenticated }}>
        {" "}
        <RouterProvider router={appRouter}></RouterProvider>
        <ToastContainer
          position="bottom-right"
          autoClose={2000}
          hideProgressBar={false}
          newestOnTop={false}
          closeOnClick
          rtl={false}
          pauseOnFocusLoss
          draggable={false}
          pauseOnHover
        />
      </AuthContext.Provider>
    </>
  );
};
export default App;
