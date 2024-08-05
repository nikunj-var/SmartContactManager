import React, { useContext } from "react";
import { useState } from "react";
import axios from "axios";
import { toast } from "react-toastify";
import { login } from "../../services/AuthService";
import { AuthContext } from "../../App";
import { Navigate } from "react-router-dom";

const Login = () => {
  const { setIsAuthenticated } = useContext(AuthContext);
  const [redirect, setRedirect] = useState(false);

  const [user, setUser] = useState({
    username: "",
    password: "",
  });

  const { username, password } = user;
  const onInputChange = (e) => {
    setUser({ ...user, [e?.target?.name]: e?.target?.value });
  };

  const handleSubmit = async (e) => {
    e?.preventDefault();
    try {
      const response = await login({ username, password });
      console.log(response);
      if (response.status === 200) {
        setIsAuthenticated(true);
        toast.success("Login Successfully");
        setRedirect(true);
      } else {
        setIsAuthenticated(false);
        toast.error("Invalid credentials");
      }
    } catch (err) {
      setIsAuthenticated(false);
      toast.error(err);
    }

    setUser({ username: "", password: "" });
  };

  if (redirect) {
    return <Navigate to="/" />;
  }
  return (
    <div class="block max-w-xl mx-auto my-20 p-10 bg-white border border-gray-200 rounded-lg shadow  dark:bg-gray-800 dark:border-white border-t-4 border-gray-600">
      <h1 className="text-center text-3xl font-bold p-8">Login Here!</h1>
      <form
        class="max-w-md mx-auto flex flex-col gap-10"
        onSubmit={handleSubmit}
      >
        <div>
          <div class="relative z-0 w-full mb-5 group">
            <input
              type="email"
              name="username"
              id="email"
              value={username}
              class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
              placeholder=" "
              onChange={(e) => onInputChange(e)}
              required
            />
            <label
              for="email"
              class="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 rtl:peer-focus:left-auto peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
            >
              Email address
            </label>
          </div>{" "}
          <div class="relative z-0 w-full mb-5 group">
            <input
              type="password"
              name="password"
              id="password"
              value={password}
              class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
              placeholder=" "
              required
              onChange={(e) => onInputChange(e)}
            />
            <label
              for="password"
              class="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
            >
              Password
            </label>
          </div>
        </div>

        <div class=" text-center">
          <button
            type="submit"
            class="text-white bg-gray-700 hover:bg-gray-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-gray-500 dark:hover:bg-gray-600 dark:focus:ring-gray-300"
          >
            Login
          </button>
        </div>
      </form>
    </div>
  );
};

export default Login;
