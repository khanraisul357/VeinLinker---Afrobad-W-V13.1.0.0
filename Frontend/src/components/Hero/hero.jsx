function Hero() {
    return (
        <section className="hero" id="home">

            <div className="container">

                <div className="hero-content">

                    <div className="hero-text">

                        <h1>
                            Every Drop<br />
                            <span className="gradient-text">
                                Saves a Life
                            </span>
                        </h1>

                        <p>
                            Connect with verified blood donors near you instantly.
                            Our map-based platform makes finding the right blood type
                            faster, easier, and more reliable than ever.
                        </p>


                        <div className="hero-actions">

                            <a
                                href="/donors"
                                className="btn btn-primary btn-lg"
                            >
                                <i className="fas fa-search"></i>
                                {" "}Find Donors
                            </a>

                            <a
                                href="/register"
                                className="btn btn-outline btn-lg"
                            >
                                <i className="fas fa-hand-holding-heart"></i>
                                {" "}Become a Donor
                            </a>

                        </div>


                        <div className="hero-stats">

                            <div className="hero-stat">
                                <span className="stat-number">
                                    0
                                </span>

                                <span className="stat-label">
                                    Donors/Receivers
                                </span>
                            </div>


                            <div className="hero-stat">
                                <span className="stat-number">
                                    0
                                </span>

                                <span className="stat-label">
                                    Active
                                </span>
                            </div>


                            <div className="hero-stat">
                                <span className="stat-number">
                                    0
                                </span>

                                <span className="stat-label">
                                    Donations
                                </span>
                            </div>

                        </div>

                    </div>


                    {/* Quick Search */}
                    <div className="hero-visual">

                        <div className="hero-card glass-card">

                            <div className="blood-drop">
                                🩸
                            </div>

                            <h3
                                style={{
                                    marginBottom: "var(--space-sm)"
                                }}
                            >
                                Quick Search
                            </h3>

                            <p
                                style={{
                                    color: "var(--text-muted)",
                                    fontSize: "var(--font-sm)",
                                    marginBottom: "var(--space-md)"
                                }}
                            >
                                Find a donor by blood type
                            </p>


                            <form
                                action="/donors"
                                method="GET"
                                className="hero-search-mini"
                            >

                                <div className="search-row">

                                    <select
                                        name="blood_group"
                                        required
                                    >
                                        <option value="">
                                            Blood Group
                                        </option>

                                        <option value="A">
                                            A
                                        </option>

                                        <option value="B">
                                            B
                                        </option>

                                        <option value="AB">
                                            AB
                                        </option>

                                        <option value="O">
                                            O
                                        </option>
                                    </select>


                                    <select name="rh_factor">

                                        <option value="">
                                            Rh
                                        </option>

                                        <option value="+">
                                            +
                                        </option>

                                        <option value="-">
                                            -
                                        </option>

                                    </select>


                                    <button
                                        type="submit"
                                        className="btn btn-primary btn-sm"
                                    >
                                        <i className="fas fa-search"></i>
                                    </button>

                                </div>

                            </form>

                        </div>

                    </div>

                </div>

            </div>

        </section>
    );
}

export default Hero;