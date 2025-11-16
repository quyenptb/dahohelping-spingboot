import React, { useEffect, useState } from 'react';
import CardHeader from './CardHeader.js';
import CardTitle from './CardTitle.js';
import CardFooter from './CardFooter.js';
import { getUserById } from 'src/services/UsersService';
import {user_data, card_data,   daho_data, fal_data, maj_data, sub_data, uni_data} from 'src/data/index'
import { Link } from 'react-router-dom';

function Card({card}) {
  const [cardUser, setCardUser] = useState([]);

  useEffect(() => {
    async function fetchUser() {
      try {
        const v_cardUser = await getUserById(card.user_id);
        setCardUser(v_cardUser[0]);
      } catch (error) {
        console.error(error);
      }
    }
  
    fetchUser();

  }, [])



    return (
      <div className="card">
        <div className="card-body">
          <CardTitle title={card.title} award={card.award} />
          <CardHeader user = {cardUser.username} time = {card.time} subject = {"cardSub.name"} uni = {"cardUni.code"} />
          <p className="card-text">{card.text}</p>
          <CardFooter id={card.card_id}/>
                </div>
      </div>
    )
    }

    export default Card