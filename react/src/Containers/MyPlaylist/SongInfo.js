import React,{useEffect, useState} from 'react';
import '../../css/SongInfo.css'
import { useFetcher, useLocation } from 'react-router-dom';
import axios from 'axios';
import {useCookies} from "react-cookie";

// import img from'../../images/img/0001.jpg'

function SongInfo(){
    const location = useLocation();
    const [state, setState] = useState([]);
    const [lyrics, setLyrics] = useState([]);
    const [img, setIMG] = useState('');
    const [cookies, setCookie] = useCookies(['userId']); 

    

    useEffect(()=>{

        let id = decodeURI(location.search.split('=')[1]);
        console.log(id);

        let data={
            title: id
        }

        console.log(data)

        const getInfor =async()=>{
            let res = await axios.post(
                '/music/title', data,{
                    headers: {
                        'Accept': 'application/json',
                    }
                }
            )

            setState(res.data.musicInfo);
            setLyrics(res.data.lyrics);
            setIMG('../../images/img/'+res.data.musicInfo.musicId+'.jpg')
            console.log(res.data.musicInfo.musicId)
          
            
        }
        getInfor();

    },[location])


    const setLike = async() =>{
        let data ={
            title: state.title,
            userId: cookies.userId
        }

        await axios.post(
            '/music/title/like', data
        )
    }
    

    return(
        <section class="product">
	<div class="product__photo">
		<div class="photo-container">
			<div class="photo-main">
				<img src={'../../images/img/'+state.musicId+'.jpg'}aler='img'></img>
			</div>
		</div>
	</div>
	<div class="music_info">
		<div class="title">
			<h1>{state.title}</h1>
			<span>{state.singer}</span>
		</div>
		<div class="genre">
			<h3>장르</h3>
            <span>{state.genre}</span>
		</div>
		<div class="mood">
			<h3>분위기</h3>
            <span>{state.mood}</span>
		</div>
        <div class="lyrics">
			<h3>가사</h3>
            <pre>
            {lyrics}</pre>``
		</div>
		<button class="like" onClick={setLike}>♥ 좋아요</button>
        <button class="play">▶ 재생</button>
	</div>
</section>
    );
}

export default SongInfo;