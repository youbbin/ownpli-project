import React,{useEffect, useState} from 'react';

const Addlist_song_list = (props)=>{

    // let songData = props.song_data;
    const [list, setList] = useState(props.song_data);
    const {SelectedSong} = props;

    useEffect(()=>{
        setList(props.song_data)

    },[props.song_data])

    console.log({list});

    

    return(
        <tbody>
            {list.map((song_data) => (
                 <tr>
                    <td>
                        <input type="checkbox" class="hidden-box-songlist" id={song_data.title} onClick={(e) =>SelectedSong(e)} ></input>
                        <label for={song_data.title} class="check--label2">
                            <span class="check--label-box"></span>
                        </label>
                    </td>
                    <td>{song_data.title}</td>
                    <td>{song_data.singer}</td>
                    <td>{song_data.gerne}</td>
                    <td>{song_data.country}</td>
                    <td>{song_data.year}</td>
                    {/* <td>{song_data.gender}</td> */}
                </tr>
            ))}
        </tbody>
    );
}

export default Addlist_song_list;



// import React from 'react';

// const Addlist_song_list = (props) =>{
//     let list = props.list;
//     console.log(list);
//     return(

//         <div>
//             {/* {list.map((name) => (
//                 <div className='singer-list-content'>{name}</div>
//             ))} */}
//         </div>
//     );

// }

// export default Addlist_song_list;