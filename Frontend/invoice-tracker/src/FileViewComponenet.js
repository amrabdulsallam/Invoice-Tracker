import file from './file.jpg'
import './FileView.css'


const FileView = (props) => {
    return(
        <div className="flex-container">
            <img width="150" height="150" src={file} alt='File Picture'/>
            <div className='details-container'>
                <label>File Name : {props.fileName}</label>
                <br/>
                <label>Upload Date : {props.uploadDate}</label>
                <br/>
                <label>Last Updated : {props.updateDate}</label>
            </div>
        </div>
    );
}

export default FileView;