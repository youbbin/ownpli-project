import React,{useState, useEffect} from 'react';
import SINGER_SEARCH from './Addlist_option_singer_search'
import SINGER_LIST from './Addlist_singer_like_dislike';
let like_singer_list = [];
let dislike_singer_list = [];


const Addlist_option_singer = (props) =>{

    // let singerlist = props.singerlist; 

    const [like, setLike] = useState("");
    const [dislike, setDisLike] = useState("");
    const [singerlist, setSingerlist] = useState(props.singerlist);

    useEffect(() => {
        setSingerlist(props.singerlist);
    },[props.singerlist]);


    const getPushedButton = (v, v2) =>{
        like_singer_list = v;
        dislike_singer_list = v2;

        let str_like = like_singer_list.toString();
        let str_dislike = dislike_singer_list.toString();

        setLike(str_like);
        setDisLike(str_dislike);
        
    }

    // let singerList= singerlist.map((name) => (<SINGER_SEARCH SingerName={name} getPushedButton = {getPushedButton}/>));
    let singerList= singerlist.map((name) => (<SINGER_SEARCH SingerName={name} getPushedButton = {getPushedButton}/>));

    const keyboardfilter =(e) =>{

        if(e.key === "Enter"){
            if(e.target.value === ''){
                alert("가수를 입력해 주세요");
            }
            else{
                console.log(e.target.value);
            }

            console.log(singerlist)
        }

        
    }

    return(
        <div className='tab-body-singer'>
            <div className='tab-body-singer-search'>
                <div className='input_singer'>
                    <input type='text' className='search-singer-text' onKeyDown={(e) =>{keyboardfilter(e)}}></input>
                    <div className='search-singer-icon'></div>
                </div>
                <div className='output_singer'>
                    {singerList}
                </div>
            </div>

            <div className='tab-body-singer-result'>
                <div className='like-singer' id='like'>
                    <div className='like-text'>like</div>
                    <div className='singerList'>
                        <SINGER_LIST list = {like}></SINGER_LIST>
                    </div>
                </div>
                <div className='dislike-singer' id='dislike'>
                    <div className='dislike-text'>dislike</div>
                    <div className='singerList'>
                        <SINGER_LIST list = {dislike}></SINGER_LIST>
                    </div>
                </div>

            </div>
        </div>
    )
}

export default Addlist_option_singer;