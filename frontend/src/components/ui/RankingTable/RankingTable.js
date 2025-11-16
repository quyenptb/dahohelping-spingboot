import { React, Table} from '../../../utils/import.js';
import '../../../App.css'
import { AppContext } from 'src/context/AppContext.js';
import { useContext } from 'react';

export default function RankingTable() {
  const {ranking} = useContext(AppContext);
    return (
      <Table className="table col-2 caption-top ranking-table">
        <caption style={{ textAlign: 'center' }}>BẢNG XẾP HẠNG</caption>
        <thead>
          <tr>
            <th scope="col">Vị trí</th>
            <th scope="col">Họ và tên</th>
            <th scope="col">Trường Đại học</th>
            <th scope="col">Điểm</th>
          </tr>
        </thead>
        <tbody>
        {ranking.map((user) => {
          return (
            <tr>
          <th scope="row">{user.id}</th>
           
            <td><a href="/user/`${user.id}`">{user.name}</a></td>
            <td>
              <img src={require(`src/assets/icons/${user.uni}.png`)} style={{ width: '52px', height: '50px' }} alt={user.uni} />
            </td>
            <td>{user.score}</td>
            </tr>
          )}) }
          </tbody>
      </Table>
    );
  }