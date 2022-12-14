import React from 'react';
import img from '../../images/profile.jpg'
import '../../css/myPage.css'

function MyPage(){
    

    return(
        <div class="layout">
  <div class="profile_mypage">
    <div class="profile__picture"><img src ={img}/></div>
    <div class="profile__header">
      <div class="profile__account">
        <h4 class="profile__username">지영 님</h4>
      </div>
      <div class="profile__edit"><a class="profile__button" href="#">Edit Name</a></div>
    </div>
  </div>
</div>
    );
}

export default MyPage;