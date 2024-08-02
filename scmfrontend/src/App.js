import "./App.css";
import { Outlet, createBrowserRouter, RouterProvider } from "react-router-dom";
import Login from "./containers/login";
import HomePage from "./containers/home";
import About from "./containers/about";
import Register from "./containers/register";
import NavbarLayout from "./components/navLayout";
import { createContext, useState } from "react";
import Services from "./containers/services";
import "react-toastify/dist/ReactToastify.css";
import { ToastContainer } from "react-toastify";

export const ThemeContext = createContext();

const AppLayout = () => {
  const [theme, setTheme] = useState("light");

  const toggleTheme = () => {
    setTheme((prevTheme) => (prevTheme === "light" ? "dark" : "light"));
  };
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
const appRouter = createBrowserRouter([
  {
    path: "/",
    element: <AppLayout />,
    children: [
      {
        path: "/",
        element: <HomePage />,
      },
      {
        path: "/about",
        element: <About />,
      },
      {
        path: "/services",
        element: <Services />,
      },
      { path: "/login", element: <Login /> },
      { path: "/register", element: <Register /> },
    ],
  },
]);

const App = () => {
  return (
    <>
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
    </>
  );
};
export default App;
