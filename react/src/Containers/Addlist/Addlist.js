import React, {useEffect, useState } from 'react';
import axios from 'axios';

import '../../css/addlist.css'
import Option from './Addlist_option';
import Addlist_Option_Singer from './Addlist_option_singer';
import Addlist_Song_List from './Addlist_song_list';
import Add_playlist from './Add_playlist';  //모달창
import Create_playlist_modal from './Create_playlist_modal';
import {useCookies } from "react-cookie";

const genre_str = ['Dance','Hiphop','Ballad','OST','Rock','Folk','Trot','Newage','Pop','Metal','Country','Latin','Alternative','R&B','Electro','JPOP','Animation']
const mood_str = ['힐링', '운동','트랜디','몽환','일상','여행','공부','위로','산책','비오는날','설렘','슬픔','우울','신나는','센치','눈물','기분좋은','행복','즐거운','기분 업','노동요','힙한'];
const singer_str = ['IVE (아이브)','지코','BLACKPINK','NewJeans','소녀시대 (GIRLS\' GENERATION)','WSG워너비 (가야G)','임영웅','WSG워너비 (4FIRE)','윤미래, 비비(BIBI)','10CM','ITZY (있지)','(여자)아이들','멜로망스(MeloMance)','BIG Naughty (서동현)','경서','주호','나연 (TWICE)','싸이 (PSY)','aespa','김승민','IVE (아이브) ','TWICE (트와이스)','LE SSERAFIM (르세라핌)','NCT 127','볼빨간사춘기','Charlie Puth','테이(Tei)','김민석 (멜로망스)','BE\'O (비오)']
const language_str = ['한국', '영어권', '일본', 'Others']
const year_str = ['1980\'s', '1990\'s', '2000\'s', '2010\'s', '2020\'s']
// const gender_str = ['Woman', 'Man']

let song_data = [];
let tab_num = 0;
let selectedOption= ['','','','',''];
let like_singer, dislike_singer='';
let selectedSong=[]; //체크박스 선택된 노래들
let singers =[];

const userId = "test123@naver.com";



const AddList = ()=>{

    const [songList, setSongList] = useState([]);
    const [cookies, setCookie] = useCookies(['userId','songs']);
    
    const [singerlist, setSingerlist] = useState([]);
    //분위기, 가수, 국가, 장르 받아오기

    const getSingers= async() =>{
        let res = await axios.post(
            '/music/singer',
            {
                headers: {
                    'Accept': 'application/json',
                }
            }
        )
        // console.log(res.data.singerName);

        // for(let i=0;i<res.data.length; i++){
        //     singers.push(res.data[i].singerName);
        // }
        // singer = res.data.singerName.

        singers = res.data.singerName;
    
        setSingerlist(singers)
        
        // console.log(singers)
        // singers.length = 0;
    }


    useEffect(() => {
        // tabIndicator = document.getElementsByClassName("tab-indicator")[0];
        // tabBody = document.getElementsByClassName("tab-body-div");
        // tabsPane = document.getElementsByClassName("header");
        // console.log("sssssssss: "+tabsPane[0].classList)
        getSingers();

    },[]);


    //옵션 체크박스 클릭
    const onSetOption =(e) =>{
        if(e.target.checked){
            selectedOption[tab_num] += e.target.id+"@";
            
        }
        else{
            let delete_list = selectedOption[tab_num].split('@');

            for(let i = 0; i < delete_list.length; i++) {
                if(delete_list[i] === e.target.id)  {
                    delete_list.splice(i, 1);
                    i--;
                }
            }

            selectedOption[tab_num] = delete_list.join('@');
            
        }
        console.log(tab_num+": "+e.target.id+ "-> "+ e.target.checked)
        console.log(selectedOption)
    }

    //노래 체크박스 선택
    const onSelectedSong=(e) =>{

        if(e.target.checked){
            selectedSong.push(e.target.id);
        }
        else{
            for(let i=0;i<selectedSong.length; i++){
                if(selectedSong[i] === e.target.id){
                    selectedSong.splice(i, 1);
                    i--;
                }
            }
        }
        
        // console.log("selected Song: "+selectedSong);
    }

    //모든 음악의 체크박스
    const setAllSong=(e) =>{
        let song_list_checkBox = document.getElementsByClassName('hidden-box-songlist');
        
        if(song_list_checkBox.length>0){
            if(e.target.checked){
                for(let i=0;i<song_list_checkBox.length;i++){
                    song_list_checkBox[i].checked = true;
                    selectedSong.push(song_list_checkBox[i].id);
                }
            }
            else{
                for(let i=0;i<song_list_checkBox.length;i++){
                    song_list_checkBox[i].checked = false;
                    selectedSong.length = 0;
                }
            }
        }
        console.log("selected Song(all): "+selectedSong);
        
    }

    //플레이스트에 추가할 노래 필터링
    const setEnter =async() =>{

        //가수 가져오기
        let like_singer_p = document.getElementsByClassName('like-singer')[0];
        let like_singer_c = like_singer_p.getElementsByClassName('singer-list-content');
        let like_s ='';

        for(let i=0; i<like_singer_c.length;i++){
            like_s += like_singer_c[i].id +'@';
        }

        let dislike_singer_p = document.getElementsByClassName('dislike-singer')[0];
        let dislike_singer_c = dislike_singer_p.getElementsByClassName('singer-list-content');

        let dislike_s ='';
        for(let i=0; i<dislike_singer_c.length;i++){
            dislike_s += dislike_singer_c[i].id+'@';
        }

        like_singer = like_s.substring(0,(like_s.length-1));
        dislike_singer = dislike_s.substring(0,(dislike_s.length-1));


        if(like_singer === ''){like_singer=null;}
        if(dislike_singer === ''){dislike_singer=null;}

        // console.log("selected like Singer: "+like_singer)
        // console.log("selected dislike Singer: "+dislike_singer)

        
        //나머지 옵션 가져오기(genre, mood, country, year)
        // selectedOption[4]= selectedOption[4].replaceAll('\'s','')

        let msg = [];
        msg[0] = selectedOption[1].substring(0, (selectedOption[1].length -1));
        msg[1] = selectedOption[2].substring(0, (selectedOption[2].length -1));
        msg[2] = selectedOption[3].substring(0, (selectedOption[3].length -1));
        msg[3] = selectedOption[4].substring(0, (selectedOption[4].length -1));

        for(let i = 0;i<msg.length;i++){
            if(msg[i] === ''){msg[i] = null}
        }

        

        let data = {
            likedSinger: like_singer,
            dislikedSinger: dislike_singer,
            genre: msg[0],
            mood: msg[1],
            country: msg[2],
            year: msg[3]
        };

        console.log(data)

        const response = await axios.post(
            '/music/add', data,
            {
                headers: {
                    'Accept': 'application/json',
                }
            }
        );

        data = response.data;
        console.log(data)
        
        song_data.length =0;
        for(let i=0 ; i< data.length; i++){
            song_data.push(
                {
                    musicId: data[i].musicId,
                    title: data[i].title,
                    singer: data[i].singer,
                    gerne: data[i].genre,
                    country:data[i].country,
                    year: data[i].year,
                }
            )
        }
        let s = song_data;

        // console.log(song_data);

        setSongList(s);

        setCookie('songs',songList)
        console.log(cookies.songs);
    }


    let genre= genre_str.map((name) => (<Option name={name} SelectedOption ={onSetOption}/>));
    let mood = mood_str.map((name) => (<Option name={name} SelectedOption ={onSetOption}/>));
    let language = language_str.map((name) => (<Option name={name} SelectedOption ={onSetOption}/>));
    let year = year_str.map((name) => (<Option name={name} SelectedOption ={onSetOption}/>));
    // let gender = gender_str.map((name) => (<Option name={name} SelectedOption ={onSetOption}/>));


    
    const setTap=(num) =>{
        let tabIndicator, tabBody, tabsPane;
        tabIndicator = document.getElementsByClassName("tab-indicator")[0];
        tabBody = document.getElementsByClassName("tab-body-div");
        tabsPane = document.getElementsByClassName("header");
        
        tabsPane[tab_num].classList.remove("active");
        tabsPane[num].classList.add("active");

        tabBody[tab_num].classList.remove("active");
        tabBody[num].classList.add("active");

        tabIndicator.style.left = `calc(calc(100% / 5) * ${num})`;
        tab_num = num;
    }

    //Addlist 버튼 누르면 모달창 나타남
    const [modalOpen, setModalOpen] = useState(false);
    const [modalCreateOpen, setCreateModalOpen] = useState(false);

    const openAddPlaylist =() =>{

        setModalOpen(true);
        
    }

    const closeModal = () => {
        setModalOpen(false);
    };

    const closeCreateModal = () => {
        setCreateModalOpen(false);
    };

    let checekd_playlist =[];

    //모달 창에서 Add Song 버튼 => 체크된 플레이리스트에 체크한 노래들이 저장됨
    const addSong =async() =>{
        let checekd_playlist_str, checked_songs_str;

        console.log(song_data.title)

        checekd_playlist_str = checekd_playlist.join('@');
        checked_songs_str = selectedSong.join('@');
        


        let data = {
            userId: cookies.userId,
            title: checekd_playlist_str,
            songsTitle: checked_songs_str
        };

        const response = await axios.post(
            '/playlist/addSongs', data,
            {
                headers: {
                    'Accept': 'application/json',
                }
            }
        );

        console.log(response);
        
        console.log(checekd_playlist_str+": "+ checked_songs_str)

        setModalOpen(false);
    }

    const createPlaylist =() =>{
        
        setModalOpen(false);//기존 플레이리스트에 음악 추가하는 모달 닫기
        
        
        setCreateModalOpen(true); // 플레이리스트 생성 모달 열기
        // console.log(modalCreateOpen);
    }

    //새로운 플레이리스트 title 입력 후 생성하는 버튼(Create)
    const create_check =async( )=>{
        let playlist_name = document.getElementsByClassName('create_playlist_input')[0];

        if(playlist_name.value === ''){
            alert("생성할 플레이리스트 이름을 작성해 주세요");
        }
        else{
            let checked_songs_str = selectedSong.join('@');

            if(checked_songs_str===''){
                checked_songs_str = null;
            }
    
    
            let data = {
                userId: cookies.userId,
                title:playlist_name.value,
                songsTitle: checked_songs_str
            };
    
            const response = await axios.post(
                '/playlist/create', data,
                {
                    headers: {
                        'Accept': 'application/json',
                    }
                }
            );
    
        }

        setCreateModalOpen(false);
        window.location.reload();
    }

    //모달창에서 플레이리스트 체크박스 선택 시 호출됨
    const checked_playlist_fnc = (checked, id) =>{
        if(checked){
            checekd_playlist.push(id);
        }
        else{
            for(let i = 0; i < checekd_playlist.length; i++) {
                if(checekd_playlist[i] === id)  {
                    checekd_playlist.splice(i, 1);
                  i--;
                }
            }
        }
    }
    

    return(
        <div class="right-content">
            {/* 모달창: 플레이리스트에 노래 추가*/}
            <Add_playlist open={modalOpen} close={closeModal} add={addSong} create ={createPlaylist} check = {checked_playlist_fnc} header="Palylist"></Add_playlist> 
            <Create_playlist_modal open_create = {modalCreateOpen} close_create={closeCreateModal} create_check = {create_check} header="Create Playlist"></Create_playlist_modal>
            <div className="tab-content">
                <div className = "check-content">
                    <div className="tabs">
                        <div className="tab-header">
                            <div className="header active" onClick={() => setTap(0)}>
                                <i className="fa fa-code"></i> SINGER
                            </div>
                            <div className="header" onClick={() => setTap(1)}>
                                <i className="fa fa-pencil-square-o"></i> GENRE
                            </div>
                            <div className="header" onClick={() => setTap(2)}>
                                <i className="fa fa-bar-chart"></i> MOOD
                            </div>
                            <div className="header" onClick={() => setTap(3)}>
                                <i className="fa fa-envelope-o"></i> COUNTRY
                            </div>
                            <div className="header" onClick={() => setTap(4)}>
                                <i className="fa fa-envelope-o"></i> YEAR
                            </div>
                            {/* <div className="header" onClick={() => setTap(5)}>
                                <i className="fa fa-envelope-o"></i> GENDER
                            </div> */}
                        </div>
                        <div className="tab-indicator"></div>
                        <div className="tab-body">
                            <div className="tab-body-div active">
                                    <Addlist_Option_Singer singerlist ={singerlist}/>
                            </div>
                            <div className="tab-body-div">
                                <div className='tab-body-grid'>
                                    {genre}
                                </div>
                            </div>
                            <div className="tab-body-div">
                                <div className='tab-body-grid'>
                                    {mood}
                                </div>
                            </div>
                            <div className="tab-body-div">
                                <div className='tab-body-grid'>
                                    {language}
                                </div>
                            </div>
                            <div className="tab-body-div">
                                <div className='tab-body-grid'>
                                    {year}
                                </div>
                            </div>
                            {/* <div className="tab-body-div">
                                <div className='tab-body-grid'>
                                    {gender}
                                </div>
                            </div> */}
                        </div>
                    </div>
                </div>
            </div>

            <div class="list-item2">
                <button class="btn-enter" onClick={setEnter}>Enter</button>
            </div>

            <div className='singer-table'>
                <table className='list-table'>
                    <thead>
                        <tr>
                            <th width={100}px>
                            <input type="checkbox" class="hidden-box" id="all" onClick={(e) =>setAllSong(e)}></input>
                            <label for="all" class="check--label2">
                                <span class="check--label-box2"></span>
                            </label>
                            </th>
                            <th width={500}px>Title</th>
                            <th width={300}px>Singer</th>
                            <th width={140}px>Gerne</th>
                            <th width={130}px>Language</th>
                            <th width={130}px>Year</th>
                            {/* <th width={100}px>Gender</th> */}
                        </tr>
                    </thead>
                    <Addlist_Song_List song_data ={songList} SelectedSong={onSelectedSong}></Addlist_Song_List>
                </table>
            </div>

            <div className='addPlaylistBtn'>
                <button className='btn-addlist' onClick={openAddPlaylist}>Addlist</button>
            </div>
            
        </div>
    );
}

export default AddList;