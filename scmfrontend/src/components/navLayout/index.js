import React, { useContext } from "react";
import { Link } from "react-router-dom";
import { AuthContext, ThemeContext } from "../../App";
import { RiContactsBook3Line } from "react-icons/ri";
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
        class="bg-gray-50 dark:bg-gray-800 fixed right-0  border-gray-200  border-2 "
        className={`nav ${theme} ${navbarWidth} ${navbarPadding}`}
      >
        <div class="max-w-screen-xl flex flex-wrap items-center justify-between mx-auto p-4">
          <div class="flex items-center space-x-3 rtl:space-x-reverse pl-2 bg-transparent">
            <span class="self-center text-2xl font-semibold italic whitespace-nowrap dark:text-white flex gap-2 items-center">
              <RiContactsBook3Line /> Link Minder
            </span>
          </div>
          <div class="flex gap-2 md:order-2 space-x-3 md:space-x-0 rtl:space-x-reverse">
            <div class="flex gap-2 md:order-2 space-x-3 md:space-x-0 rtl:space-x-reverse">
              {" "}
              {!isAuthenticated && (
                <Link
                  to={"/login"}
                  type="button"
                  className="text-white bg-gray-700 hover:bg-gray-800 focus:ring-4 focus:outline-none focus:ring-gray-300 font-medium rounded-lg text-sm px-4 py-2 text-center dark:bg-gray-600 dark:hover:bg-gray-700 dark:focus:ring-gray-800"
                >
                  Login
                </Link>
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
            {/* <ul class="flex flex-col p-4 md:p-0 mt-4 font-medium border border-gray-100 rounded-lg bg-transparent md:space-x-8 rtl:space-x-reverse md:flex-row md:mt-0 md:border-0  dark:border-gray-700">
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
            </ul> */}
          </div>
        </div>
      </nav>
    </div>
  );
};

export default NavbarLayout;
