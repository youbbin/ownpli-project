import React,{useState} from 'react';
import {
    BrowserRouter,
    Routes,
    Route,
} from "react-router-dom";

// import cookies from 'react-cookies';
import {useCookies} from "react-cookie";

import ViewPage from './ViewPage/ViewPage';
import Login from './Login/login';
import Logout from './Login/logout';
import Signup from './Login/Signup';
import MyPlaylist from './MyPlaylist/MyPlaylist';
import MyPage from './Mypage/MyPage';
import AddList from './Addlist/Addlist';

import List_Songs from './MyPlaylist/List_Song'
import SongInfo from './MyPlaylist/SongInfo'

import Up from '../Basic/Up';
import Left from '../Basic/Left';

import Notificaton_toLogin from './Notificaton_toLogin';

import axios from 'axios';


const getURL = (url) => {
    console.log(url);
}


const Content = ()=>{
    const [cookies, setCookie, removeCookie] = useCookies(['userId']); 
    // console.log("dddd:"+(cookies.userId === undefined))
    const [userId, setUserId] = useState((cookies.userId === undefined)? '':cookies.userId);
    const [check, setCheck] = useState(false);

    
    const setLogin =async()=>{
        console.log('click');

        let id = document.getElementsByClassName('id')[0].value;
        let pw = document.getElementsByClassName('password')[0].value;

        if(id==='' | pw==''){
            alert('아이디 또는 비밀번호를 입력하세요')
        }
        else{

            // id test123@naver.com

            let data = {
                userId: id,
                password: pw
                
            };

            console.log(data)
    
            const response = await axios.post(
                '/login', data,
                {
                    headers: {
                        'Accept': 'application/json',
                    }
                }
            );
    
            // console.log(response.data.userId)
            if(id === response.data.userId){
                setUserId(response.data.userId);
                setCookie('userId',response.data.userId);
            }

        }
    }

    const setLogout =() =>{
        removeCookie('userId')
    }

    return(
        <BrowserRouter>
            <div id = 'container'>
                <Left></Left>
                <div class="right-area">
                    <Up getURL={getURL}></Up>
                    <Routes>
                        <Route path="/" element={<ViewPage/>}/>
                        <Route path="/login" element={(userId==='')?<Login setLogin = {setLogin}/>:<Logout setLogout= {setLogout}></Logout>} />
                        <Route path="/signup" element={<Signup/>} />
                        <Route path="/myPlaylist" element={(userId!=='')?<MyPlaylist/>:<Notificaton_toLogin/>} />
                        <Route path="/myPage" element={(userId!=='')?<MyPage/>:<Notificaton_toLogin/>} />
                        <Route path="/addList" element={(userId !=='')?<AddList/>:<Notificaton_toLogin/>} />
                        <Route path="/myPlaylist/Songs" element={(userId !=='')?<List_Songs/>:<Notificaton_toLogin/>} />
                        <Route path="/myPlaylist/SongInfo" element={(userId !=='')?<SongInfo/>:<Notificaton_toLogin/>} />
                    </Routes>
                </div>
            </div>
        </BrowserRouter>
    );
}

export default Content; 

