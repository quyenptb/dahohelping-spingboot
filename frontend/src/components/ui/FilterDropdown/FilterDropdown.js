import React, { useState, useEffect, useContext } from 'react';
import { DropdownButton, Dropdown } from 'react-bootstrap';
import { AppContext } from 'src/context';
import { filterCards } from 'src/services/CardsService';
function DropDownCustom({ isDisabled, toggle, listItems, onSelect }) {
    const {setChoosenUni} = useContext(AppContext);
    const [value, setValue] = useState(toggle);
    const handleChoice = selectedItem => {
    setValue(selectedItem);
    onSelect(selectedItem);
    if (toggle === "Trường Đại học") {
      setChoosenUni(selectedItem);
    }
  };

  return (
    <DropdownButton disabled={isDisabled} title={value} id="dropdown-basic">
        {Array.isArray(listItems) && listItems.map(item => (
  <Dropdown.Item key={item.id} onClick={() => handleChoice(item.name)}>
    {item.name}
  </Dropdown.Item>
))}
    </DropdownButton>
  );
}

function FilterDropdown() {
  const {
    filterState, //lọc trường, khoa, ngành, môn
    setFilterState, //gán lọc trường, khoa, ngành, môn
    setCards, //gán danh sách thẻ
    dahoHelping, //lọc dahohelping
    uni, //danh sách trường
    fal, //danh sách khoa
    maj, //danh sách ngành
    sub, //danh sách môn học
  } = useContext(AppContext);
  
  useEffect(() => {
    if (filterState.DahoHelping === "DahoHelping") {
      return;
    }
    else {
    const response = filterCards(filterState.DahoHelping, filterState.Others[0], filterState.Others[1], filterState.Others[2], filterState.Others[3]);
    setCards(response);
    }
  }, [filterState.Others[0], filterState.Others[1], filterState.Others[2], filterState.Others[3]]);

  const handleSelect = (dropdownName, selectedItem) => {
    setFilterState(prevState => ({
      ...prevState,
      [dropdownName]: selectedItem
    }));
  };

  return (
    <>
      <div className="btn-group">
        <DropDownCustom
          toggle="DahoHelping"
          listItems={dahoHelping}
          onSelect={item => handleSelect('DahoHelping', item)}
        />
        {filterState.DahoHelping === 'DahoHelping' && (
            <>
        <DropDownCustom
          toggle="Trường Đại học"
          listItems={uni}
          onSelect={item => handleSelect('Others', [item.key, null, null, null])}
        />
        <DropDownCustom
          disabled = {filterState.Others[0] === 'Trường Đại học' ? true : false }
          toggle="Khoa"
          listItems={fal}
          onSelect={item => handleSelect('Others', [filterState.Others[0], item.key, null, null])}
        />
        <DropDownCustom
          disabled = {filterState.Others[0] === 'Trường Đại học' && filterState.Others[1] === 'Khoa' ? true : false }
          toggle="Ngành"
          listItems={maj}
          onSelect={item => handleSelect('Others', [filterState.Others[0], filterState.Others[1], item.key, null])}
        />
        <DropDownCustom
          disabled = {filterState.Others[0] === 'Trường Đại học' && filterState.Others[1] === 'Khoa' && filterState.Others[2] === 'Ngành' ? true : false }
          toggle="Môn"
          listItems={sub}
          onSelect={item => handleSelect('Others', [filterState.Others[0], filterState.Others[1], filterState.Others[2], item.key])}
        />
        </>
    )}
    </div>
    </>
    )
}

export default FilterDropdown;
