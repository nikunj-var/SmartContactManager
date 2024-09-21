import React, { useEffect, useState } from "react";
import debounce from "lodash/debounce";
import axios from "axios";
import { toast } from "react-toastify";

const ContactList = () => {
  const [data, setData] = useState([]);
  const token = localStorage.getItem("token");

  const [size, setSize] = useState(3);
  const [page, setPage] = useState(0);
  const [totalPage, setTotalPage] = useState(0);
  const [loading, setLoading] = useState(false);
  const [searchTerm, setSearchTerm] = useState("");
  const [isSearching, setIsSearching] = useState(false);

  useEffect(() => {
    if (isSearching) return;
    const fetchData = async () => {
      setLoading(true);
      try {
        if (!token) {
          throw new Error("No token found");
        }
        const response = await axios.get(
          `http://localhost:8080/contact/getAll?page=${page}&size=${size}&sortBy=${"name"}&direction=${"asc"}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
        console.log(response);
        console.log("Data is array:", Array.isArray(response.data));
        setData(response?.data?.content);
        setTotalPage(response?.data?.totalPages);
        setLoading(false);
      } catch (error) {
        console.error("Error fetching profile data:", error);
      }
    };
    fetchData();
  }, [token, page, isSearching]);

  const handlePrevious = () => {
    if (page > 0) {
      setPage((prev) => prev - 1);
    }
  };

  const handleNext = () => {
    if (page < totalPage - 1) {
      setPage((prev) => prev + 1);
    }
  };

  const debouncedSearch = debounce(async (searchQuery) => {
    if (!searchQuery) {
      setIsSearching(false); // Reset search mode when query is empty
      return;
    }

    setLoading(true);
    setPage(0);
    try {
      const response = await axios.get(
        `http://localhost:8080/contact/search?page=${page}&size=${size}&sortBy=${"name"}&direction=${"asc"}&q=${searchQuery}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      console.log("respons", response);
      setData(response?.data?.content);
      setTotalPage(response?.data?.totalPages);
      setLoading(false);
    } catch (e) {
      toast.error("Error while searching contacts.");
      setLoading(false);
    }
  }, 300);

  const handleSearch = (e) => {
    const searchValue = e.target.value;
    setSearchTerm(searchValue);
    setIsSearching(!!searchValue); // Set search mode if search value is non-empty
    debouncedSearch(searchValue);
  };

  const image =
    "https://cdn1.iconfinder.com/data/icons/app-user-interface-glyph/64/user_man_user_interface_app_person-512.png";

  return (
    <div>
      <p className="text-center p-2 text-2xl underline ">All Contacts List</p>
      <div className="relative overflow-x-auto border shadow-md sm:rounded-lg m-5">
        <div className="flex items-center justify-end flex-column flex-wrap md:flex-row space-y-4 md:space-y-0 pb-4 bg-white dark:bg-gray-900 p-2">
          <label htmlFor="table-search" className="sr-only">
            Search
          </label>
          <div className="relative ">
            <div className="absolute inset-y-0 rtl:inset-r-0 start-0 flex items-center ps-3 pointer-events-none">
              <svg
                className="w-4 h-4 text-gray-500 dark:text-gray-400"
                aria-hidden="true"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 20 20"
              >
                <path
                  stroke="currentColor"
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth="2"
                  d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"
                />
              </svg>
            </div>
            <input
              type="text"
              id="table-search-users"
              className="block p-2 ps-10 text-sm text-gray-900 border border-gray-300 rounded-lg w-80 bg-gray-50 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              placeholder="Search user by name"
              onChange={handleSearch}
            />
          </div>
        </div>
        <table className="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
          <thead className="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400 ">
            <tr>
              <th scope="col" className="px-6 py-3">
                Name
              </th>
              <th scope="col" className="px-6 py-3">
                Phone
              </th>
              <th scope="col" className="px-6 py-3">
                Address
              </th>
              <th scope="col" className="px-6 py-3">
                Action
              </th>
            </tr>
          </thead>

          <tbody>
            {data?.map((userData) => (
              <tr className="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                <th
                  scope="row"
                  className="flex items-center px-6 py-4 text-gray-900 whitespace-nowrap dark:text-white"
                >
                  <img
                    className="w-10 h-10 rounded-full"
                    src={userData?.picture ?? image}
                    alt="Jese image"
                  />
                  <div className="ps-3">
                    <div className="text-base font-semibold">
                      {userData?.name}
                    </div>
                    <div className="font-normal text-gray-500">
                      {userData?.email}
                    </div>
                  </div>
                </th>
                <td className="px-6 py-4">{userData?.phoneNumber ?? "N/A"}</td>
                <td className="px-6 py-4">
                  <div className="flex items-center">
                    {userData?.address ?? "N/A"}
                  </div>
                </td>
                <td className="px-6 py-4">
                  <a
                    href="#"
                    className="font-medium text-blue-600 dark:text-blue-500 hover:underline"
                  >
                    Edit user
                  </a>
                </td>
              </tr>
            ))}
            <tr>
              {" "}
              {!data && (
                <p class="flex justify-center text-2xl w-full text-center mx-auto p-5">
                  {" "}
                  ☹️ No Data Found!
                </p>
              )}
            </tr>

            {/* Additional rows */}
          </tbody>
        </table>
      </div>
      <div class="flex justify-center text-center w-full ">
        <button
          onClick={handlePrevious}
          disabled={page === 0}
          href="#"
          className={`flex items-center justify-center px-3 h-8 me-3 text-sm font-medium ${
            page === 0
              ? "text-gray-400 bg-gray-200 cursor-not-allowed"
              : "text-gray-500 bg-white border hover:bg-gray-100 hover:text-gray-700"
          } border-gray-300 rounded-lg dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white`}
        >
          <svg
            class="w-3.5 h-3.5 me-2 rtl:rotate-180"
            aria-hidden="true"
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 14 10"
          >
            <path
              stroke="currentColor"
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M13 5H1m0 0 4 4M1 5l4-4"
            />
          </svg>
          Previous
        </button>
        <button
          onClick={handleNext}
          disabled={page === totalPage - 1 || !data}
          href="#"
          className={`flex items-center justify-center px-3 h-8 text-sm font-medium ${
            page === totalPage - 1 || !data
              ? "text-gray-400 bg-gray-200 cursor-not-allowed"
              : "text-gray-500 bg-white border hover:bg-gray-100 hover:text-gray-700"
          } border-gray-300 rounded-lg dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white`}
        >
          Next
          <svg
            class="w-3.5 h-3.5 ms-2 rtl:rotate-180"
            aria-hidden="true"
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 14 10"
          >
            <path
              stroke="currentColor"
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M1 5h12m0 0L9 1m4 4L9 9"
            />
          </svg>
        </button>
      </div>
    </div>
  );
};

export default ContactList;
