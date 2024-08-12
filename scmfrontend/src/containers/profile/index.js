// Profile.js
import axios from "axios";
import React, { useEffect, useState } from "react";
import API from "../../constants/api";

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
        setData(response.data);
      } catch (error) {
        console.error("Error fetching profile data:", error);
      }
    };

    fetchData();
  }, [token]);

  return (
    <>
      hello - {data?.username}
      <p>email - {data?.email}</p>
    </>
  );
};

export default Profile;
