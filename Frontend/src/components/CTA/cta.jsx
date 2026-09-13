function CTA() {
    return (
        <section className="section text-center">

            <div className="container">

                <div
                    className="glass-card"
                    style={{
                        maxWidth: "700px",
                        margin: "0 auto",
                        padding: "var(--space-3xl)"
                    }}
                >

                    <h2
                        style={{
                            fontSize: "var(--font-3xl)",
                            fontWeight: 800,
                            marginBottom: "var(--space-md)"
                        }}
                    >
                        Ready to{" "}
                        <span className="text-accent">
                            Save Lives
                        </span>
                        ?
                    </h2>

                    <p
                        style={{
                            color: "var(--text-secondary)",
                            marginBottom: "var(--space-xl)",
                            fontSize: "var(--font-lg)"
                        }}
                    >
                        Join thousands of donors making a difference every day.
                        Register now and become part of the Veinlinker community.
                    </p>

                    <div
                        style={{
                            display: "flex",
                            gap: "var(--space-md)",
                            justifyContent: "center",
                            flexWrap: "wrap"
                        }}
                    >

                        <a
                            href="/register"
                            className="btn btn-primary btn-lg"
                        >
                            <i className="fas fa-user-plus"></i>
                            {" "}Register as Donor
                        </a>

                        <a
                            href="/map"
                            className="btn btn-secondary btn-lg"
                        >
                            <i className="fas fa-map-marked-alt"></i>
                            {" "}Explore Map
                        </a>

                    </div>

                </div>

            </div>

        </section>
    );
}

export default CTA;