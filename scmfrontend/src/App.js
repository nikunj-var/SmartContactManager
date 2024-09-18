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
import Sidebar from "./components/sidebar";
import Profile from "./containers/profile";
import AddContact from "./containers/addContact";
import ContactList from "./containers/contactList";

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
  const width = isAuthenticated ? "w-[calc(100%-256px)]" : "w-full";
  const padding = isAuthenticated ? "ml-[256px]" : "ml-2";

  return (
    <>
      <ThemeContext.Provider value={{ theme, toggleTheme }}>
        <div className={`app ${theme}`}>
          <div className="navbar">
            <NavbarLayout />
            <ProtectedRoute element={<Sidebar />} />
          </div>
          <div className={`main-content ${width} ${padding} mx-auto`}>
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
      {
        path: "/profile",
        element: <ProtectedRoute element={<Profile />} />,
      },
      {
        path: "/addContact",
        element: <ProtectedRoute element={<AddContact />} />,
      },
      {
        path: "/contactList",
        element: <ProtectedRoute element={<ContactList />} />,
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
