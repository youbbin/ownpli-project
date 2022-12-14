import React from 'react';

const Addlist_option = (props) =>{
    const {SelectedOption} = props;

    // console.log(props.name);

    return(
        <div class="list-item">
            <input type="checkbox" class="hidden-box" id={props.name} onClick={(e) =>SelectedOption(e)}></input>
            <label for={[props.name]} class="check--label">
                <span class="check--label-box"></span>
                <h5 class="check--label-text">{[props.name]}</h5>
            </label>
        </div>
    )
}

export default Addlist_option;