import React, {useEffect}from 'react';
import {Link} from "react-router-dom"
import $ from "jquery";
import img from '../images/profile.jpg';
import {useCookies} from "react-cookie";

function Left(){

  const [cookies, setCookie] = useCookies(['userId']); 

  useEffect(() => {
    $(".close-menu").click(function () {
      $(".left-area").addClass("all");
    });
    $("a#pageLink").click(function () {
      $("a#pageLink").removeClass("active");
      $(this).addClass("active");
      // console.log(this.classList)
    });
  },[]);


  

    return(
      <div class="left-area all">
        <div class="app-header">Own.
          <span class="inner-text">pli</span>
          <button class="close-menu">
            <svg width="24" height="24" fill="none" stroke="#51a380" strokeLinecap="round" stroke-linejoin="round" stroke-width="2" class="feather feather-x">
              <defs />
              <path d="M18 6L6 18M6 6l12 12" />
            </svg>
          </button>
        </div>
        <div class="left-area-content">
          <div class="profile">
            <img src={img} alt="프로필 사진"></img>
            <div class="profile-info">
              {(cookies.userId!== undefined)?<span class="profile-name">이지영</span>:<span></span>}
              {(cookies.userId!== undefined)?<span class="country">한국</span>:<span></span>}
            </div>
          </div>
          <div class="page-link-list">
          <Link to ="/">
            <a href="#" class="item-link active" id="pageLink">
              <svg class="link-icon" fill="none" stroke="currentColor" strokeLinecap="round" stroke-linejoin="round" stroke-width="2" viewBox="0 0 24 24">
                <defs />
                <path d="M3 9l9-7 9 7v11a2 2 0 01-2 2H5a2 2 0 01-2-2z" />
                <path d="M9 22V12h6v10" /></svg>
              Home</a>
          </Link>

              {/* <a href="#" class="item-link" id="pageLink">
              <svg class="link-icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" strokeLinecap="round" stroke-linejoin="round">
                <path d="M5 3l14 9-14 9V3z" />
              </svg>
              My </a> */}
            <Link to ="/myPlaylist">
              <a href="#" class="item-link" id="pageLink" >
                <svg class="link-icon" xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" strokeLinecap="round" stroke-linejoin="round">
                <path d="M8 6h13M8 12h13M8 18h13M3 6h.01M3 12h.01M3 18h.01" />
              </svg>
                My Playlist</a>
            </Link>
            <Link to ="/myPage">
              <a href="#" class="item-link" id="pageLink">
              <svg class="link-icon" xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" strokeLinecap="round" stroke-linejoin="round">
                <path d="M20.84 4.61a5.5 5.5 0 00-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 00-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 000-7.78z" />
              </svg>
              Mypage</a>
            </Link>
          </div>

          <div class='item-blank'></div>
          <Link to ="/addList">
            <button class="btn-invite">Add Playlist</button>
          </Link>
        </div>
        {/* <button class="btn-invite">Add Playlist</button> */}
      </div>
    );
}

export default Left;