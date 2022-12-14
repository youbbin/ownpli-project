import React,{useEffect}  from 'react';
// import '../css/addlist.css'
let like_btn, dislike_btn, singer_name;

let like_singer =[];
let dislike_singer=[];

const Addlist_option_singer_search = (props) =>{
    const {getPushedButton} = props;

    useEffect(() => {
        like_btn = document.getElementsByClassName("singer_btn_like");
        dislike_btn = document.getElementsByClassName("singer_btn_dislike");
        singer_name = document.getElementsByClassName("singer_name");

        // console.log(singer_name[0].id)
    });
    

    const PushedButton = (e) =>{
        
        // console.log(e.target.id)
        for(let i=0;i<singer_name.length;i++){
            if(e.target.id === singer_name[i].id){
                // console.log(singer_name[i].id);

                if(e.target.value === "like"){ //좋아요 누름
                    let num;
                    let singer;

                    for(let i= 0; i<like_btn.length;i++){
                        if(like_btn[i].id === e.target.id){
                            num = i;
                            singer = e.target.id;
                        }
                    }

                    if(dislike_btn[num].classList[1] === 'active'){
                        //싫어요를 눌렀는데 좋아요 누름
                        // console.log("싫어요를 눌렀는데 좋아요 누름")
                        for(let i=0 ; i<dislike_singer.length;i++){
                            if(dislike_singer[i] === singer){
                                dislike_singer.splice(i, 1); 
                                break;
                            }
                        }
                        dislike_btn[num].classList.remove("active");
                        e.target.classList.add("active");
                        like_singer.push(like_btn[num].id);
                    }
                    else{
                         if(like_btn[num].classList[1] === 'active'){
                            //이미 좋아요를 눌렀는데 다시 좋아요 누름 -> 좋아요 제거
                            // console.log("이미 좋아요를 눌렀는데 다시 좋아요 누름 -> 좋아요 제거")
                            like_btn[num].classList.remove("active");
                            for(let i=0; i<like_singer.length;i++){
                                if (like_singer[i] === singer) { 
                                    like_singer.splice(i, 1); 
                                    break;
                                }
                            }
                        }
                        else{
                            //좋아요로 전환
                            // console.log("좋아요로 전환")
                            like_btn[num].classList.remove("active");
                            e.target.classList.add("active");
                            like_singer.push(like_btn[num].id);
                        }

                    }
                }
                else{
                    let num;
                    let singer;

                    for(let i= 0; i<dislike_btn.length;i++){
                        if(dislike_btn[i].id === e.target.id){
                            num = i;
                            singer = e.target.id;
                        }
                    }

                    if(like_btn[num].classList[1] === 'active'){
                        //좋아요를 눌렀는데 싫어요 누름
                        // console.log("좋아요를 눌렀는데 싫어요 누름")
                        for(let i=0 ; i<like_singer.length;i++){
                            if(like_singer[i] === singer){
                                like_singer.splice(i, 1);
                                break;
                            }
                        }
                        like_btn[num].classList.remove("active");
                        e.target.classList.add("active");
                        dislike_singer.push(dislike_btn[num].id);
                    }
                    else{
                         if(dislike_btn[num].classList[1] === 'active'){
                            //이미 싫어요 눌렀는데 다시 싫어요 누름 -> 싫어요 제거
                            // console.log("이미 좋아요를 눌렀는데 다시 좋아요 누름 -> 좋아요 제거")
                            dislike_btn[num].classList.remove("active");
                            for(let i=0; i<dislike_singer.length;i++){
                                if (dislike_singer[i] === singer) { 
                                    dislike_singer.splice(i, 1); 
                                    break;
                                }
                            }
                        }
                        else{
                            //좋아요로 전환
                            // console.log("싫어요로 전환")
                            dislike_btn[num].classList.remove("active");
                            e.target.classList.add("active");
                            dislike_singer.push(dislike_btn[num].id);
                        }

                    }
                }

                getPushedButton(like_singer, dislike_singer);

            }
        }

    }

    return(
        <div className='singer_research_box'>
            <div className='singer_name' id = {props.SingerName}>{props.SingerName}</div>
            <div className='singer_like'>
                <button className='singer_btn_like' id = {props.SingerName}  value= "like" onClick={(e)=>{PushedButton(e)}}></button>
            </div>
            <div className='singer_dislike'>
                <button className='singer_btn_dislike' id = {props.SingerName} value= "dislike" onClick={(e)=>{PushedButton(e)}}></button>
            </div>
        </div>
    );

}

export default Addlist_option_singer_search;