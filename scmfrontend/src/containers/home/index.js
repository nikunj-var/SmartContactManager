import React from "react";
import Profile from "../profile";
import { useEffect } from "react";
const HomePage = () => {
  useEffect(() => {
    const urlParams = new URLSearchParams(window.location.search);
    const token = urlParams.get("token");
    localStorage.setItem("token", token);
  }, []);

  return (
    <div>
      <div>HomePage</div>
      <Profile />
      <div>HomePage</div>
    </div>
  );
};

export default HomePage;
