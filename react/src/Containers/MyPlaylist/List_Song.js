import React,{useState, useEffect} from 'react';
import { useCookies } from 'react-cookie';
import { useLocation , useNavigate} from 'react-router-dom';
import axios from 'axios';

import Song from './Songs';


import Modify_playlist_modal from './ModifyPlaylist'





const Songs = (props) =>{
    let deleteMusic = [];

    const [cookies, setCookie] = useCookies(['userId']);
    const location = useLocation();
    const navigate = useNavigate();
    const [songs, setSong] = useState([]);
    const [id, setId] = useState('');
    const [modalCreateOpen, setCreateModalOpen] = useState(false);
    // const { id } = queryString.parse(value);

    const selectPlaylistCheckBox =(musicId, check)=>{
        // console.log(typeof(check));

        if(check){
            deleteMusic.push(musicId);
        }
        else{

            for(let i = 0; i < deleteMusic.length; i++) {
                if(deleteMusic[i] === musicId)  {
                    deleteMusic.splice(i, 1);
                }
            }
        }

        // console.log(deleteMusic)
    }


    const closeCreateModal = () => {
        setCreateModalOpen(false);
    };
   
    

    useEffect(()=>{
        let id = decodeURI(location.search);
        console.log(id);

        setId(id.split("=")[1])
;

        const getSongs =async() =>{

            let data = {
                userId: cookies.userId,
                playlistTitle: id.split('=')[1]
            }

            console.log(data)
            const response = await axios.post(
                '/playlist/getlist/musics',data,{
                    headers: {
                        'Accept': 'application/json',
                    }
                }
            );


            setSong(response.data.musicDTOList);
            
        }

        getSongs();
    },[location])

    const setModify =async() =>{

        setCreateModalOpen(true);

    }

    const setRemove = async() =>{

        let deleteMusic_str = deleteMusic.join('@');

        if(deleteMusic_str === '') deleteMusic_str = null;

        let data = {
            userId: cookies.userId,
            playlistTitle: id,
            music: deleteMusic_str
        }

        let res = await axios.post(
            '/playlist/getlist/delete',data,{
                headers: {
                    'Accept': 'application/json',
                }
            }
        )

        window.location.reload();
    }

    const modify_check =async() =>{

        let newName = document.getElementsByClassName('modify_playlist_input')[0];
        // console.log(newName.value)

        let data = {
            userId: cookies.userId,
            oldTitle: id,
            newTitle: newName.value
        };

        console.log(data)

        let res = await axios.post(
            '/playlist/getlist/update',data,
            {
                headers: {
                    'Accept': 'application/json',
                }
            }
        )
        setCreateModalOpen(false);
        navigate('/myPlaylist')

    }

    const selectedSongs =(title)=>{
        // console.log(title)
    }

   

    return( 
        <div className='songsBox'>
            <Modify_playlist_modal open_create = {modalCreateOpen} close_create={closeCreateModal} modify_check = {modify_check} header="Modify Playlist Name"></Modify_playlist_modal>
            <div className='buttons'>
                <div className='id'>{id}</div>
                <button className='modify' onClick={setModify}>modify</button>
                <button className='remove' onClick={setRemove}>remove</button>
            </div>
            <Song className="songs_content" data = {songs} selectPlaylistCheckBox= {selectPlaylistCheckBox} selectedSongs={selectedSongs}></Song>
        </div>
    )

   
}


export default Songs;