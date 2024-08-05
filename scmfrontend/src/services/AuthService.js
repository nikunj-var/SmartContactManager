import API from "../constants/api";
import { toast } from "react-toastify";

export const login = async (credentials) => {
  try {
    const response = await API.post("/auth/login", credentials, {
      headers: {
        "Content-Type": "application/json",
      },
    });
    return response;
  } catch (err) {
    toast.error(err);
    return err?.response;
  }
};

export const logout = async () => {
  try {
    await API.post("/auth/logout");
  } catch (err) {
    toast.error(err);
  }
};
