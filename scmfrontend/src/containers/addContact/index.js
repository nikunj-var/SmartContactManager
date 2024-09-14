import React from "react";
import { toast } from "react-toastify";
import axios from "axios";
import { useState } from "react";
const AddContact = () => {
  const [newUser, setNewUser] = useState({
    name: "",
    email: "",
    phoneNumber: "",
    description: "",
    address: "",
    file: "",
    linkedIn: "",
    favourite: "",
  });

  const {
    name,
    email,
    phoneNumber,
    file,
    description,
    address,
    favourite,
    linkedIn,
  } = newUser;

  const onInputChange = (e) => {
    console.log(e?.target?.name);
    const { name, value, type, checked } = e.target;

    // setNewUser({ ...newUser, [e?.target?.name]: e?.target?.value });
    if (type === "file") {
      setNewUser({ ...newUser, [e?.target?.name]: e?.target?.files?.[0] }); // File input handling
    } else {
      setNewUser({ ...newUser, [e?.target?.name]: e?.target?.value });
    }
  };

  const handleSubmit = async (e) => {
    e?.preventDefault();
    const token = localStorage.getItem("token");

    if (!token) {
      console.error("Token not found in localStorage");
      return;
    }

    try {
      const formData = new FormData();
      Object.keys(newUser).forEach((key) => {
        formData.append(key, newUser[key]);
      });

      if (newUser.file) {
        formData.append("file", newUser.file); 
      }

      const response = await axios
        .post("http://localhost:8080/contact/save-contact", formData, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
        .then((res) => {
          toast.success(res?.data);
        });
      console.log(response);
    } catch (err) {
      toast.error(err?.message);
    }
  };
  return (
    <div>
      <div class="w-8/12 mt-5 m-auto">
        <div class="block w-full p-6 bg-white border text-center border-gray-200 rounded-lg shadow  dark:bg-gray-800 dark:border-gray-700 ">
          <h1 class="text-xl font-semibold ">Add New Contact</h1>

          <form
            class="mt-8"
            onSubmit={handleSubmit}
            encType="multipart/form-data"
          >
            <div class="mb-1">
              {" "}
              <label
                for="input-group-1"
                class="block mb-1 text-sm font-medium text-gray-900 dark:text-white text-left"
              >
                Contact Name
              </label>
              <div class="relative mb-6">
                <div class="absolute inset-y-0 start-0 flex items-center ps-3.5 pointer-events-none">
                  <svg
                    class="w-4 h-4 text-gray-500 dark:text-gray-400"
                    aria-hidden="true"
                    xmlns="http://www.w3.org/2000/svg"
                    fill="currentColor"
                    viewBox="0 0 20 16"
                  >
                    <path d="m10.036 8.278 9.258-7.79A1.979 1.979 0 0 0 18 0H2A1.987 1.987 0 0 0 .641.541l9.395 7.737Z" />
                    <path d="M11.241 9.817c-.36.275-.801.425-1.255.427-.428 0-.845-.138-1.187-.395L0 2.6V14a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V2.5l-8.759 7.317Z" />
                  </svg>
                </div>
                <input
                  name="name"
                  type="text"
                  value={name}
                  id="input-group-1"
                  onChange={(e) => {
                    onInputChange(e);
                  }}
                  class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full ps-10 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                  placeholder="John Barden"
                />
              </div>
            </div>
            <div class="mb-1">
              {" "}
              <label
                for="input-group-1"
                class="block mb-1 text-sm font-medium text-gray-900 dark:text-white text-left"
              >
                Contact Email
              </label>
              <div class="relative mb-6">
                <div class="absolute inset-y-0 start-0 flex items-center ps-3.5 pointer-events-none">
                  <svg
                    class="w-4 h-4 text-gray-500 dark:text-gray-400"
                    aria-hidden="true"
                    xmlns="http://www.w3.org/2000/svg"
                    fill="currentColor"
                    viewBox="0 0 20 16"
                  >
                    <path d="m10.036 8.278 9.258-7.79A1.979 1.979 0 0 0 18 0H2A1.987 1.987 0 0 0 .641.541l9.395 7.737Z" />
                    <path d="M11.241 9.817c-.36.275-.801.425-1.255.427-.428 0-.845-.138-1.187-.395L0 2.6V14a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V2.5l-8.759 7.317Z" />
                  </svg>
                </div>
                <input
                  name="email"
                  type="text"
                  value={email}
                  onChange={(e) => onInputChange(e)}
                  id="input-group-1"
                  class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full ps-10 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                  placeholder="name@flowbite.com"
                />
              </div>
            </div>
            <div class="mb-1">
              {" "}
              <label
                for="input-group-1"
                class="block mb-1 text-sm font-medium text-gray-900 dark:text-white text-left"
              >
                Contact Phone
              </label>
              <div class="relative mb-6">
                <div class="absolute inset-y-0 start-0 flex items-center ps-3.5 pointer-events-none">
                  <svg
                    class="w-4 h-4 text-gray-500 dark:text-gray-400"
                    aria-hidden="true"
                    xmlns="http://www.w3.org/2000/svg"
                    fill="currentColor"
                    viewBox="0 0 20 16"
                  >
                    <path d="m10.036 8.278 9.258-7.79A1.979 1.979 0 0 0 18 0H2A1.987 1.987 0 0 0 .641.541l9.395 7.737Z" />
                    <path d="M11.241 9.817c-.36.275-.801.425-1.255.427-.428 0-.845-.138-1.187-.395L0 2.6V14a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V2.5l-8.759 7.317Z" />
                  </svg>
                </div>
                <input
                  name="phoneNumber"
                  value={phoneNumber}
                  type="number"
                  id="input-group-1"
                  onChange={(e) => onInputChange(e)}
                  class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full ps-10 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                  placeholder="+91 7300538003"
                />
              </div>
            </div>
            <div class="mb-1">
              {" "}
              <label
                for="input-group-1"
                class="block mb-1 text-sm font-medium text-gray-900 dark:text-white text-left"
              >
                Contact Address
              </label>
              <div class="relative mb-6">
                <textarea
                  name="address"
                  id="message"
                  value={address}
                  rows="4"
                  onChange={(e) => onInputChange(e)}
                  class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                  placeholder="Write contact address here..."
                ></textarea>
              </div>
            </div>
            <div class="mb-1">
              {" "}
              <label
                for="input-group-1"
                class="block mb-1 text-sm font-medium text-gray-900 dark:text-white text-left"
              >
                Contact Description
              </label>
              <div class="relative mb-6">
                <textarea
                  name="description"
                  id="message"
                  rows="4"
                  value={description}
                  onChange={(e) => onInputChange(e)}
                  class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                  placeholder="Write something about contact"
                ></textarea>
              </div>
            </div>
            <div class="flex">
              <div class="w-full">
                <div class="relative mb-6">
                  <input
                    name="linkedIn"
                    value={linkedIn}
                    type="text"
                    onChange={(e) => onInputChange(e)}
                    id="input-group-1"
                    class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full ps-10 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                    placeholder="LinkedIn Link"
                  />
                </div>
              </div>
              <div class="w-full">
                <div class="relative mb-6">
                  <input
                    type="text"
                    id="input-group-1"
                    onChange={(e) => onInputChange(e)}
                    class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full ps-10 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                    placeholder="websiteLink"
                  />
                </div>
              </div>
            </div>

            <div class=" mb-4">
              <label
                class="block mb-1 text-sm text-left font-medium text-gray-900 dark:text-white"
                for="file_input"
              >
                Upload file
              </label>
              <input
                name="file"
                onChange={(e) => onInputChange(e)}
                class="block w-full text-sm text-gray-900 border border-gray-300 rounded-lg cursor-pointer bg-gray-50 dark:text-gray-400 focus:outline-none dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400"
                id="file_input"
                type="file"
              />
            </div>
            <div>
              <div class="flex items-center mb-4">
                <input
                  name="favourite"
                  id="default-checkbox"
                  type="checkbox"
                  value={(e) => onInputChange(e)}
                  class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded dark:ring-offset-gray-800 dark:bg-gray-700 dark:border-gray-600"
                />
                <label
                  for="default-checkbox"
                  class="ms-2 text-sm font-medium text-gray-900 dark:text-gray-300"
                >
                  Make this Contact Favourite
                </label>
              </div>
            </div>
            <div class="button-container text-center">
              <button
                class="px-3 py-2 rounded bg-black text-white dark:bg-white dark:text-black"
                type="submit"
              >
                Add Contact
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default AddContact;
