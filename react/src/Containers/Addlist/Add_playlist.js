import React,{useState, useEffect} from 'react';
import Add_playlist_songs from './Add_playlist_songs';
import '../../css/modal.css'
import { useCookies } from 'react-cookie';
import axios from 'axios';
let playlist_id =[]
let title =[];
// let date =[];

let first_page = true;

const Add_playlist = (props) =>{
    const { open, close, add, create, header, check } = props;
    const [cookies, setCookie] = useCookies(['userId']); 

    const getList = async() =>{
        console.log('getlist')
    
        let data = {
            userId: cookies.userId
        }
     
        const response = await axios.post(
            '/playlist/getlist',data,{
                headers: {
                    'Accept': 'application/json',
                }
            }
        );
    
        console.log(response.data)
    
        //두번 실행되서 한번 실행 후 배열 비움
        playlist_id.length = 0;
        title.length = 0;
        // date.length = 0;
    
    
        for(let i=0; i<response.data.length; i++){
            playlist_id.push(response.data[i].playlistId);
            title.push(response.data[i].title);
            // date.push(response.data[i].date);
        }
    
    }

    useEffect(() =>{
        getList(); //get으로 플레이리스트 받아온다.
    },[]);
    

   

    if(open){
        getList();
    }

    const SelectedPlaylist = (e, idx) =>{
        // console.log(idx+": "+e.target.id+"("+e.target.checked+")");
        check(e.target.checked, e.target.id);
    }

    return( 
        <div className={open ? 'openModal modal' : 'modal'}>
        {open ? (
            <section>
            <header>
                {header}
                <button className="close" onClick={close}>
                &times;
                </button>
            </header>
            <main>
                <Add_playlist_songs title ={title} SelectedPlaylist={SelectedPlaylist}></Add_playlist_songs>
            </main>
            <footer>
                <button className="create" onClick={create}>create Playlist</button>
                <button className="add" onClick={add}>Add Song</button>
            </footer>
            </section>
        ) : null}
        </div>
    )

   
}


export default Add_playlist;