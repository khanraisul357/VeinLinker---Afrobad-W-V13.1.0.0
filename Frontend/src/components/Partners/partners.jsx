import local from "../../assets/images/Local Partners.png";
import international from "../../assets/images/International Partners.png";

function Partners() {
    return (
        <section
            className="section partners-section"
            id="partners"
        >
            <div className="container">

                <div className="section-header">
                    <h2>
                        Our <span className="text-accent">Partners</span>
                    </h2>

                    <p>
                        Building a connected ecosystem for accessible and
                        responsive blood donation.
                    </p>
                </div>

                <div className="partners-grid">

                    {/* Local Partners */}

                    <div className="partner-card glass-card">

					    <div className="partner-icon">
					        <img
					            src={local}
					            alt="Local Partners"
					        />
					    </div>

                        <h3>Local Partners</h3>

                        <p>
                            VeinLinker aims to collaborate with local hospitals,
                            blood banks, universities, NGOs, community
                            organizations, and corporate institutions to
                            strengthen local blood-donation networks.
                        </p>

                        <div className="partner-types">

                            <div>
                                <i className="fas fa-hospital"></i>
                                Hospitals & Blood Banks
                            </div>

                            <div>
                                <i className="fas fa-university"></i>
                                Universities & Institutions
                            </div>

                            <div>
                                <i className="fas fa-users"></i>
                                NGOs & Community Organizations
                            </div>

                        </div>

                    </div>


                    {/* International Partners */}

                    <div className="partner-card glass-card">

					    <div className="partner-icon">
					        <img
					            src={international}
					            alt="International Partners"
					        />
					    </div>

                        <h3>International Partners</h3>

                        <p>
                            Our long-term vision includes collaboration with
                            international health organizations, humanitarian
                            organizations, technology partners, and global
                            blood-donation networks to expand the impact of
                            accessible blood services.
                        </p>

                        <div className="partner-types">

                            <div>
                                <i className="fas fa-globe-asia"></i>
                                International Health Organizations
                            </div>

                            <div>
                                <i className="fas fa-hands-helping"></i>
                                Humanitarian Organizations
                            </div>

                            <div>
                                <i className="fas fa-network-wired"></i>
                                Global Health Networks
                            </div>

                        </div>

                    </div>

                </div>


                {/* Partnership CTA */}

                <div className="partners-cta">

                    <p>
                        Interested in contributing to a connected blood
                        donation ecosystem?
                    </p>

                    <a
                        href="mailto:partnerships@veinlinker.com"
                        className="btn btn-secondary"
                    >
                        <i className="fas fa-handshake"></i>
                        {" "}Partner With Us
                    </a>

                </div>

            </div>
        </section>
    );
}

export default Partners;