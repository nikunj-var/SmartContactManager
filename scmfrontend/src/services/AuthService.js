import API from "../constants/api";
import { toast } from "react-toastify";

export const login = async (credentials) => {
  try {
    const response = await API.post("/auth/loginuser", credentials, {
      headers: {
        "Content-Type": "application/json",
      },
    });

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
