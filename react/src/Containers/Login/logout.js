import React,{useEffect} from 'react';
import '../../css/login.css'
import {Link} from "react-router-dom"

function Logout(props){
    const {setLogout} = props;

    return(
        <div>

            <div className="login-form">
                <div className="box">
                    <h2>로그아웃</h2>

                    <div class="input">
                        <input type="submit" value="로그아웃" className="submit-btn" onClick={setLogout}></input>
                    </div>

                
                </div>
            </div>

            
            {/* <button className='login_btn' >login</button> */}
        </div>
    );
}

export default Logout;