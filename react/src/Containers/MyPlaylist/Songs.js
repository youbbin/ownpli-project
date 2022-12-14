import React,{useEffect, useState} from "react";
import Slider from "react-slick";
import {Link} from "react-router-dom"
let seletcted = [];


const Song =(props) =>{

  const[songs, setSongs] = useState(props.data);
  const {selectPlaylistCheckBox} = props;
  // let id = props.id.split('=')[1];
  let title =[];
  let singer =[];
  let likes = [];

  // console.log(id)

  useEffect(()=>{
    setSongs(props.data);
  },[props.data])

  
  if(songs !== undefined){
    for(let key in Object.keys(songs)){
        title.push(songs[key].title)
        singer.push(songs[key].singer)
        likes.push(songs[key].likes)
    }
  }

  // console.log(title)
  // console.log(singer)
  // console.log(likes)
  // console.log(id)


  // const selectPlaylistCheckBox =(e)=>{
  //   console.log(e.target.id)
  // }
 
 

  return (
    <div>
        {title.map((title,idx) =>(
        //   <div className='songs'>
            
        //         <div className='title'>{title}</div>
        //         <div className='singer'>{singer[idx]}</div>
        //   </div>


                <div className='list_box'>
                    <div className='checkbox'>
                        <input type="checkbox" class="hidden-box" id={title} onClick={(e) =>selectPlaylistCheckBox(e.target.id, e.target.checked)}></input>
                        <label for={title} class="check--label-songs">
                            <span class="check--label-box"></span>
                        </label>
                    </div>

                    <Link to = {'/myPlaylist/SongInfo?title='+title} className="link">
                      <div className='list_box_text' id={title} >
                          <div className='Songtitle'>{title}</div>
                          <div className='Songsinger'>{singer[idx]}</div>
                          <div className='Songlikes'>{likes[idx]}</div>
                      </div>
                    </Link>
                   
                </div>
        ))}
    </div>
  );

}
export default Song;