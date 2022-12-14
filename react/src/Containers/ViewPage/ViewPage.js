import '../../index.css'
import React,{useEffect, useState} from 'react';
import axios from 'axios'
import { useCookies } from 'react-cookie';
import '../../css/ViewPage.css'

import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

import Slider1 from './Slider1'
import Slider2 from './Slider2'


import img1 from '../../images/0000.jpg'
import img2 from '../../images/0007.jpg'
import img3 from '../../images/0059.jpg'
// import img4 from '../images/0061.jpg'
// import img5 from '../images/0062.jpg'
// import img6 from '../images/0063.jpg'
// import img7 from '../images/0064.jpg'
// import img8 from '../images/0065.jpg'
// import img9 from '../images/0087.jpg'



function ViewPage(){
  const [cookies, setCookie] = useCookies(['userId']); 
  const [recoment, setRecoment] = useState([]);

  const get =async() =>{

    let userId = cookies.userId;

    if(userId === undefined){userId = null}

    console.log(userId)

    let data = {
      userId: userId
    }

    console.log(data)

    let res = await axios.post(
      '/home', data,
      {
          headers: {
              'Accept': 'application/json',
          }
      }
    )
    setRecoment(res.data)

    console.log(res.data)
  }

  useEffect(() => {
    get();
  },[]);
  

    return (
      
      <div class="right-content">
        {/* <div class="content-line content-line-hero">
          <div class="line-header">
            <span class="header-text">New Uploads</span>
          </div>
          <div class="slider-wrapper owl-carousel owl-theme" id="owl-slider-1">
            <div class="item hero-img-wrapper img-1">
              <div class="upload-text-wrapper">
                <p class="upload-text-header">The </p>
                <p class="upload-text-info">By Pravin</p>
              </div>
              <img src={img1} alt="SlideShow"></img>
            </div>
            <div class="item hero-img-wrapper img-2">
              <div class="upload-text-wrapper">
                <p class="upload-text-header">History of Art</p>
                <p class="upload-text-info">By Pravin <span> 10 minutes ago</span></p>
              </div>
              <img src={img2} alt="SlideShow"></img>
            </div>
            <div class="item hero-img-wrapper img-3">
              <div class="upload-text-wrapper">
                <p class="upload-text-header">Van Life</p>
                <p class="upload-text-info">By Tess <span> 3 days ago</span></p>
              </div>
              <img src={img3} alt="SlideShow"></img>
            </div>
          </div>
        </div> */}

        <div class="content">
          <Slider2 header={'Top 10'} data ={recoment.top10}></Slider2>
        </div>

        <div class="content">
          <Slider1 header={'New Upload'} data ={recoment.newSongs}></Slider1>
        </div>


        <div class="content">
          <Slider1 header={'Likes'} data ={recoment.likes}></Slider1>
        </div>

        <div class="content">
        <Slider1 header={'Mood'} data ={recoment.mood}></Slider1>
        </div>

        <div class="content">
        {(cookies.userId !== undefined)?<Slider1 header={'AGE'} data ={recoment.age}></Slider1>:<div></div>}
        </div>
      </div>
    );
}

export default ViewPage;