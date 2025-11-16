export default function convertToSlug(str) {
    str = str.toLowerCase()
      .replace(/ /g, '-') 
      .normalize('NFD') 
      .replace(/[\u0300-\u036f]/g, '');
    return str;
  }
