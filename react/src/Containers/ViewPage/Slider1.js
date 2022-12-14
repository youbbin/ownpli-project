
import React,{useEffect, useState} from "react";
import Slider from "react-slick";
import  igm from '../../images/img/0001.jpg'


const Slider1 =(props) =>{

  const header = props.header
  const[state, setState] = useState(props.data);
  let title =[];
  let singer =[];
  let img = [];

 
  // const mapping = () =>{
  //   // state.map((state)=>{
  //   //     title.push(state.title)
  //   // })
  // }


  useEffect(()=>{
    setState(props.data);
  },[props.data])

  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 5,
    slidesToScroll: 5
  };


  
  if(state !== undefined){
    // console.log(Object.keys(state['0'].imageFile));
    // console.log(state['0'].imageFile)
    for(const key in Object.keys(state)){
      title.push(state[key].title)
      singer.push(state[key].singer)
      img.push(state[key].musicId)
    }
  }

  // console.log(title)
  // console.log(singer)
  // console.log(img)
 
 

  return (
    <div>
      <h2 className='slider_header'> {header}</h2>
      <Slider {...settings} className="slider">

        {title.map((title,idx) =>(
          <div className='songs'>
              <img className='img' src={require('../../images/img/'+img[idx]+'.jpg')} alt="SlideShow" ></img>
                <div className='title'>{title}</div>
                <div className='singer'>{singer[idx]}</div>
          </div>
        ))}
      </Slider>
    </div>
  );

}
export default Slider1;