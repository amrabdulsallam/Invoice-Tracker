import FileView from "./FileViewComponenet";
import './App.css';
import { useState } from "react";


const Dashboard = () => {
    const [page , setPage] = useState(0)

    const HandlePrePage = () => {
        if(page >= 1){
            setPage(page - 1);
        }
    }

    const HandleNextPage = () => {
        setPage(page + 1)
    }

    const files = [ {
        fileName: 'file1.txt', 
        uploadDate: '2005', 
        updateDate : '2010'
          
      },
      {
        fileName: 'file10.txt',
        uploadDate: '2040', 
        updateDate : '2020'
      },
      ];
    return(
        <div>
            <h1>Dashboard</h1>
            {files.map(function(data) {
            return (
                <FileView fileName={data.fileName} uploadDate={data.uploadDate} updateDate={data.updateDate}/>
            )
            })}

            <footer className="pages">
                <button onClick={HandlePrePage}>Prev</button>
                <p>{page}</p>
                <button onClick={HandleNextPage}>Next</button>
            </footer>
        </div>
    );
}

export default Dashboard;