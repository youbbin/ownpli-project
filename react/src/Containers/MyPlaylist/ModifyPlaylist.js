import React from 'react';
import '../../css/modal.css'
import axios from 'axios';

const Modify_playlist_modal = (props) =>{
    const { open_create, close_create, modify_check, header } = props;


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
                <div className='modify_playlist_modal'>
                    <input className='modify_playlist_input'></input>
                </div>
            </main>
            <footer id='modify_playlist'>
                <button className="modify_check" onClick={modify_check}>Modify</button>
            </footer>
            </section>
        ) : null}
        </div>
    )

   
}


export default Modify_playlist_modal;