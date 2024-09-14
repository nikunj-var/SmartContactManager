// HomePage.js
import React, { useEffect } from "react";
import Profile from "../profile";

const HomePage = () => {
  useEffect(() => {
    const token = new URLSearchParams(window.location.search).get("token");
    if (token) {
      localStorage.setItem("token", token);
    }
  }, []);

  return (
    <div className="text-center">
      <Profile />
    </div>
  );
};

export default HomePage;
