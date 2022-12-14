import React from 'react';

const Addlist_singer_like_dislike = (props) =>{
    let list = props.list.split(',');

    // console.log(list)

    return(

        <div>
            {list.map((name) => (
                <div className='singer-list-content' id={name}>{name}</div>
            ))}
        </div>
    );

}

export default Addlist_singer_like_dislike;