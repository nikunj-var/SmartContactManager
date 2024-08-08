import axios from "axios";
import React, { useEffect, useState } from "react";

const Profile = () => {
  const { data, setData } = useState();
  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get("http://localhost:8080/user/profile");
        console.log(response);
        setData(response.data);
      } catch (error) {
        console.error("Error fetching profile data:", error);
      }
    };

    fetchData();
  }, []);

  return <>hello - {data}</>;
};

export default Profile;
