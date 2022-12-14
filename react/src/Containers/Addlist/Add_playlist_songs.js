import React from 'react';

const Add_playlist_songs = (props) =>{
    let title = props.title;
    // let date = props.date;
    const {SelectedPlaylist} = props;

    return(

        <div>
            {(title,title).map((t,d) => (
                <div className='add_playlist_songs'>
                    <div className='checkbox'>
                        <input type="checkbox" class="hidden-box" id={title[d]} onClick={(e) =>SelectedPlaylist(e,d)}></input>
                        <label for={title[d]} class="check--label-songs">
                            <span class="check--label-box"></span>
                        </label>
                    </div>
                    <div className='title'>{title[d]}</div>
                    {/* <div className='date'>{date[d]}</div> */}
                </div>
            ))}
        </div>
    );

}

export default Add_playlist_songs;