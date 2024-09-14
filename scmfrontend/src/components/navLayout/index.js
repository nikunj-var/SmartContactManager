import React, { useContext } from "react";
import { Link } from "react-router-dom";
import { AuthContext, ThemeContext } from "../../App";
import { MdOutlineLightMode } from "react-icons/md";
import { logout } from "../../services/AuthService";
import "./index.css";

const NavbarLayout = () => {
  const { theme, toggleTheme } = useContext(ThemeContext);
  const { isAuthenticated, setIsAuthenticated } = useContext(AuthContext);
  const user = localStorage.getItem("name");
  const navbarWidth = isAuthenticated ? "w-[calc(100%-256px)]" : "w-full";
  const navbarPadding = isAuthenticated ? "ml-[256px]" : "";
  return (
    <div className="navbar">
      <nav
        class="bg-white dark:bg-gray-800 fixed right-0  border-gray-200  border-2 "
        className={`nav ${theme} ${navbarWidth} ${navbarPadding}`}
      >
        <div class="max-w-screen-xl flex flex-wrap items-center justify-between mx-auto p-4">
          <div class="flex items-center space-x-3 rtl:space-x-reverse pl-2 bg-transparent">
            <img
              src="data:image/png;base64,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"
              class="h-8 bg-transparent"
              alt="Flowbite Logo"
            />
            <span class="self-center text-2xl font-semibold whitespace-nowrap dark:text-white">
              LINK MINDER
            </span>
          </div>
          <div class="flex gap-2 md:order-2 space-x-3 md:space-x-0 rtl:space-x-reverse">
            <div class="flex gap-2 md:order-2 space-x-3 md:space-x-0 rtl:space-x-reverse">
              {" "}
              {!isAuthenticated ? (
                <Link
                  to={"/login"}
                  type="button"
                  className="text-white bg-gray-700 hover:bg-gray-800 focus:ring-4 focus:outline-none focus:ring-gray-300 font-medium rounded-lg text-sm px-4 py-2 text-center dark:bg-gray-600 dark:hover:bg-gray-700 dark:focus:ring-gray-800"
                >
                  Login
                </Link>
              ) : (
                <>
                  {" "}
                  <Link
                    to={"/login"}
                    type="button"
                    className="text-white bg-gray-700 hover:bg-gray-800 focus:ring-4 focus:outline-none focus:ring-gray-300 font-medium rounded-lg text-sm px-4 py-2 text-center dark:bg-gray-600 dark:hover:bg-gray-700 dark:focus:ring-gray-800"
                    onClick={async () => {
                      const response = await logout();
                      if (response?.status === 200) {
                        setIsAuthenticated(false);
                      }
                    }}
                  >
                    Logout
                  </Link>
                  <p>{user}</p>
                </>
              )}
              {!isAuthenticated && (
                <Link
                  to={"/register"}
                  type="button"
                  className="text-white bg-gray-700 hover:bg-gray-800 focus:ring-4 focus:outline-none focus:ring-gray-300 font-medium rounded-lg text-sm px-4 py-2 text-center dark:bg-gray-600 dark:hover:bg-gray-700 dark:focus:ring-gray-800 md:order-2 space-x-2"
                >
                  Register
                </Link>
              )}
            </div>

            <div>
              <button
                className="text-black font-medium rounded-lg text-sm px-4 py-2 text-center dark:text-white  flex align-middle"
                onClick={toggleTheme}
              >
                <MdOutlineLightMode className="h-5 w-5" />
              </button>
            </div>

            <button
              data-collapse-toggle="navbar-cta"
              type="button"
              class="inline-flex items-center p-2 w-10 h-10 justify-center text-sm text-gray-500 rounded-lg md:hidden hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200 dark:text-gray-400 dark:hover:bg-gray-700 dark:focus:ring-gray-600"
              aria-controls="navbar-cta"
              aria-expanded="false"
            >
              <span class="sr-only">Open main menu</span>
              <svg
                class="w-5 h-5"
                aria-hidden="true"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 17 14"
              >
                <path
                  stroke="currentColor"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M1 1h15M1 7h15M1 13h15"
                />
              </svg>
            </button>
          </div>
          <div
            class="items-center justify-between hidden w-full md:flex md:w-auto md:order-1"
            id="navbar-sticky"
          >
            <ul class="flex flex-col p-4 md:p-0 mt-4 font-medium border border-gray-100 rounded-lg bg-transparent md:space-x-8 rtl:space-x-reverse md:flex-row md:mt-0 md:border-0  dark:border-gray-700">
              <li>
                <Link
                  to={"/"}
                  class="block py-2 px-3 text-white  rounded md:bg-transparent md:text-blue-700 md:p-0 "
                  aria-current="page"
                >
                  Home
                </Link>
              </li>
              <li>
                <Link
                  to={"/about"}
                  class="block py-2 px-3 text-gray-900 rounded hover:bg-gray-100 md:hover:bg-transparent md:hover:text-blue-700 md:p-0 md:dark:hover:text-blue-500 dark:text-white dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700"
                >
                  About
                </Link>
              </li>
              <li>
                <Link
                  to={"/services"}
                  href="#"
                  class="block py-2 px-3 text-gray-900 rounded hover:bg-gray-100 md:hover:bg-transparent md:hover:text-blue-700 md:p-0 md:dark:hover:text-blue-500 dark:text-white dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700"
                >
                  Services
                </Link>
              </li>
            </ul>
          </div>
        </div>
      </nav>
    </div>
  );
};

export default NavbarLayout;
