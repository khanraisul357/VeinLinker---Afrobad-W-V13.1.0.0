import React from "react";
import ReactDOM from "react-dom/client";

import App from "./App";
import "./styles/Home/navbar.css";
import "./styles/Home/hero.css";
import "./styles/Home/howitworks.css";
import "./styles/Home/cta.css";
import "./styles/Home/about.css";
import "./styles/Home/partners.css";
import "./styles/Home/footer.css";
import "./styles/veinlinker.css";
import "./styles/variable.css";

ReactDOM.createRoot(document.getElementById("root")).render(
    <React.StrictMode>
        <App />
    </React.StrictMode>
);