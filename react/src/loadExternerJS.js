

function LoadExternerJS(){
    const script = document.createElement("script");
    script.src= 'https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js';
    script.async = true;
    document.body.appendChild(script);
 
    const script2 = document.createElement("script");
    script2.src= 'https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/owl.carousel.min.js';
    script2.async = true;
    document.body.appendChild(script2);
 
    const script3 = document.createElement("script");
    script3.src= './script.js';
    script3.async = true;
    document.body.appendChild(script3);
}

export default LoadExternerJS;