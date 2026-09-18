import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import logo from "../../assets/images/VeinLinker Logo.png";
import {
    faPhone,
    faEnvelope
} from "@fortawesome/free-solid-svg-icons";

import {
    faFacebookF,
    faTwitter,
    faInstagram,
    faWhatsapp
} from "@fortawesome/free-brands-svg-icons";


function Footer() {

    return (

        <footer className="footer">

            <div className="container">

                <div className="footer-grid">

                    {/* Brand */}

                    <div className="footer-brand">

                         <a href="/#home" className="footer-logo">

						    <span className="logo-icon">
						        <img
						            src={logo}
						            alt="VeinLinker Logo"
						        />
						    </span>

                            <span className="logo-text">
                                Vein
                                <span className="text-accent">
                                    linker
                                </span>
                            </span>

                        </a>

                        <p className="footer-tagline">
                            Connecting donors. Saving lives. Every drop counts.
                        </p>


                        <div className="footer-social">

                            <a
                                href="https://facebook.com"
                                aria-label="Facebook"
                                target="_blank"
                                rel="noopener noreferrer"
                            >
                                <FontAwesomeIcon icon={faFacebookF} />
                            </a>

                            <a
                                href="https://twitter.com"
                                aria-label="Twitter"
                                target="_blank"
                                rel="noopener noreferrer"
                            >
                                <FontAwesomeIcon icon={faTwitter} />
                            </a>

                            <a
                                href="https://instagram.com"
                                aria-label="Instagram"
                                target="_blank"
                                rel="noopener noreferrer"
                            >
                                <FontAwesomeIcon icon={faInstagram} />
                            </a>

                            <a
                                href="https://wa.me/880123456789"
                                aria-label="WhatsApp"
                                target="_blank"
                                rel="noopener noreferrer"
                            >
                                <FontAwesomeIcon icon={faWhatsapp} />
                            </a>

                        </div>

                    </div>


                    {/* Quick Links */}

                    <div className="footer-links">

                        <h4>Quick Links</h4>

                        <a href="/donors">
                            Find Donors
                        </a>

                        <a href="/map">
                            Donor Map
                        </a>

                        <a href="/blood-banks">
                            Blood Banks
                        </a>

                        <a href="/register">
                            Become a Donor
                        </a>

                    </div>


                    {/* Information */}

                    <div className="footer-links">

                        <h4>Information</h4>

                        <a href="#">
                            Who Can Donate
                        </a>

                        <a href="#">
                            Blood Types Guide
                        </a>

                        <a href="#">
                            FAQs
                        </a>

                        <a href="#">
                            Privacy Policy
                        </a>

                    </div>


                    {/* Emergency */}

                    <div className="footer-links">

                        <h4>Contact</h4>

                        <p className="footer-emergency">
                            <FontAwesomeIcon icon={faPhone} />
                            {" "}+880-123-456-789
                        </p>

                        <p className="footer-emergency">
                            <FontAwesomeIcon icon={faEnvelope} />
                            {" "}help@veinlinker.com
                        </p>

                    </div>

                </div>


                <div className="footer-bottom">

                    <p>
                        © 2026 Veinlinker. All rights reserved.
                        Built to save lives.
                    </p>

                </div>

            </div>

        </footer>

    );
}

export default Footer;