import React, {useEffect, useState}from 'react';
import {Link} from "react-router-dom"
import $ from "jquery";
import axios from 'axios';
import {useCookies} from "react-cookie";


function Up({url, getURL}){

  const [cookies, setCookie] = useCookies(['userId']); 


  useEffect(() => {
    $(".menu-button").click(function () {
      $(".left-area").removeClass("all");
    });
  },[]);

    // console.log(url);
  
  
    return(
    <div class="right-area-upper">
      <button class="menu-button">
        <svg width="24" height="24" fill="none" stroke="#51a380" strokeLinecap="round" stroke-linejoin="round" stroke-width="2">
          <defs />
          <path d="M3 12h18M3 6h18M3 18h18" />
        </svg>
      </button>
      <div class="search-part-wrapper">
        <input class="search-input" type="text" placeholder="Search music..."></input>
      </div>
      <div class="action-buttons-wrapper">
        <button class="action-buttons btn-search">Search</button>
        {/* <button class="action-buttons btn-login" onClick={setLogin}>LogIn</button> */}
        <Link to ="/login">
        <button class="action-buttons btn-login">{(cookies.userId !== undefined)?'Logout':'LogIn'}</button>
        </Link>
      </div>
    </div>
    );
}

export default Up;