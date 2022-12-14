import React,{useEffect} from 'react';
import '../../css/login.css'
import {Link} from "react-router-dom"

function Login(props){
    const {setLogin} = props;

    return(
        <div>

            <div className="login-form">
                <div className="box">
                    <h2>로그인</h2>

                    <div class="input">
                        <input type="text" name="id" className="id" placeholder="아이디"></input>
                        <input type="password" name="password" className="password" placeholder="비밀번호"></input>
                        <input type="submit" value="로그인" className="submit-btn" onClick={setLogin}></input>
                    </div>

                    <Link to ="/signup">
                        <div className="links">
                            <a href="#">회원가입</a>
                        </div>
                    </Link>

                
                </div>
            </div>

            
            {/* <button className='login_btn' >login</button> */}
        </div>
    );
}

export default Login;