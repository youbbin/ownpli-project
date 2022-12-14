import React,{useState, useEffect} from 'react';
import { Link } from "react-router-dom";

import axios from 'axios';


const Playlist =(props) =>{

    const [list, setList] = useState(props.list);
    const {selectPlaylistCheckBox, selectPlaylistTitle} = props
    // console.log(list)

    let title =[];

    for(let i=0; i<list.length; i++){
        // playlist_id.push(list.data[i].playlistId);
        title.push(list[i].title);
        // date.push(list[i].date);
        // console.log(title)
    }


    useEffect(()=>{
        setList(props.list);
        

    },[props.list])

    
    return(
        <div className='song_list_box'>
              {(title).map((t,d) => (
                <div className='list_box'>
                    <div className='checkbox'>
                        <input type="checkbox" class="hidden-box" id={title[d]} onClick={(e) =>selectPlaylistCheckBox(e,d)}></input>
                        <label for={title[d]} class="check--label-songs">
                            <span class="check--label-box"></span>
                        </label>
                    </div>
                    <Link to={'/myPlaylist/Songs?vlaue='+title[d]}>
                    <div className='list_box_text' id={title[d]} onClick={(e) => selectPlaylistTitle(e)}>
                        <div className='listTitle'>{title[d]}</div>
                        {/* <div className='listDate'>{date[d]}</div> */}
                    </div>
                    </Link>
                </div>
            ))}
             {/* {(list.length !== 0)?
                (title,date).map((t,d) => (
                    <div className='list_box'>
                       <div className='checkbox'>
                            <input type="checkbox" class="hidden-box" id={title[d]} onClick={(e) =>selectPlaylistCheckBox(e)}></input>
                            <label for={title[d]} class="check--label-songs">
                                <span class="check--label-box"></span>
                            </label>
                        </div>
                        <div className='list_box_text' id={title[d]} onClick={(e) => selectPlaylistTitle(e)}>
                            <div className='listTitle'>{title[d]}</div>
                            <div className='listDate'>{title[d]}</div>
                        </div>
                        
                    </div>
                ))
                :
                <div></div>
             } */}
        </div>
    );
}

export default Playlist;