import axios from "axios";
import React, { useEffect, useState } from "react";

const Profile = () => {
  const [data, setData] = useState(null);
  const token = localStorage.getItem("token");
  console.log("token =", token);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get("http://localhost:8080/user/profile", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        console.log("response user ", response);
        setData(response.data);
      } catch (error) {
        console.error("Error fetching profile data:", error);
      }
    };

    fetchData();
  }, [token]);

  return <>hello - {data?.username}</>;
};

export default Profile;
