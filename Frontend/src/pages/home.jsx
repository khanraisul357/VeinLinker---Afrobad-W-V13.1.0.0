import Navbar from "../components/Navbar/navbar.jsx"
import Hero from "../components/Hero/hero.jsx";
import HowItWorks from "../components/How It Works/howitworks.jsx";
import CTA from "../components/CTA/cta.jsx";
import About from "../components/About/about.jsx";
import Partners from "../components/Partners/partners.jsx";
import Footer from "../components/Footer/footer.jsx";

function Home() {

return (

    <>

	    <Navbar />
		
        <Hero />

        <HowItWorks />

        <CTA />

        <About />

        <Partners />
		
		<Footer />

    </>

);

}

export default Home;
