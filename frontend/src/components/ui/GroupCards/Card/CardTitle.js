import { React } from './importCard.js';

export default function CardTitle({title, award}) {
    return (
        <div className="card-title">
            <h3>{title}</h3>
            <span className="card-award"><h2>{award}đ</h2></span>
          </div>
    )
}