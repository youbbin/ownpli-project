import React from 'react';
import axios from 'axios';

function Signup(props){

    return(
        <form action="doJoin" method="POST" className="joinForm" onsubmit="DoJoinForm__submit(this); return false;">
                                                                                               
            <h2>회원가입</h2>
            <div class="textForm">
                <input name="loginId" type="text" className="id_signup" placeholder="아이디"></input>
            </div>
            <div class="textForm">
                <input name="loginPw" type="password" className="pw_signup" placeholder="비밀번호"></input>
            </div>
            <div class="textForm">
                <input name="name" type="text" className="name" placeholder="이름"></input>
            </div>
            <div class="textForm">
                <input name="age" type="text" className="age" placeholder="나이"></input>
            </div>
            <div className="radioForm">
            <div claclassNamess = "sex">
                <input name="sex" type="radio" value="male" required></input>
                <label for="male">남자</label>
                <input name="sex" type="radio" value="female"></input>
                <label for="female">여자</label>
            </div>
            
            </div>
            <input type="submit" className="btn_join" value="J O I N"/>
        </form>
    );
}

export default Signup;