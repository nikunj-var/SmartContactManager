import API from "../constants/api";
import { toast } from "react-toastify";

export const login = async (credentials) => {
  try {
    const response = await API.post("/auth/loginuser", credentials, {
      headers: {
        "Content-Type": "application/json",
      },
      maxRedirects: 0, // Prevent Axios from following redirects
      validateStatus: function (status) {
        return status >= 200 && status < 400; // Accepts only status codes less than 400
      },
    });

    if (response.status === 302) {
      const redirectUrl = response.headers["location"];
      toast.info(`Redirecting to ${redirectUrl}`);
      // Optionally, handle the redirect in your application
    }

    return response;
  } catch (err) {
    const errorMessage =
      err?.response?.data?.message || "Login failed. Please try again.";
    toast.error(errorMessage);
    return err?.response;
  }
};

export const logout = async () => {
  try {
    const response = await API.post("/auth/logout");
    if (response?.status === 200) {
      toast.success(response?.data);
    } else {
      toast.error(response?.data);
    }
    return response;
  } catch (err) {
    toast.error(err?.response);
    return err?.response;
  }
};
