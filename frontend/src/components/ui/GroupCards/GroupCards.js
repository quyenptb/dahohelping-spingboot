import { useContext } from 'react';
import { React, Link, BrowserRouter as Router} from '../../../utils/import.js';
import Card from './Card/Card.js';
import { AppContext } from 'src/context/AppContext.js';

export default function GroupCards() {
  const { cards, setCards } = useContext(AppContext);
    return (
      <>
        {cards.map((card) => (
        <Link key={card.card_id} to={`/cards/${card.card_id}`} state={{ card }}>
         <Card key={card.card_id} card={card} />
       </Link>
        ))}
      </>
    );
  }