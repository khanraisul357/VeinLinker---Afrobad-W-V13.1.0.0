import logo from "../../assets/images/VeinLinker Logo.png";
import sdgIcon from "../../assets/images/sdg.png";
import missionIcon from "../../assets/images/mission.png";
import visionIcon from "../../assets/images/vision.png";

function About() {


return (

    <section className="section" id="about">

        <div className="container">

            <div className="section-header">

                <h2>About <span className="text-accent">VeinLinker</span></h2>

                <p>
                    Technology-driven blood donor connection for faster,
                    safer, and more reliable emergency response.
                </p>

            </div>


			{/* VeinLinker Logo Showcase */}

			<div className="about-logo-showcase">
			   
			   <a href="/#home" className="about-logo-link">

			        <img
			            src={logo}
			            alt="VeinLinker Logo"
			            className="about-logo"
			        />
				
               </a>
			   
			    <h3 className="about-logo-title">
			        An Advanced, Scalable & Nationally Integratable
			        Blood Donor & Receiver Linking Platform in Bangladesh
			    </h3>

			</div>
			
			{/* Introduction */}

			<div className="glass-card about-description-card">

			    <p className="about-description">
			        VeinLinker is a technology-driven platform that connects
			        voluntary blood donors with people in urgent need through
			        verified donor information, real-time availability,
			        location-aware discovery, eligibility validation, and
			        rapid communication.
			    </p>

			</div>


			
			{/* Mission, Vision & SDG */}

			<div className="steps-grid">

			    {/* Mission */}

			    <div className="step-card glass-card">

				    <div className="step-number">
				        <img src={missionIcon} alt="Mission" />
				    </div>

			        <h3>Our Mission</h3>

			        <p>
			            To make emergency blood access faster, safer, and
			            more reliable by connecting patients with verified,
			            eligible, available, and nearby blood donors while
			            reducing the delays caused by fragmented and
			            outdated donor information.
			        </p>

			    </div>


			    {/* Vision */}

			    <div className="step-card glass-card">

				    <div className="step-number">
				        <img src={visionIcon} alt="Vision" />
				    </div>

			        <h3>Our Vision</h3>

			        <p>
			            To build a connected and scalable blood-donation
			            ecosystem where individuals, institutions,
			            hospitals, blood banks, NGOs, and healthcare
			            organizations can work together to ensure that
			            timely access to safe blood is never limited by
			            information or communication barriers.
			        </p>

			    </div>


			    {/* SDG 3 */}

			    <div className="step-card glass-card">

				    <div className="step-number">
				        <img src={sdgIcon} alt="SDG 3" />
				    </div>

			        <h3>Supporting SDG 3</h3>

			        <p>
			            VeinLinker aligns with the United Nations Sustainable
			            Development Goal 3: <strong>Good Health and Well-Being</strong>,
			            by improving access to emergency blood donation and
			            strengthening community healthcare responsiveness.
			        </p>

			    </div>

			</div>
			


            
        </div>

    </section>

);

}

export default About;
