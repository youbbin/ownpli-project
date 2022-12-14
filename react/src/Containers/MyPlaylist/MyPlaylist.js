import React,{useEffect, useState} from 'react';
import { Route, Link, Routes, useLocation } from "react-router-dom";
import {useCookies} from "react-cookie";
import '../../css/my_playlist.css'
import Playlist_list from './Playlist_list'
import axios from 'axios';

import Create_playlist_modal from '../Addlist/Create_playlist_modal'

let checkedlistTitle = '';
let checkedPlaylist = [];


const MyPlaylist =() =>{
    const [list, setList] = useState([]);
    const [cookies, setCookie] = useCookies(['userId','seletedList']); 
    const [modalCreateOpen, setCreateModalOpen] = useState(false);
    const [modalOpen, setModalOpen] = useState(false);
    const [title, getTitle] = useState('');
   
    let data = {
        userId: cookies.userId
    }
    

    const closeModal = () => {
        setModalOpen(false);
    };

    const getList = async() =>{
        let response = await axios.post(
            '/playlist/getlist',data,{
                headers: {
                    'Accept': 'application/json',
                }
            }
        );

        setList(response.data)
        console.log("list"+response.data)
    }

    useEffect(() => {
        getList();
    },[]);


    const closeCreateModal = () => {
        setCreateModalOpen(false);
    };

    const create_check = async() =>{
        let playlist_name = document.getElementsByClassName('create_playlist_input')[0];
        
        if(playlist_name.value === ''){
            alert("생성할 플레이리스트 이름을 작성해 주세요");
        }
        else{
            let checked_songs_str = checked_songs_str = null;
    
            let data = {
                userId: cookies.userId,
                title:playlist_name.value,
                songsTitle: checked_songs_str
            };

            // console.log(data)
    
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

    const selectCheckBox =(e) =>{
        if(e.target.checked){
            checkedPlaylist.push(e.target.id);
        }
        else{
            for(let i=0; i<checkedPlaylist.length; i++){
                if(e.target.id === checkedPlaylist[i]){
                    checkedPlaylist.splice(i,1);
                    i--;
                }
            }
        }

        console.log(checkedPlaylist)
    }



    // let header ='';

    const selectTitle =(e) =>{ 
    }

    const remove_btn =async() =>{

        let msg = checkedPlaylist.join('@');
        console.log(msg)

        checkedPlaylist.length = 0;

        let data = {
            userId: cookies.userId,
            title: msg
        }

        let response = await axios.post(
            '/playlist/delete', data,
            {
                headers: {
                    'Accept': 'application/json',
                }
            }
        )

        getList();
    }

    const add_btn = async() =>{
        
        setCreateModalOpen(true);

        getList();

    } 

    
    
    return(
        <div className='my_playlist_box'>
            
             <Create_playlist_modal open_create = {modalCreateOpen} close_create={closeCreateModal} create_check = {create_check} header="Create Playlist"></Create_playlist_modal>
            <div className='playlist_remove_btn_box'>
                <button class="playlist_remove_btn" onClick={remove_btn}>remove</button>
                <button class="playlist_add_btn" onClick={add_btn}>add</button>
            </div>
            <Playlist_list list = {list} selectPlaylistCheckBox = {selectCheckBox} selectPlaylistTitle = {selectTitle}></Playlist_list>
        </div>
    );
}

export default MyPlaylist;