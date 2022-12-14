import React from 'react';
import '../../css/modal.css'
import axios from 'axios';

const createPlaylistModal = (props) =>{
    const { open_create, close_create, create_check, header } = props;

    // console.log(open_create);
    // if(open_create){
    //     getList();
    // }

    // const SelectedPlaylist = (e, idx) =>{
    //     // console.log(idx+": "+e.target.id+"("+e.target.checked+")");
    //     check(e.target.checked, e.target.id);
    // }

    return( 
        <div className={open_create ? 'openModal modal' : 'modal'}>
        {open_create ? (
            <section>
            <header>
                {header}
                <button close_create="close_create" onClick={close_create}>
                &times;
                </button>
            </header>
            <main>
                <div className='create_playlist_modal'>
                    <input className='create_playlist_input'></input>
                </div>
            </main>
            <footer id='create_playlist'>
                <button className="create_check" onClick={create_check}>Create</button>
            </footer>
            </section>
        ) : null}
        </div>
    )

   
}


export default createPlaylistModal;