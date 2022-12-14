import React from 'react';
import axios from 'axios';
import {Link} from "react-router-dom"

const notificaton_toLogin = (props) =>{
    return(
        <div className='needLogin'>
            <div className='needLogin_text'>로그인 필요</div>
            {/* <button className='toLogin_btn'>로그인</button> */}

            <Link to ="/login">
                <button class="toLogin_btn">로그인</button>
            </Link>
        </div>
    
    );
}

export default notificaton_toLogin;