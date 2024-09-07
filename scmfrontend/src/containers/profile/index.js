// Profile.js
import axios from "axios";
import React, { useEffect, useState } from "react";
import API from "../../constants/api";
import Sidebar from "../../components/sidebar";

const Profile = () => {
  const [data, setData] = useState(null);
  const token = localStorage.getItem("token");

  useEffect(() => {
    const fetchData = async () => {
      try {
        if (!token) {
          throw new Error("No token found");
        }
        const response = await API.get("http://localhost:8080/user/profile", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        localStorage.setItem("name", response?.data?.username);
        localStorage.setItem("email", response?.data?.email);
      } catch (error) {
        console.error("Error fetching profile data:", error);
      }
    };

    fetchData();
  }, [token]);

  return <>This is profile page</>;
};

export default Profile;
