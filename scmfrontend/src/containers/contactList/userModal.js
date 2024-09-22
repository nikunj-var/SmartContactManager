import React from "react";
import { FaPhoneAlt } from "react-icons/fa";
import { FaLinkedin } from "react-icons/fa";
import { MdFavoriteBorder, MdOutlineMail } from "react-icons/md";

import { CiLocationOn } from "react-icons/ci";

const UserModal = ({ showModal, setShowModal, data }) => {
  if (!showModal) return <></>;

  return (
    <div
      id="default-modal"
      tabindex="-1"
      aria-hidden="true"
      class={
        " fixed top-0 right-0 left-0 z-50 flex justify-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full "
      }
    >
      <div class="relative p-4 w-full max-w-2xl max-h-full ">
        <div class="relative rounded-lg shadow bg-white dark:bg-gray-700">
          <button
            id="close-modal"
            type="button"
            class="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-end items-end ml-auto dark:hover:bg-gray-600 dark:hover:text-white"
            onClick={() => setShowModal((prev) => !prev)}
          >
            <svg
              class="w-3 h-3"
              aria-hidden="true"
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 14 14"
            >
              <path
                stroke="currentColor"
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"
              />
            </svg>
            <span class="sr-only">Close modal</span>
          </button>
          <div class="flex flex-col items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600">
            <img class="w-40 h-40 rounded-full mx-auto" src={data?.picture} />
            <h3 class="text-xl font-semibold text-gray-900 dark:text-white pt-2">
              {data?.name}
            </h3>
            <div class="flex gap-2">
              <h2 class="flex gap-1 text-base items-center text-gray-900 dark:text-white">
                <MdOutlineMail />
                {data?.email}
              </h2>
              <h2 class="flex gap-1 text-base items-center text-gray-900 dark:text-white">
                | <FaPhoneAlt />
                {data?.phoneNumber}
              </h2>
            </div>
          </div>

          <div class="p-4 flex flex-col gap-2">
            <div class="flex flex-col px-10  ">
              <div class="flex justify-between">
                <h3 class="flex gap-1 text-base items-center font-semibold text-gray-900 dark:text-white pt-2">
                  <CiLocationOn />
                  Address
                </h3>
                <h3 class="text-base  text-gray-900 dark:text-white pt-2">
                  {data?.address ? data?.address : "N/A"}
                </h3>
              </div>
            </div>
            <div class="flex flex-col px-10">
              <div class="flex justify-between">
                <h3 class="flex gap-1 text-base items-center font-semibold text-gray-900 dark:text-white pt-2">
                  <MdFavoriteBorder /> Favourite
                </h3>
                <h3 class="text-base  text-gray-900 dark:text-white pt-2">
                  {data?.favorite ? "Yes" : "No"}
                </h3>
              </div>
            </div>
            <div class="flex flex-col px-10">
              <div class="flex justify-between">
                <h3 class="flex gap-1 text-base items-center font-semibold text-gray-900 dark:text-white pt-2">
                  <FaLinkedin /> LinkedIn
                </h3>
                <a
                  class="text-base  text-gray-900 dark:text-white pt-2"
                  href={data?.linkedInLink}
                  target="blank"
                >
                  {data?.linkedInLink ? data?.linkedInLink : "N/A"}
                </a>
              </div>
            </div>
          </div>

          <div class="flex items-center justify-center p-4 md:p-5 border-t border-gray-200 rounded-b dark:border-gray-600">
            <button
              id="decline-btn"
              type="button"
              onClick={() => setShowModal((prev) => !prev)}
              class="py-2.5 px-5 ms-3 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-100 dark:focus:ring-gray-700 dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-700"
            >
              Close
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default UserModal;
