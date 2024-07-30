import About from "../../containers/about";
import Login from "../../containers/login";

export const UnAuthRoutes = [
  { path: "/", component: Login },
  { path: "/login", component: Login },
  { path: "/about", component: About },
];
