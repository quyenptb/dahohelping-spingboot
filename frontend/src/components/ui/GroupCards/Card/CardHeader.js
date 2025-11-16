import { React } from './importCard.js';
export default function CardHeader({user, time, subject, uni}) {
    return (
        <div className="card-header">
            <div className="user"><a href="#">{user}</a></div>
            <div className="time">{time}</div>
            <div className="subject"><a href="#">{subject}</a></div>
            <div className="uni"> <a href={`/introduction#${uni}`}>{uni}</a></div>
          </div>
    )
}