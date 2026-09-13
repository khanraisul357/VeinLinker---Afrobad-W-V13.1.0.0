
import logo from "../../assets/images/VeinLinker Logo.png";

function Navbar() {

    const toggleMenu = () => {
        const navMenu = document.getElementById("navMenu");
        const navToggle = document.getElementById("navToggle");

        navMenu.classList.toggle("active");
        navToggle.classList.toggle("active");

        const isOpen = navMenu.classList.contains("active");

        navToggle.setAttribute("aria-expanded", isOpen);
    };


    const closeMenu = () => {
        const navMenu = document.getElementById("navMenu");
        const navToggle = document.getElementById("navToggle");

        navMenu.classList.remove("active");
        navToggle.classList.remove("active");

        navToggle.setAttribute("aria-expanded", "false");
    };


    return (

        <nav className="navbar" id="navbar">

            <div className="container nav-container">


                {/* =====================================================
                    LOGO
                   ===================================================== */}

                <a href="/" className="nav-logo">

                    <img
                        src={logo}
                        alt="VeinLinker Logo"
                        className="logo-image"
                    />

                    <span className="logo-text">

                        Vein

                        <span className="text-accent">
                            Linker
                        </span>

                    </span>

                </a>



                {/* =====================================================
                    NAVIGATION MENU
                   ===================================================== */}

                <ul className="nav-menu" id="navMenu">


                    {/* Home */}

                    <li>

                        <a
                            href="/"
                            className="nav-link"
                            onClick={closeMenu}
                        >
                            Home
                        </a>

                    </li>



                    {/* About */}

                    <li>

                        <a
                            href="/#about"
                            className="nav-link"
                            onClick={closeMenu}
                        >
                            About
                        </a>

                    </li>



                    {/* Partners */}

                    <li>

                        <a
                            href="/#partners"
                            className="nav-link"
                            onClick={closeMenu}
                        >
                            Partners
                        </a>

                    </li>



                    {/* Find Donors */}

                    <li>

                        <a
                            href="/donors"
                            className="nav-link"
                            onClick={closeMenu}
                        >
                            Find Donors
                        </a>

                    </li>



                    {/* Map */}

                    <li>

                        <a
                            href="/map"
                            className="nav-link"
                            onClick={closeMenu}
                        >
                            Map
                        </a>

                    </li>



                    {/* Hospital Inventory */}

                    <li>

                        <a
                            href="/hospital-inventory"
                            className="nav-link"
                            onClick={closeMenu}
                        >
                            Hospital Inventory
                        </a>

                    </li>



                    {/* Blood Banks */}

                    <li>

                        <a
                            href="/blood-banks"
                            className="nav-link"
                            onClick={closeMenu}
                        >
                            Blood Banks
                        </a>

                    </li>



                    {/* =================================================
                        LOGIN FOR MOBILE MENU
                       ================================================= */}

                    <li className="mobile-login">

                        <a
                            href="/login"
                            className="nav-link btn-nav-login"
                            onClick={closeMenu}
                        >
                            Login
                        </a>

                    </li>

                </ul>



                {/* =====================================================
                    RIGHT SIDE ACTIONS
                   ===================================================== */}

                <div className="nav-actions">


                    {/* =================================================
                        DESKTOP LOGIN
                       ================================================= */}

                    <a
                        href="/login"
                        className="nav-link btn-nav-login desktop-login"
                    >
                        Login
                    </a>



                    {/* =================================================
                        REGISTER
                       ================================================= */}

                    <a
                        href="/register"
                        className="nav-link btn-nav-register"
                    >
                        Register
                    </a>



                    {/* =================================================
                        MOBILE MENU BUTTON
                       ================================================= */}

                    <button
                        className="nav-toggle"
                        id="navToggle"
                        aria-label="Toggle navigation"
                        aria-expanded="false"
                        onClick={toggleMenu}
                    >

                        <span></span>
                        <span></span>
                        <span></span>

                    </button>

                </div>

            </div>

        </nav>

    );

}

export default Navbar;






